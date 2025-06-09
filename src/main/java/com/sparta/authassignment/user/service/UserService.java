package com.sparta.authassignment.user.service;

import org.springframework.stereotype.Service;

import com.sparta.authassignment.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
}
