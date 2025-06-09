package com.sparta.authassignment.user.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sparta.authassignment.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
}
