package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ParticipationActiviteDTO {
    private Long id;
    private Long activiteId;
    private String activiteTitre;
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotNull(message = "Le statut est obligatoire")
    private StatutActivite statut;

    private String commentaire;
    private LocalDateTime createdAt;
}