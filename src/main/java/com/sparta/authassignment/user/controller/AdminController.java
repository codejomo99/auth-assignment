package com.sparta.authassignment.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authassignment.user.dto.AdminRolePatchResponse;
import com.sparta.authassignment.user.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/admin/users")
@Tag(name = "ADMIN API", description = "관리자 API")
public class AdminController {

	private final AdminService adminService;

	@Operation(
		summary = "권한 변경",
		description = "사용자의 권한을 변경합니다."
	)
	@ApiResponse(responseCode = "201", description = "권한변경 성공")
	@PatchMapping("/{id}/roles")
	public ResponseEntity<AdminRolePatchResponse> patchRole(
		@PathVariable Long id
	){
		AdminRolePatchResponse response = adminService.patchRole(id);
		return ResponseEntity.ok(response);
	}

}
