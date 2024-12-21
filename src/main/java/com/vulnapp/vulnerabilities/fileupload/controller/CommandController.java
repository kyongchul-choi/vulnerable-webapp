package com.vulnapp.vulnerabilities.fileupload.controller;

import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class CommandController {

    @PostMapping("/execute")
    public String executeCommand(@RequestBody CommandRequest request) {
        try {
            ProcessBuilder pb = new ProcessBuilder();

            // Windows 환경에서의 명령어 실행
            pb.command("cmd.exe", "/c", request.getCommand());
            pb.redirectErrorStream(true);  // 에러 스트림을 표준 출력으로 리다이렉트

            Process process = pb.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), "MS949")  // Windows 인코딩
            );

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor(10, TimeUnit.SECONDS);  // 타임아웃 설정

            return output.toString();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

class CommandRequest {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}