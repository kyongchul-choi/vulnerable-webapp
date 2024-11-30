-- input validation 테이블 생성
CREATE TABLE IF NOT EXISTS input_validation_users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   email VARCHAR(100),
   role VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS input_validation_boards (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(200) NOT NULL,
   content TEXT,
   user_id BIGINT,
   FOREIGN KEY (user_id) REFERENCES input_validation_users(id)
);

-- input validation 테스트 사용자 데이터
INSERT INTO input_validation_users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@example.com', 'ADMIN'),
('user1', 'user123', 'user1@example.com', 'USER'),
('user2', 'user123', 'user2@example.com', 'USER');

-- input validation 테스트 게시글 데이터
INSERT INTO input_validation_boards (title, content, user_id) VALUES
('User1의 게시글', '일반 사용자가 작성한 게시글입니다.', 2),
('Admin의 공지사항', '관리자가 작성한 공지사항입니다.', 1),
('User2의 게시글', '다른 사용자가 작성한 게시글입니다.', 3);

-- 기존 SQL Injection 테이블 및 데이터는 유지
CREATE TABLE IF NOT EXISTS users (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   username VARCHAR(50) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   email VARCHAR(100),
   role VARCHAR(20)
);

INSERT INTO users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@example.com', 'ADMIN'),
('user1', 'user123', 'user1@example.com', 'USER'),
('test', 'test123', 'test@example.com', 'USER');