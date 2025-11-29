package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// UtilisateurDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurDTO {
    private Long id;
    private String idBeneficiaire;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotNull(message = "Le profil est obligatoire")
    private ProfileRole profile;

    private Genre genre;
    private LocalDate dateNaissance;
    private String telephone;
    private String telephoneSecondaire;
    private String adresse;
    private String pays;
    private String region;
    private String ville;
    private Zone zone;
    private String niveauEducation;
    private String situationProfessionnelle;
    private LocalDate dateEnrolement;
    private String programme;
    private String bailleur;
    private StatutBeneficiaire statut;
    private String motifSortie;

    // Infos entreprise
    private String nomEntreprise;
    private String secteurActivite;
    private String statutJuridique;
    private Integer anneeCreation;
    private String niveauAvancement;
    private Integer nombreEmployesPermanents;
    private Integer nombreEmployesTemporaires;

    // Relations
    private Long coachId;
    private String coachNom;
    private String avatar;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}