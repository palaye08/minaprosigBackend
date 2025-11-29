package com.minaproseg.entities;

import com.minaproseg.enums.CategorieObjectif;
import com.minaproseg.enums.StatutObjectif;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "objectifs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Objectif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Utilisateur beneficiaire;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategorieObjectif categorie;

    @Column(length = 255)
    private String indicateur;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateCible;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutObjectif statut;

    @Builder.Default  // ✅ Ajoutez cette annotation
    @Column(nullable = false)
    private Integer progress = 0;

    @OneToMany(mappedBy = "objectif", cascade = CascadeType.ALL)
    private List<CommentaireObjectif> commentaires;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (statut == null) statut = StatutObjectif.EN_COURS;
        if (progress == null) progress = 0;  // ✅ Ajoutez cette ligne
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}