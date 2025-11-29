// CommentaireObjectif.java
package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "commentaires_objectifs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentaireObjectif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objectif_id", nullable = false)
    private Objectif objectif;

    @ManyToOne
    @JoinColumn(name = "auteur_id", nullable = false)
    private Utilisateur auteur;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String texte;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
}
