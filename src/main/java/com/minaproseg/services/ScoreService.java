package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.Score;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.repositories.ScoreRepository;
import com.minaproseg.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ScoreDTO createScore(ScoreDTO dto) {
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));
        Utilisateur evaluateur = utilisateurRepository.findById(dto.getEvaluateurId())
                .orElseThrow(() -> new RuntimeException("Évaluateur non trouvé"));

        Score score = modelMapper.map(dto, Score.class);
        score.setBeneficiaire(beneficiaire);
        score.setEvaluateur(evaluateur);
        score = scoreRepository.save(score);

        return convertToDTO(score);
    }

    public ScoreDTO getScoreById(Long id) {
        Score score = scoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Score non trouvé"));
        return convertToDTO(score);
    }

    public PageResponse<ScoreDTO> getScoresByBeneficiaire(Long beneficiaireId, Pageable pageable) {
        Page<Score> page = scoreRepository.findByBeneficiaireId(beneficiaireId, pageable);
        return convertToPageResponse(page);
    }

    public ScoreDTO getLatestScoreByBeneficiaire(Long beneficiaireId) {
        Score score = scoreRepository.findFirstByBeneficiaireIdOrderByCreatedAtDesc(beneficiaireId)
                .orElseThrow(() -> new RuntimeException("Aucun score trouvé pour ce bénéficiaire"));
        return convertToDTO(score);
    }

    @Transactional
    public void deleteScore(Long id) {
        scoreRepository.deleteById(id);
    }

    private ScoreDTO convertToDTO(Score score) {
        ScoreDTO dto = modelMapper.map(score, ScoreDTO.class);
        dto.setBeneficiaireNom(score.getBeneficiaire().getPrenom() + " " + score.getBeneficiaire().getNom());
        dto.setEvaluateurNom(score.getEvaluateur().getPrenom() + " " + score.getEvaluateur().getNom());
        return dto;
    }

    private PageResponse<ScoreDTO> convertToPageResponse(Page<Score> page) {
        return PageResponse.<ScoreDTO>builder()
                .content(page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}