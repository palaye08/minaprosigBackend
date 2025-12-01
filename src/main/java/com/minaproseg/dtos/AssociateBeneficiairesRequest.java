package com.minaproseg.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// AssociateBeneficiairesRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociateBeneficiairesRequest {
    @NotNull(message = "La liste des bénéficiaires est obligatoire")
    @NotEmpty(message = "Vous devez sélectionner au moins un bénéficiaire")
    private List<Long> beneficiaireIds;
}