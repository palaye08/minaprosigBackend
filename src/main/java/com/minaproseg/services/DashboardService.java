package com.minaproseg.services;

import com.minaproseg.dtos.DashboardStatsDTO;
import com.minaproseg.enums.ProfileRole;
import com.minaproseg.enums.StatutBeneficiaire;
import com.minaproseg.enums.StatutObjectif;
import com.minaproseg.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final UtilisateurRepository utilisateurRepository;
    private final ActiviteRepository activiteRepository;
    private final SessionCoachingRepository sessionCoachingRepository;
    private final ObjectifRepository objectifRepository;
    private final ScoreRepository scoreRepository;
    private final IndicateurFinancierRepository indicateurFinancierRepository;
    private final ParticipationActiviteRepository participationActiviteRepository;

    public DashboardStatsDTO getDashboardStats(ProfileRole profile) {
        return DashboardStatsDTO.builder()
                .totalBeneficiaires(utilisateurRepository.countByProfile(ProfileRole.BENEFICIAIRE))
                .beneficiairesActifs(utilisateurRepository.countByProfileAndStatut(ProfileRole.BENEFICIAIRE, StatutBeneficiaire.ACTIF))
                .beneficiairesPause(utilisateurRepository.countByProfileAndStatut(ProfileRole.BENEFICIAIRE, StatutBeneficiaire.EN_PAUSE))
                .beneficiairesSortis(utilisateurRepository.countByProfileAndStatut(ProfileRole.BENEFICIAIRE, StatutBeneficiaire.SORTI))
                .beneficiairesDiplomes(utilisateurRepository.countByProfileAndStatut(ProfileRole.BENEFICIAIRE, StatutBeneficiaire.DIPLOME))
                .totalActivites(activiteRepository.count())
                .totalCoachings(sessionCoachingRepository.count())
                .totalObjectifs(objectifRepository.count())
                .scoreMoyen(scoreRepository.getAverageScoreByProfile(ProfileRole.BENEFICIAIRE))
                .build();
    }

    public DashboardStatsDTO getDashboardStatsByBeneficiaire(Long beneficiaireId) {
        Long totalActivites = participationActiviteRepository.countByBeneficiaireId(beneficiaireId);
        Long totalCoachings = sessionCoachingRepository.countByBeneficiaireId(beneficiaireId);
        Long totalObjectifs = objectifRepository.countByBeneficiaireId(beneficiaireId);

        return DashboardStatsDTO.builder()
                .totalActivites(totalActivites)
                .totalCoachings(totalCoachings)
                .totalObjectifs(totalObjectifs)
                .objectifsEnCours(objectifRepository.countByBeneficiaireIdAndStatut(beneficiaireId, StatutObjectif.EN_COURS))
                .objectifsAtteints(objectifRepository.countByBeneficiaireIdAndStatut(beneficiaireId, StatutObjectif.ATTEINT))
                .progressMoyen(objectifRepository.getAverageProgressByBeneficiaire(beneficiaireId))
                .scoreMoyen(scoreRepository.getAverageScoreByBeneficiaire(beneficiaireId))
                .build();
    }
}