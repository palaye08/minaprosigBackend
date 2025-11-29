package com.minaproseg.controllers;

import com.minaproseg.dtos.ApiResponse;
import com.minaproseg.dtos.AuthenticationResponse;
import com.minaproseg.services.ActiviteService;
import com.minaproseg.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/activites")
@RequiredArgsConstructor
@Tag(name = "Activités", description = "Gestion des activités")
public class ActiviteController {
    private final ActiviteService activiteService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Créer une activité")
    public ResponseEntity<ApiResponse<ActiviteDTO>> createActivite(@Valid @RequestBody ActiviteDTO dto) {
        ActiviteDTO created = activiteService.createActivite(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Activité créée"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'une activité")
    public ResponseEntity<ApiResponse<ActiviteDTO>> getActiviteById(@PathVariable Long id) {
        ActiviteDTO activite = activiteService.getActiviteById(id);
        return ResponseEntity.ok(ApiResponse.success(activite, "Activité trouvée"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Activités d'un bénéficiaire")
    public ResponseEntity<ApiResponse<PageResponse<ActiviteDTO>>> getActivitesByBeneficiaire(
            @PathVariable Long beneficiaireId,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<ActiviteDTO> response = activiteService.getActivitesByBeneficiaire(beneficiaireId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Activités du bénéficiaire"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Recherche d'activités")
    public ResponseEntity<ApiResponse<PageResponse<ActiviteDTO>>> searchActivites(
            @RequestParam(required = false) TypeActivite type,
            @RequestParam(required = false) ModaliteActivite modalite,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<ActiviteDTO> response = activiteService.searchActivites(type, modalite, startDate, endDate, search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Résultats de recherche"));
    }
}
