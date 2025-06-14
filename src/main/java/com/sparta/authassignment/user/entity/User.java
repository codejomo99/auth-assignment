package com.sparta.authassignment.user.entity;

import com.sparta.authassignment.user.dto.UserUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String nickName;

	@Column(name = "password", nullable = false)
	private String password;


	@Builder.Default
	@Enumerated(EnumType.STRING)
	private UserRole userRole = UserRole.USER;

	public void update(UserUpdateRequest userUpdateRequest) {
		this.nickName = userUpdateRequest.getNickName();
	}

	public void updateUserRole() {
		this.userRole = UserRole.ADMIN;
	}
}
