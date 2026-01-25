# School Microservices (Eureka + HS256)

Microservices demo gồm: `eureka-server`, `api-gateway`, `auth-service`, `student-service`, `point-service`.
- Mỗi service dùng **PostgreSQL riêng**.
- **Eureka** dùng cho service discovery.
- **JWT HS256** dùng chung secret (qua biến môi trường `JWT_SECRET`).
- Tất cả request đi qua **API Gateway**.

## Cách chạy (Docker)
```bash
docker compose build
docker compose up -d
```

Gateway: http://localhost:8080  
Eureka:  http://localhost:8761

Luồng nhanh:
1. Gọi `POST /api/auth/login` qua gateway để lấy `accessToken` + `refreshToken`.
2. Gọi các API `/student/**`, `/point/**` qua gateway với header `Authorization: Bearer <accessToken>`.
3. `POST /api/auth/refresh` để lấy access token mới.

Tài khoản seed mặc định (auth-service):
- admin / admin123 (role ADMIN, scope ALL)
- manager1 / manager123 (role SCHOOL_MANAGER, scope SCHOOL, schoolId = 100)
- student1 / student123 (role STUDENT, scope SELF, schoolId = 100)

