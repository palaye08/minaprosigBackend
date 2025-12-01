package com.minaproseg.controllers;

import com.minaproseg.dtos.*;
import com.minaproseg.services.ProgrammeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/programmes")
@RequiredArgsConstructor
@Tag(name = "Programmes", description = "Gestion des programmes")
public class ProgrammeController {
    private final ProgrammeService programmeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Créer un programme")
    public ResponseEntity<ApiResponse<ProgrammeDTO>> createProgramme(
            @Valid @RequestBody CreateProgrammeRequest request) {
        ProgrammeDTO created = programmeService.createProgramme(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Programme créé avec succès"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Détails d'un programme")
    public ResponseEntity<ApiResponse<ProgrammeDTO>> getProgrammeById(@PathVariable Long id) {
        ProgrammeDTO programme = programmeService.getProgrammeById(id);
        return ResponseEntity.ok(ApiResponse.success(programme, "Programme trouvé"));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Liste des programmes avec pagination")
    public ResponseEntity<ApiResponse<PageResponse<ProgrammeDTO>>> getAllProgrammes(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<ProgrammeDTO> response = programmeService.getAllProgrammes(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Liste des programmes"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Recherche de programmes")
    public ResponseEntity<ApiResponse<PageResponse<ProgrammeDTO>>> searchProgrammes(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String partenaire,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(hidden = true) @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<ProgrammeDTO> response = programmeService.searchProgrammes(
                nom, partenaire, statut, startDate, endDate, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Résultats de recherche"));
    }

    @GetMapping("/actifs")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Liste des programmes actifs")
    public ResponseEntity<ApiResponse<List<ProgrammeDTO>>> getActiveProgrammes() {
        List<ProgrammeDTO> programmes = programmeService.getActiveProgrammes();
        return ResponseEntity.ok(ApiResponse.success(programmes, "Programmes actifs"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Modifier un programme")
    public ResponseEntity<ApiResponse<ProgrammeDTO>> updateProgramme(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProgrammeRequest request) {
        ProgrammeDTO updated = programmeService.updateProgramme(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Programme modifié"));
    }

    @PostMapping("/{id}/beneficiaires")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Associer des bénéficiaires à un programme")
    public ResponseEntity<ApiResponse<ProgrammeDTO>> associateBeneficiaires(
            @PathVariable Long id,
            @Valid @RequestBody AssociateBeneficiairesRequest request) {
        ProgrammeDTO updated = programmeService.associateBeneficiaires(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Bénéficiaires associés"));
    }

    @DeleteMapping("/{programmeId}/beneficiaires/{beneficiaireId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Retirer un bénéficiaire")
    public ResponseEntity<ApiResponse<ProgrammeDTO>> removeBeneficiaire(
            @PathVariable Long programmeId,
            @PathVariable Long beneficiaireId) {
        ProgrammeDTO updated = programmeService.removeBeneficiaire(programmeId, beneficiaireId);
        return ResponseEntity.ok(ApiResponse.success(updated, "Bénéficiaire retiré"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un programme")
    public ResponseEntity<ApiResponse<Void>> deleteProgramme(@PathVariable Long id) {
        programmeService.deleteProgramme(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Programme supprimé"));
    }
}