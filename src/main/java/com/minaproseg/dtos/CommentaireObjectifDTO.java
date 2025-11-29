package com.minaproseg.dtos;


import com.minaproseg.enums.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentaireObjectifDTO {
    private Long id;
    private Long objectifId;
    private Long auteurId;
    private String auteurNom;

    @NotBlank(message = "Le texte est obligatoire")
    private String texte;

    private LocalDateTime createdAt;
}
