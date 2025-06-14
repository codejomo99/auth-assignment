package com.sparta.authassignment.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("백엔드 기술과제 API")
				.description("13기 바로인턴 기술과제 API 문서입니다.")
				.version("v1.0.0")
				.contact(new Contact()
					.email("codejomo99@gmail.com")
					.url("https://github.com/codejomo99/auth-assignment"))
				.license(new License()
					.name("Apache 2.0")
					.url("http://www.apache.org/licenses/LICENSE-2.0.html")))
			.servers(List.of(
				new Server().url("http://54.180.88.6").description("배포 서버"),
				new Server().url("http://localhost:8080").description("로컬")
			))
			.components(new Components()
				.addSecuritySchemes("bearerAuth",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
						.in(SecurityScheme.In.HEADER)
						.name("Authorization")))
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}