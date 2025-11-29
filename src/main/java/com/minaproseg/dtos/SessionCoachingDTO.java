package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SessionCoachingDTO {
    private Long id;

    @NotNull(message = "Le bénéficiaire est obligatoire")
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotNull(message = "Le coach est obligatoire")
    private Long coachId;
    private String coachNom;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    @NotNull(message = "La modalité est obligatoire")
    private ModaliteActivite modalite;

    @NotNull(message = "Le statut est obligatoire")
    private StatutCoaching statut;

    private String objectif;
    private String situationInitiale;
    private List<String> themesAbordes;
    private List<String> actionsBeneficiaire;
    private List<String> actionsCoach;
    private List<String> difficultes;
    private LocalDate prochaineSession;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}