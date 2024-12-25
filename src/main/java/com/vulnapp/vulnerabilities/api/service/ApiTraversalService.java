package com.vulnapp.vulnerabilities.api.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import org.springframework.stereotype.Service;


@Service
public class ApiTraversalService {
    private final String DEST_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    public String processZipFile(File uploadedFile) {
        try {
            System.out.println("업로드된 파일 경로: " + uploadedFile.getAbsolutePath());
            System.out.println("대상 디렉토리 경로: " + DEST_DIR);
            extractZipFile(uploadedFile.getAbsolutePath(), DEST_DIR);
            return "ZIP 파일이 성공적으로 처리되었습니다.";
        } catch (IOException e) {
            e.printStackTrace();
            return "ZIP 파일 처리 중 오류 발생했습니다: " + e.getMessage();
        }
    }

    public void extractZipFile(String zipFilePath, String destDir) throws IOException {
        System.out.println("ZIP 파일 추출 시작 - 파일 경로: " + zipFilePath);
        ZipFile zipFile = new ZipFile(new File(zipFilePath));
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();

        while (entries.hasMoreElements()) {
            ZipArchiveEntry entry = entries.nextElement();
            File entryFile = new File(destDir, entry.getName());

            System.out.println("처리중인 엔트리: " + entry.getName());
            System.out.println("대상 파일 경로: " + entryFile.getAbsolutePath());
            System.out.println("정규화된 경로: " + entryFile.getCanonicalPath());

            if (entry.isDirectory()) {
                System.out.println("디렉토리 생성: " + entryFile.getAbsolutePath());
                entryFile.mkdirs();
            } else {
                System.out.println("파일 생성 시도: " + entryFile.getAbsolutePath());
                entryFile.getParentFile().mkdirs();
                try (InputStream inputStream = zipFile.getInputStream(entry);
                     OutputStream outputStream = Files.newOutputStream(entryFile.toPath())) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                    System.out.println("파일 생성 완료: " + entryFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("파일 생성 실패: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("ZIP 파일 추출 완료");
    }
}