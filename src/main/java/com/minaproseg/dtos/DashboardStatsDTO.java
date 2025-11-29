package com.minaproseg.dtos;


import lombok.*;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardStatsDTO {
    // Stats communes
    private Long totalBeneficiaires;
    private Long totalActivites;
    private Long totalCoachings;
    private Long totalObjectifs;

    // Stats par statut
    private Long beneficiairesActifs;
    private Long beneficiairesPause;
    private Long beneficiairesSortis;
    private Long beneficiairesDiplomes;

    // Stats activités
    private Long activitesAVenir;
    private Long activitesEnCours;
    private Long activitesTerminees;

    // Stats objectifs
    private Long objectifsEnCours;
    private Long objectifsAtteints;
    private Long objectifsNonAtteints;
    private Double progressMoyen;

    // Stats coaching
    private Long coachingsPlanifies;
    private Long coachingsCompletes;
    private Double scoreMoyen;

    // Stats financières
    private Double chiffreAffairesTotal;
    private Double depensesTotales;
    private Double margeMovenne;
    private Double variationCA;
    private Double variationMarge;
}
