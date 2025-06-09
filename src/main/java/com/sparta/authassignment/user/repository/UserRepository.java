package com.sparta.authassignment.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.authassignment.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
