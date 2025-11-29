package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// ParticipationActivite.java
@Entity
@Table(name = "participation_activites")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParticipationActivite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activite_id", nullable = false)
    private Activite activite;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Utilisateur beneficiaire;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutActivite statut;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}