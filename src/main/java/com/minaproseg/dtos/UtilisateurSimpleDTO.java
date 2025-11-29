package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

// UtilisateurSimpleDTO.java (pour les listes)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UtilisateurSimpleDTO {
    private Long id;
    private String idBeneficiaire;
    private String prenom;
    private String nom;
    private String email;
    private ProfileRole profile;
    private StatutBeneficiaire statut;
    private String avatar;
    private String coachNom;
}