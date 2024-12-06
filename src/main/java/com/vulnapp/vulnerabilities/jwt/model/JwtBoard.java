// Board.java (Entity)
package com.vulnapp.vulnerabilities.jwt.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "boards")
@Getter @Setter
public class JwtBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    private JwtUser writer;

    private LocalDateTime createdAt;
}
