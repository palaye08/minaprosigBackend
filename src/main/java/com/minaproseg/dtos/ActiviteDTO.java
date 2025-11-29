package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// ActiviteDTO.java
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ActiviteDTO {
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotNull(message = "L'heure de début est obligatoire")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est obligatoire")
    private LocalTime heureFin;

    @NotNull(message = "Le type est obligatoire")
    private TypeActivite type;

    @NotNull(message = "La modalité est obligatoire")
    private ModaliteActivite modalite;

    private String formateur;
    private String lieu;
    private Integer nombreParticipants;
    private String commentaire;
    private List<Long> participantIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}