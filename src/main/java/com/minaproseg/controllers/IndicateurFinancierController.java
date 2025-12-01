// ===== INDICATEUR FINANCIER CONTROLLER =====
package com.minaproseg.controllers;

import com.minaproseg.services.IndicateurFinancierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;
import com.minaproseg.dtos.*;
import java.util.List;



@RestController
@RequestMapping("/api/indicateurs-financiers")
@RequiredArgsConstructor
@Tag(name = "Indicateurs Financiers", description = "Gestion des indicateurs financiers")
public class IndicateurFinancierController {
    private final IndicateurFinancierService indicateurService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Créer un indicateur financier")
    public ResponseEntity<ApiResponse<IndicateurFinancierDTO>> createIndicateur(
            @Valid @RequestBody IndicateurFinancierDTO dto) {
        IndicateurFinancierDTO created = indicateurService.createIndicateur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Indicateur créé"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'un indicateur")
    public ResponseEntity<ApiResponse<IndicateurFinancierDTO>> getIndicateurById(@PathVariable Long id) {
        IndicateurFinancierDTO indicateur = indicateurService.getIndicateurById(id);
        return ResponseEntity.ok(ApiResponse.success(indicateur, "Indicateur trouvé"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Indicateurs d'un bénéficiaire")
    public ResponseEntity<ApiResponse<List<IndicateurFinancierDTO>>> getIndicateursByBeneficiaire(
            @PathVariable Long beneficiaireId) {
        List<IndicateurFinancierDTO> indicateurs = indicateurService.getIndicateursByBeneficiaire(beneficiaireId);
        return ResponseEntity.ok(ApiResponse.success(indicateurs, "Indicateurs du bénéficiaire"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}/annee/{annee}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Indicateurs d'un bénéficiaire par année")
    public ResponseEntity<ApiResponse<List<IndicateurFinancierDTO>>> getIndicateursByYear(
            @PathVariable Long beneficiaireId,
            @PathVariable Integer annee) {
        List<IndicateurFinancierDTO> indicateurs = indicateurService.getIndicateursByBeneficiaireAndYear(beneficiaireId, annee);
        return ResponseEntity.ok(ApiResponse.success(indicateurs, "Indicateurs de l'année"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}/total-ca")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Chiffre d'affaires total d'un bénéficiaire")
    public ResponseEntity<ApiResponse<Double>> getTotalCA(@PathVariable Long beneficiaireId) {
        Double totalCA = indicateurService.getTotalCAByBeneficiaire(beneficiaireId);
        return ResponseEntity.ok(ApiResponse.success(totalCA, "Chiffre d'affaires total"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Modifier un indicateur")
    public ResponseEntity<ApiResponse<IndicateurFinancierDTO>> updateIndicateur(
            @PathVariable Long id,
            @Valid @RequestBody IndicateurFinancierDTO dto) {
        IndicateurFinancierDTO updated = indicateurService.updateIndicateur(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Indicateur modifié"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Supprimer un indicateur")
    public ResponseEntity<ApiResponse<Void>> deleteIndicateur(@PathVariable Long id) {
        indicateurService.deleteIndicateur(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Indicateur supprimé"));
    }
}

