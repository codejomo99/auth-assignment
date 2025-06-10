# Spring Boot 백엔드 과제

Spring Boot 기반으로 **JWT 인증 및 인가**, **역할 기반 접근 제어**, **Swagger UI 문서화**, **JUnit 테스트**, **GitHub Actions 기반 CI/CD**, **AWS EC2 배포**까지 구현한 백엔드 과제입니다.

## 📚 요구사항 기능
-[x] JWT 기반 회원가입 / 로그인 / 권한 부여 API 개발
-[x] 역할(Role) 기반 관리자 접근 제어
-[x] Swagger UI를 통한 API 문서화
-[x] JUnit 기반 테스트 작성
-[x] AWS EC2 배포 및 Nginx 리버스 프록시 구성

## ➕ 추가 기능
-[x] 유저 CRUD
-[x] GiHub Actions 기반 CI/CD 자동화





## 🛠 기술 스택

- Java 17
- Spring Boot 3
- Spring Security + JWT
- Swagger (springdoc-openapi)
- H2
- JUnit 5
- Gradle
- AWS EC2 (Amazon Linux 2023)


## 🔐 주요 API

### 회원가입

- POST `/signup`
```json
{
  "username": "JIN HO",
  "password": "12341234",
  "nickname": "Mentos"
}
```

### 로그인

- POST `/login`
```json
{
  "username": "JIN HO",
  "password": "12341234"
}
```

응답 예시:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### 관리자 권한 부여

- PATCH `/admin/users/{userId}/roles`

응답 예시:
```json
{
  "username": "JIN HO",
  "nickname": "Mentos",
  "roles": [
    {
      "role": "Admin"
    }
  ]
}
```


## 🧪 테스트

- JUnit 기반 테스트 작성
```bash
./gradlew test
```


## 📘 Swagger 문서

- Swagger UI: `http://54.180.88.6/swagger-ui/index.html#/`


## ☁ 배포 정보

- EC2 주소: `http://54.180.88.6`
- 서버 환경:
    - Amazon Linux 2023
    - OpenJDK 17 (Corretto)
    - Nginx


## 📁 프로젝트 구조
```
src
├── config
├── controller
├── dto
├── entity
├── repository
├── security
├── service
└── test
```


