package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "activites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeActivite type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ModaliteActivite modalite;

    @Column(length = 255)
    private String formateur;

    @Column(length = 255)
    private String lieu;

    private Integer nombreParticipants;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @ManyToMany
    @JoinTable(
            name = "activite_beneficiaires",
            joinColumns = @JoinColumn(name = "activite_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    private List<Utilisateur> participants;

    @OneToMany(mappedBy = "activite", cascade = CascadeType.ALL)
    private List<ParticipationActivite> participations;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}