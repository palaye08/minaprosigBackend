
// ===== SESSION COACHING CONTROLLER =====
package com.minaproseg.controllers;

import com.minaproseg.dtos.*;
import com.minaproseg.services.SessionCoachingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions-coaching")
@RequiredArgsConstructor
@Tag(name = "Sessions de Coaching", description = "Gestion des sessions de coaching")
public class SessionCoachingController {
    private final SessionCoachingService sessionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Créer une session de coaching")
    public ResponseEntity<ApiResponse<SessionCoachingDTO>> createSession(@Valid @RequestBody SessionCoachingDTO dto) {
        SessionCoachingDTO created = sessionService.createSession(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Session créée"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'une session")
    public ResponseEntity<ApiResponse<SessionCoachingDTO>> getSessionById(@PathVariable Long id) {
        SessionCoachingDTO session = sessionService.getSessionById(id);
        return ResponseEntity.ok(ApiResponse.success(session, "Session trouvée"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Sessions d'un bénéficiaire")
    public ResponseEntity<ApiResponse<PageResponse<SessionCoachingDTO>>> getSessionsByBeneficiaire(
            @PathVariable Long beneficiaireId,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<SessionCoachingDTO> response = sessionService.getSessionsByBeneficiaire(beneficiaireId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Sessions du bénéficiaire"));
    }

    @GetMapping("/coach/{coachId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Sessions d'un coach")
    public ResponseEntity<ApiResponse<PageResponse<SessionCoachingDTO>>> getSessionsByCoach(
            @PathVariable Long coachId,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<SessionCoachingDTO> response = sessionService.getSessionsByCoach(coachId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Sessions du coach"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Modifier une session")
    public ResponseEntity<ApiResponse<SessionCoachingDTO>> updateSession(
            @PathVariable Long id,
            @Valid @RequestBody SessionCoachingDTO dto) {
        SessionCoachingDTO updated = sessionService.updateSession(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Session modifiée"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Supprimer une session")
    public ResponseEntity<ApiResponse<Void>> deleteSession(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Session supprimée"));
    }
}