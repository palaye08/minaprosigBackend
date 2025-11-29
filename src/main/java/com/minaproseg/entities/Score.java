// Score.java
package com.minaproseg.entities;


import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Entity
@Table(name = "scores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Utilisateur beneficiaire;

    @ManyToOne
    @JoinColumn(name = "evaluateur_id", nullable = false)
    private Utilisateur evaluateur;

    @Column(nullable = false)
    private Double scoreGlobal;

    @Column(nullable = false)
    private Double engagement;

    @Column(nullable = false)
    private Double respectDelais;

    @Column(nullable = false)
    private Double participation;

    @Column(nullable = false)
    private Double evolutionBusiness;

    @Column(nullable = false)
    private Double capaciteExecution;

    @Column(columnDefinition = "TEXT")
    private String commentaire;

    @ElementCollection
    @CollectionTable(name = "score_points_forts", joinColumns = @JoinColumn(name = "score_id"))
    @Column(name = "point")
    private List<String> pointsForts;

    @ElementCollection
    @CollectionTable(name = "score_points_ameliorer", joinColumns = @JoinColumn(name = "score_id"))
    @Column(name = "point")
    private List<String> pointsAmeliorer;

    @ElementCollection
    @CollectionTable(name = "score_recommandations", joinColumns = @JoinColumn(name = "score_id"))
    @Column(name = "recommandation")
    private List<String> recommandations;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
}