package com.minaproseg.controllers;

import com.minaproseg.dtos.ApiResponse;
import com.minaproseg.dtos.AuthenticationResponse;
import com.minaproseg.services.AuthenticationService;
import com.minaproseg.services.UtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;

import java.util.List;


@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@Tag(name = "Utilisateurs", description = "Gestion des utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Liste des utilisateurs avec pagination")
    @PageableAsQueryParam
    public ResponseEntity<ApiResponse<PageResponse<UtilisateurSimpleDTO>>> getAllUtilisateurs(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        PageResponse<UtilisateurSimpleDTO> response = utilisateurService.getAllUtilisateurs(pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Liste des utilisateurs"));
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'un utilisateur")
    public ResponseEntity<ApiResponse<UtilisateurDTO>> getUtilisateurById(@PathVariable Long id) {
        UtilisateurDTO utilisateur = utilisateurService.getUtilisateurById(id);
        return ResponseEntity.ok(ApiResponse.success(utilisateur, "Utilisateur trouvé"));
    }

    @GetMapping("/profile/{profile}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Utilisateurs par profil")
    public ResponseEntity<ApiResponse<List<UtilisateurSimpleDTO>>> getUsersByProfile(
            @PathVariable ProfileRole profile) {
        List<UtilisateurSimpleDTO> users = utilisateurService.getUsersByProfile(profile);
        return ResponseEntity.ok(ApiResponse.success(users, "Utilisateurs par profil"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Recherche d'utilisateurs")
    public ResponseEntity<ApiResponse<PageResponse<UtilisateurSimpleDTO>>> searchUtilisateurs(
            @RequestParam(required = false) ProfileRole profile,
            @RequestParam(required = false) StatutBeneficiaire statut,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<UtilisateurSimpleDTO> response = utilisateurService.searchUtilisateurs(profile, statut, search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Résultats de recherche"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'BENEFICIAIRE')")
    @Operation(summary = "Modifier un utilisateur")
    public ResponseEntity<ApiResponse<UtilisateurDTO>> updateUtilisateur(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUtilisateurRequest request) {
        UtilisateurDTO updated = utilisateurService.updateUtilisateur(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Utilisateur modifié"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un utilisateur")
    public ResponseEntity<ApiResponse<Void>> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Utilisateur supprimé"));
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Changer le mot de passe")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        utilisateurService.changePassword(id, request);
        return ResponseEntity.ok(ApiResponse.success(null, "Mot de passe modifié"));
    }
}
