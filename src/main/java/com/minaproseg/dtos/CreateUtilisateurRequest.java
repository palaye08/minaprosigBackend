// CreateUtilisateurRequest.java


package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUtilisateurRequest {
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @NotNull(message = "Le profil est obligatoire")
    private ProfileRole profile;

    private Genre genre;
    private LocalDate dateNaissance;
    private String telephone;
    private String adresse;
    private String pays;
    private String region;
    private String ville;
    private Zone zone;
    private String niveauEducation;
    private String situationProfessionnelle;
    private String programme;
    private String bailleur;
    private Long coachId;
}
