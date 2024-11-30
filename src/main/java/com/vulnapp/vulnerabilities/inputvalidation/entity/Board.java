// src/main/java/com/vulnapp/vulnerabilities/inputvalidation/entity/Board.java
package com.vulnapp.vulnerabilities.inputvalidation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "input_validation_boards")    // 테이블 이름도 변경
@Getter @Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private InputValidationUser user;    // User를 InputValidationUser로 변경
}