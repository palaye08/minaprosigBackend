package com.minaproseg.dtos;


import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ScoreDTO {
    private Long id;

    @NotNull(message = "Le bénéficiaire est obligatoire")
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotNull(message = "L'évaluateur est obligatoire")
    private Long evaluateurId;
    private String evaluateurNom;

    @NotNull @Min(0) @Max(10)
    private Double scoreGlobal;

    @NotNull @Min(0) @Max(10)
    private Double engagement;

    @NotNull @Min(0) @Max(10)
    private Double respectDelais;

    @NotNull @Min(0) @Max(10)
    private Double participation;

    @NotNull @Min(0) @Max(10)
    private Double evolutionBusiness;

    @NotNull @Min(0) @Max(10)
    private Double capaciteExecution;

    private String commentaire;
    private List<String> pointsForts;
    private List<String> pointsAmeliorer;
    private List<String> recommandations;
    private LocalDateTime createdAt;
}
