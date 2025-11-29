package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
// IndicateurFinancier.java
@Entity
@Table(name = "indicateurs_financiers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class IndicateurFinancier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Utilisateur beneficiaire;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Integer mois;

    @Column(nullable = false)
    private Double chiffreAffaires;

    @Column(nullable = false)
    private Double depenses;

    @Column(nullable = false)
    private Double marge;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (marge == null && chiffreAffaires != null && depenses != null) {
            marge = ((chiffreAffaires - depenses) / chiffreAffaires) * 100;
        }
    }
}