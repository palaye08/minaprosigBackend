package com.minaproseg.controllers;


import com.minaproseg.dtos.ApiResponse;
import com.minaproseg.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;



@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Statistiques et tableaux de bord")
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Statistiques globales")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats(
            @RequestParam(required = false) ProfileRole profile) {
        DashboardStatsDTO stats = dashboardService.getDashboardStats(profile);
        return ResponseEntity.ok(ApiResponse.success(stats, "Statistiques du dashboard"));
    }

    @GetMapping("/stats/beneficiaire/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Statistiques d'un bénéficiaire")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStatsByBeneficiaire(@PathVariable Long id) {
        DashboardStatsDTO stats = dashboardService.getDashboardStatsByBeneficiaire(id);
        return ResponseEntity.ok(ApiResponse.success(stats, "Statistiques du bénéficiaire"));
    }
}