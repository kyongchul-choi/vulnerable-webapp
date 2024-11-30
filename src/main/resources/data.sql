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

-- config_files 테이블 생성
CREATE TABLE IF NOT EXISTS config_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(100) NOT NULL,
    content TEXT,
    file_path VARCHAR(200),
    is_secret BOOLEAN DEFAULT false,
    is_encrypted BOOLEAN DEFAULT false,
    encryption_key VARCHAR(100),
    backup_date VARCHAR(8)
);

-- 일반 설정 파일
INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('general.properties', '# General Configuration\napp.name=MyApplication\napp.version=1.0.0\napp.environment=production\nlog.level=INFO\nmax.upload.size=10MB', '/config/general.properties', false, false, null, null);

-- 민감한 데이터베이스 설정
INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('database.properties', '# Database Configuration\ndb.url=jdbc:mysql://prod-server:3306/maindb\ndb.username=admin\ndb.password=super_secret_password_123\ndb.driver=com.mysql.cj.jdbc.Driver', '/config/database.properties', true, false, null, null);

-- 암호화된 API 키 설정
INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('secure.conf', '# Encrypted API Keys\naws.access.key=AKIA1234567890ABCDEF\naws.secret.key=abcdef1234567890ghijklmn/opqrstuvwxyz\npayment.api.key=pk_test_payment_123456789', '/config/secure.conf', true, true, 'secret123', null);

-- 백업 파일들
INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('database.properties.bak', '# Database Configuration (Old)\ndb.url=jdbc:mysql://old-server:3306/maindb\ndb.username=old_admin\ndb.password=old_password_123', '/config/backup/database.properties.bak', true, false, null, '20231129');

INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('api-keys.bak', '# Old API Keys\naws.old.key=AKIA9876543210ZYXWVU\npayment.old.key=pk_old_key_987654321', '/config/backup/api-keys.bak', true, false, null, '20231129');

-- secure.conf의 이전 백업 파일 (암호화 키 노출)
INSERT INTO config_files (file_name, content, file_path, is_secret, is_encrypted, encryption_key, backup_date)
VALUES ('secure.conf.bak','# Previous Security Configuration    # Production encryption key api.gateway.url=https://api.example.com # Note: Update this key every month','/config/backup/secure.conf.bak',true, false, null, '20231127');