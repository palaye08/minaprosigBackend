package com.minaproseg.entities;

import com.minaproseg.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String idBeneficiaire; // Format: BEN-2025-001

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProfileRole profile;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private LocalDate dateNaissance;

    @Column(length = 20)
    private String telephone;

    @Column(length = 20)
    private String telephoneSecondaire;

    @Column(length = 255)
    private String adresse;

    @Column(length = 100)
    private String pays;

    @Column(length = 100)
    private String region;

    @Column(length = 100)
    private String ville;

    @Enumerated(EnumType.STRING)
    private Zone zone;

    @Column(length = 100)
    private String niveauEducation;

    @Column(length = 100)
    private String situationProfessionnelle;

    private LocalDate dateEnrolement;

    @Column(length = 255)
    private String programme;

    @Column(length = 255)
    private String bailleur;

    @Enumerated(EnumType.STRING)
    private StatutBeneficiaire statut;

    @Column(length = 500)
    private String motifSortie;

    // Informations entrepreneuriales (si bénéficiaire entrepreneur)
    @Column(length = 255)
    private String nomEntreprise;

    @Column(length = 255)
    private String secteurActivite;

    @Column(length = 100)
    private String statutJuridique;

    private Integer anneeCreation;

    @Column(length = 100)
    private String niveauAvancement;

    private Integer nombreEmployesPermanents;

    private Integer nombreEmployesTemporaires;

    // Coach assigné (pour les bénéficiaires)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Utilisateur coach;

    // Bénéficiaires suivis (pour les coachs)
    @OneToMany(mappedBy = "coach")
    private List<Utilisateur> beneficiaires;

    @Column(length = 255)
    private String avatar;

    @Builder.Default  // ✅ Ajoutez cette annotation avant enabled
    private Boolean enabled = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (statut == null && profile == ProfileRole.BENEFICIAIRE) {
            statut = StatutBeneficiaire.ACTIF;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Méthodes UserDetails pour Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + profile.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}