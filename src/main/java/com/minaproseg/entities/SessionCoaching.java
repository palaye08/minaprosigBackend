// SessionCoaching.java

package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sessions_coaching")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionCoaching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Utilisateur beneficiaire;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Utilisateur coach;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModaliteActivite modalite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCoaching statut;

    @Column(columnDefinition = "TEXT")
    private String objectif;

    @Column(columnDefinition = "TEXT")
    private String situationInitiale;

    @ElementCollection
    @CollectionTable(name = "coaching_themes", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "theme")
    private List<String> themesAbordes;

    @ElementCollection
    @CollectionTable(name = "coaching_actions_beneficiaire", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "action")
    private List<String> actionsBeneficiaire;

    @ElementCollection
    @CollectionTable(name = "coaching_actions_coach", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "action")
    private List<String> actionsCoach;

    @ElementCollection
    @CollectionTable(name = "coaching_difficultes", joinColumns = @JoinColumn(name = "session_id"))
    @Column(name = "difficulte")
    private List<String> difficultes;

    private LocalDate prochaineSession;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}
