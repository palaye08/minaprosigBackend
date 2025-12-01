package com.minaproseg.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

// UpdateProgrammeRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProgrammeRequest {
    private String nom;
    private String description;
    private String partenaire;
    private Double montantTotal;
    private String activites;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private List<Long> coachIds;
}