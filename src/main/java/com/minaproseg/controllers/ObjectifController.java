
// ===== OBJECTIF CONTROLLER =====
package com.minaproseg.controllers;

import com.minaproseg.services.ObjectifService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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


@RestController
@RequestMapping("/api/objectifs")
@RequiredArgsConstructor
@Tag(name = "Objectifs", description = "Gestion des objectifs")
public class ObjectifController {
    private final ObjectifService objectifService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Créer un objectif")
    public ResponseEntity<ApiResponse<ObjectifDTO>> createObjectif(@Valid @RequestBody ObjectifDTO dto) {
        ObjectifDTO created = objectifService.createObjectif(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Objectif créé"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'un objectif")
    public ResponseEntity<ApiResponse<ObjectifDTO>> getObjectifById(@PathVariable Long id) {
        ObjectifDTO objectif = objectifService.getObjectifById(id);
        return ResponseEntity.ok(ApiResponse.success(objectif, "Objectif trouvé"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Objectifs d'un bénéficiaire")
    public ResponseEntity<ApiResponse<PageResponse<ObjectifDTO>>> getObjectifsByBeneficiaire(
            @PathVariable Long beneficiaireId,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<ObjectifDTO> response = objectifService.getObjectifsByBeneficiaire(beneficiaireId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Objectifs du bénéficiaire"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Modifier un objectif")
    public ResponseEntity<ApiResponse<ObjectifDTO>> updateObjectif(
            @PathVariable Long id,
            @Valid @RequestBody ObjectifDTO dto) {
        ObjectifDTO updated = objectifService.updateObjectif(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Objectif modifié"));
    }

    @PostMapping("/{objectifId}/commentaires")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Ajouter un commentaire")
    public ResponseEntity<ApiResponse<CommentaireObjectifDTO>> addCommentaire(
            @PathVariable Long objectifId,
            @Valid @RequestBody CommentaireObjectifDTO dto) {
        CommentaireObjectifDTO created = objectifService.addCommentaire(objectifId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Commentaire ajouté"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Supprimer un objectif")
    public ResponseEntity<ApiResponse<Void>> deleteObjectif(@PathVariable Long id) {
        objectifService.deleteObjectif(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Objectif supprimé"));
    }
}
