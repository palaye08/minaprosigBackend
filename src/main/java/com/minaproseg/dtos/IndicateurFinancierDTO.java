package com.minaproseg.dtos;

import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;



@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IndicateurFinancierDTO {
    private Long id;

    @NotNull(message = "Le bénéficiaire est obligatoire")
    private Long beneficiaireId;
    private String beneficiaireNom;

    @NotNull @Min(2020) @Max(2100)
    private Integer annee;

    @NotNull @Min(1) @Max(12)
    private Integer mois;

    @NotNull @Min(0)
    private Double chiffreAffaires;

    @NotNull @Min(0)
    private Double depenses;

    private Double marge;
    private LocalDateTime createdAt;
}