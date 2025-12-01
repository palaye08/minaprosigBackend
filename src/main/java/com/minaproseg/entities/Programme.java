package com.minaproseg.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "programmes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Programme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 255)
    private String partenaire;

    @Column(nullable = false)
    private Double montantTotal;

    @Column(columnDefinition = "TEXT")
    private String activites;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(length = 50)
    @Builder.Default
    private String statut = "ACTIF";

    // ✅ CHANGEMENT ICI : Ajout de fetch = FetchType.EAGER
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "programme_coachs",
            joinColumns = @JoinColumn(name = "programme_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    @Builder.Default
    private List<Utilisateur> coachs = new ArrayList<>();

    // ✅ CHANGEMENT ICI : Ajout de fetch = FetchType.EAGER
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "programme_beneficiaires",
            joinColumns = @JoinColumn(name = "programme_id"),
            inverseJoinColumns = @JoinColumn(name = "beneficiaire_id")
    )
    @Builder.Default
    private List<Utilisateur> beneficiaires = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (statut == null) {
            statut = "ACTIF";
        }
        if (coachs == null) {
            coachs = new ArrayList<>();
        }
        if (beneficiaires == null) {
            beneficiaires = new ArrayList<>();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}