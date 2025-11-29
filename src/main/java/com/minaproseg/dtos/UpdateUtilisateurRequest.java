package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


// UpdateUtilisateurRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUtilisateurRequest {
    private String prenom;
    private String nom;
    private String telephone;
    private String telephoneSecondaire;
    private String adresse;
    private String ville;
    private String niveauEducation;
    private String situationProfessionnelle;
    private StatutBeneficiaire statut;
    private String motifSortie;
    private String nomEntreprise;
    private String secteurActivite;
    private String statutJuridique;
    private Integer anneeCreation;
    private String niveauAvancement;
    private Integer nombreEmployesPermanents;
    private Integer nombreEmployesTemporaires;
    private Long coachId;
}