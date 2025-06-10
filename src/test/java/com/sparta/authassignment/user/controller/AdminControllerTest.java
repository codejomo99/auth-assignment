package com.sparta.authassignment.user.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sparta.authassignment.common.exception.CommonErrorCode;
import com.sparta.authassignment.security.jwt.JwtUtil;
import com.sparta.authassignment.user.dto.AdminRolePatchResponse;
import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.entity.UserRole;
import com.sparta.authassignment.user.repository.UserRepository;
import com.sparta.authassignment.user.service.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;

    @BeforeEach
    void setup() throws Exception {
        userRepository.deleteAll();
        User user = User.builder()
            .username("admin")
            .nickName("관리자")
            .password(passwordEncoder.encode("1234"))
            // .email("admin@example.com")
            .userRole(UserRole.ADMIN)
            .build();
        userRepository.save(user);

        User user1 = User.builder()
            .username("username")
            .nickName("유저")
            .password(passwordEncoder.encode("1234"))
            // .email("user@example.com")
            .userRole(UserRole.USER)
            .build();
        userRepository.save(user1);

        adminService = Mockito.mock(AdminService.class);

        java.lang.reflect.Field field = AdminController.class.getDeclaredField("adminService");
        field.setAccessible(true);
        field.set(applicationContext.getBean(AdminController.class), adminService);
    }

    @Test
    @DisplayName("성공 - ROLE_ADMIN이 토큰과 함께 요청")
    void patchRole_success() throws Exception {
        // given
        User user = userRepository.findAll().get(0);
        Long userId = user.getId();

        String loginRequestJson = objectMapper.writeValueAsString(Map.of(
            "username", "admin",
            "password", "1234"
        ));

        MvcResult loginResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
            .andExpect(status().isOk())
            .andReturn();

        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.accessToken");

        // mock 응답 설정
        AdminRolePatchResponse mockResponse = new AdminRolePatchResponse(user);
        Mockito.when(adminService.patchRole(userId)).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(patch("/admin/users/{id}/roles", userId)
                .header(HttpHeaders.AUTHORIZATION, token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.nickname").value(user.getNickName()))
            .andExpect(jsonPath("$.roles.role").value("ADMIN"));
    }

    @Test
    @DisplayName("실패 - 권한 없음")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void patchRole_forbidden() throws Exception {

        // given
        User user = userRepository.findAll().get(1);
        Long userId = user.getId();

        String loginRequestJson = objectMapper.writeValueAsString(Map.of(
            "username", "username",
            "password", "1234"
        ));

        MvcResult loginResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
            .andExpect(status().isOk())
            .andReturn();

        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.accessToken");

        // mock 응답 설정
        AdminRolePatchResponse mockResponse = new AdminRolePatchResponse(user);
        Mockito.when(adminService.patchRole(userId)).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(patch("/admin/users/{id}/roles", userId)
                .header(HttpHeaders.AUTHORIZATION, token))
            .andExpect(status().isForbidden())
            .andExpect(jsonPath("$.error.code").value(CommonErrorCode.ACCESS_DENIED.getErrorCode()))
            .andExpect(jsonPath("$.error.message").value(CommonErrorCode.ACCESS_DENIED.getMessage()));

    }

    @Test
    @DisplayName("실패 - 잘못된 토큰")
    void patchRole_invalidToken() throws Exception {
        // given
        User user = userRepository.findAll().get(0);
        Long userId = user.getId();

        String loginRequestJson = objectMapper.writeValueAsString(Map.of(
            "username", "username",
            "password", "1234"
        ));

        MvcResult loginResult = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
            .andExpect(status().isOk())
            .andReturn();

        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.accessToken");

        // mock 응답 설정
        AdminRolePatchResponse mockResponse = new AdminRolePatchResponse(user);
        Mockito.when(adminService.patchRole(userId)).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(patch("/admin/users/{id}/roles", userId)
                .header(HttpHeaders.AUTHORIZATION, token + "xxx"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error.code").value(CommonErrorCode.JWT_INVALID.getErrorCode()))
            .andExpect(jsonPath("$.error.message").value(CommonErrorCode.JWT_INVALID.getMessage()));
    }

    @Test
    @DisplayName("실패 - 토큰없음")
    void patchRole_missingToken() throws Exception {
        // given
        User user = userRepository.findAll().get(0);
        Long userId = user.getId();

        // when & then
        mockMvc.perform(patch("/admin/users/{id}/roles", userId))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error.code").value(CommonErrorCode.JWT_MISSING.getErrorCode()))
            .andExpect(jsonPath("$.error.message").value(CommonErrorCode.JWT_MISSING.getMessage()));
    }

    @Test
    @DisplayName("실패 - 만료된 토큰")
    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    void patchRole_expiredToken() throws Exception {

        User user = userRepository.findAll().get(0);
        Long userId = user.getId();
        String expiredToken = jwtUtil.createExpiredToken(user.getUsername());

        // mock 응답 설정
        AdminRolePatchResponse mockResponse = new AdminRolePatchResponse(user);
        Mockito.when(adminService.patchRole(userId)).thenReturn(mockResponse);

        // when & then
        mockMvc.perform(patch("/admin/users/{id}/roles", userId)
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + expiredToken))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error.code").value(CommonErrorCode.JWT_EXPIRED.getErrorCode()))
            .andExpect(jsonPath("$.error.message").value(CommonErrorCode.JWT_EXPIRED.getMessage()));

    }

}