
package com.minaproseg.controllers;

import com.minaproseg.services.ParticipationActiviteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/participations")
@RequiredArgsConstructor
@Tag(name = "Participations", description = "Gestion des présences aux activités")
public class ParticipationActiviteController {
    private final ParticipationActiviteService participationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Marquer une présence/absence")
    public ResponseEntity<ApiResponse<ParticipationActiviteDTO>> markPresence(
            @Valid @RequestBody ParticipationActiviteDTO dto) {
        ParticipationActiviteDTO created = participationService.markPresence(dto);
        return ResponseEntity.ok(ApiResponse.success(created, "Présence enregistrée"));
    }

    @GetMapping("/activite/{activiteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Liste des participations à une activité")
    public ResponseEntity<ApiResponse<List<ParticipationActiviteDTO>>> getParticipationsByActivite(
            @PathVariable Long activiteId) {
        List<ParticipationActiviteDTO> participations = participationService.getParticipationsByActivite(activiteId);
        return ResponseEntity.ok(ApiResponse.success(participations, "Participations de l'activité"));
    }
}