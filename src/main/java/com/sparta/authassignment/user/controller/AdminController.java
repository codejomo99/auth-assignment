package com.sparta.authassignment.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.authassignment.user.dto.AdminRolePatchResponse;
import com.sparta.authassignment.user.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminController {

	private final AdminService adminService;

	@PatchMapping("/{id}/roles")
	public ResponseEntity<AdminRolePatchResponse> patchRole(
		@PathVariable Long id
	){
		AdminRolePatchResponse response = adminService.patchRole(id);
		return ResponseEntity.ok(response);
	}

}
