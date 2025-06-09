package com.sparta.authassignment.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.authassignment.common.exception.BaseException;
import com.sparta.authassignment.common.exception.CommonErrorCode;
import com.sparta.authassignment.user.dto.AdminRolePatchResponse;
import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	private final UserRepository userRepository;

	@Transactional
	public AdminRolePatchResponse patchRole(Long id) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new BaseException(CommonErrorCode.USER_NOT_EXISTS));

		user.updateUserRole();
		return new AdminRolePatchResponse(user);
	}
}
