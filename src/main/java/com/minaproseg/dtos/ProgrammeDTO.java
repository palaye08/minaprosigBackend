package com.minaproseg.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

// ProgrammeDTO.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgrammeDTO {
    private Long id;

    @NotBlank(message = "Le nom du programme est obligatoire")
    private String nom;

    private String description;

    @NotBlank(message = "Le partenaire est obligatoire")
    private String partenaire;

    @NotNull(message = "Le montant total est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montantTotal;

    private String activites;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date de fin est obligatoire")
    private LocalDate dateFin;

    private String statut;

    private List<Long> coachIds;
    private List<UtilisateurSimpleDTO> coachs;

    private List<Long> beneficiaireIds;
    private List<UtilisateurSimpleDTO> beneficiaires;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}




