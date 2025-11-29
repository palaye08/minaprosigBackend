package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentDTO {
    private Long id;

    @NotNull(message = "Le bénéficiaire est obligatoire")
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotNull(message = "La catégorie est obligatoire")
    private CategorieDocument categorie;

    @NotBlank(message = "Le type est obligatoire")
    private String type;

    private String taille;
    private String cheminFichier;

    @NotNull(message = "Le statut est obligatoire")
    private StatutDocument statut;

    private List<String> tags;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


