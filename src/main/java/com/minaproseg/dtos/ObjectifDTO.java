package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ObjectifDTO {
    private Long id;

    @NotNull(message = "Le bénéficiaire est obligatoire")
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieObjectif categorie;

    private String indicateur;

    @NotNull(message = "La date de début est obligatoire")
    private LocalDate dateDebut;

    @NotNull(message = "La date cible est obligatoire")
    private LocalDate dateCible;

    @NotNull(message = "Le statut est obligatoire")
    private StatutObjectif statut;

    @Min(0) @Max(100)
    private Integer progress;

    private List<CommentaireObjectifDTO> commentaires;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

