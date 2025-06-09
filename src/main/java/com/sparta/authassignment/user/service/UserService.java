package com.sparta.authassignment.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.authassignment.common.exception.BaseException;
import com.sparta.authassignment.common.exception.CommonErrorCode;
import com.sparta.authassignment.user.dto.UserSignUpRequest;
import com.sparta.authassignment.user.dto.UserUpdateRequest;
import com.sparta.authassignment.user.entity.User;
import com.sparta.authassignment.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public void signUp(UserSignUpRequest requestDto) {

		if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
			throw new BaseException(CommonErrorCode.USER_ALREADY_EXISTS);
		}

		String passwordEncode = passwordEncoder.encode(requestDto.getPassword());

		userRepository.save(User.builder()
			.email(requestDto.getEmail())
			.password(passwordEncode)
			.nickName(requestDto.getNickName())
			.build());
	}

	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() ->
			new BaseException(CommonErrorCode.USER_NOT_EXISTS));
	}

	@Transactional
	public void updateUser(Long id, UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findById(id).orElseThrow(() ->
			new BaseException(CommonErrorCode.USER_NOT_EXISTS));

		user.update(userUpdateRequest);
	}

	@Transactional
	public void deleteUser(Long id) {
		userRepository.findById(id).orElseThrow(() ->
			new BaseException(CommonErrorCode.USER_NOT_EXISTS));

		userRepository.deleteById(id);
	}
}
