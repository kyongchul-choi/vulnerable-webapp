-- 테이블이 없을 경우를 대비한 테이블 생성 쿼리
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20)
);

-- 초기 데이터 삽입
INSERT INTO users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@example.com', 'ADMIN'),
('user1', 'user123', 'user1@example.com', 'USER'),
('test', 'test123', 'test@example.com', 'USER');