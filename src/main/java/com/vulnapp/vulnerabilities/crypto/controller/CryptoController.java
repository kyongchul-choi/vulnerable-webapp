// CryptoController.java
package com.vulnapp.vulnerabilities.crypto.controller;

import com.vulnapp.vulnerabilities.crypto.dto.CryptoTransactionDTO;
import com.vulnapp.vulnerabilities.crypto.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/vulnerable/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;

    // ECB 취약점 학습 페이지
    @GetMapping("/ecb/learn")
    public String ecbLearnPage(Model model) {
        try {
            log.info("ECB Learn page requested");

            List<CryptoTransactionDTO> transactions = new ArrayList<>();
            transactions.add(cryptoService.createTransaction("송금", "100만원", "홍길동"));
            transactions.add(cryptoService.createTransaction("송금", "100만원", "김철수"));
            transactions.add(cryptoService.createTransaction("출금", "100만원", "홍길동"));

            model.addAttribute("transactions", transactions);
            log.info("Transactions added to model: {}", transactions.size());

            return "crypto/ecb-learn";
        } catch (Exception e) {
            log.error("Error in ecbLearnPage: ", e);
            throw e;
        }
    }

    // 암호화 모드 비교 페이지
    @GetMapping("/comparison")
    public String comparisonPage(Model model) {
        try {
            log.info("Encryption comparison page requested");

            List<CryptoTransactionDTO> transactions = new ArrayList<>();

            // 동일한 데이터로 여러 거래 생성
            transactions.add(cryptoService.createTransaction("송금", "100만원", "홍길동"));
            transactions.add(cryptoService.createTransaction("송금", "100만원", "김철수"));
            // 같은 사람, 다른 금액
            transactions.add(cryptoService.createTransaction("송금", "200만원", "홍길동"));
            // 완전히 같은 데이터로 한번 더 생성 (GCM의 차이를 보여주기 위해)
            transactions.add(cryptoService.createTransaction("송금", "100만원", "홍길동"));

            model.addAttribute("transactions", transactions);
            return "crypto/mode-comparison";
        } catch (Exception e) {
            log.error("Error in comparison page: ", e);
            throw e;
        }
    }

    // ECB 패턴 분석 실습
    @PostMapping("/ecb/analyze")
    @ResponseBody
    public CryptoTransactionDTO analyzePattern(
            @RequestParam String transactionType,
            @RequestParam String amount,
            @RequestParam String userName) {

        return cryptoService.createTransaction(transactionType, amount, userName);
    }

    // 암호화 결과 확인
    @PostMapping("/encrypt")
    @ResponseBody
    public CryptoTransactionDTO encryptData(
            @RequestParam String transactionType,
            @RequestParam String amount,
            @RequestParam String userName) {
        log.info("Encryption requested for: {}, {}, {}", transactionType, amount, userName);
        return cryptoService.createTransaction(transactionType, amount, userName);
    }

    // 에러 처리
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleError(Exception e) {
        log.error("Error occurred: ", e);
        return "Error: " + e.getMessage();
    }
}