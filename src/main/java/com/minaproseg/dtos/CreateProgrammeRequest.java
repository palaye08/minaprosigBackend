package com.minaproseg.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// CreateProgrammeRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProgrammeRequest {
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

    private List<Long> coachIds;
}
