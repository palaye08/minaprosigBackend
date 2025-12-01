// ===== SESSION COACHING SERVICE =====
package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.SessionCoaching;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.repositories.SessionCoachingRepository;
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
public class SessionCoachingService {
    private final SessionCoachingRepository sessionRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public SessionCoachingDTO createSession(SessionCoachingDTO dto) {
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));
        Utilisateur coach = utilisateurRepository.findById(dto.getCoachId())
                .orElseThrow(() -> new RuntimeException("Coach non trouvé"));

        SessionCoaching session = modelMapper.map(dto, SessionCoaching.class);
        session.setBeneficiaire(beneficiaire);
        session.setCoach(coach);
        session = sessionRepository.save(session);

        return convertToDTO(session);
    }

    public SessionCoachingDTO getSessionById(Long id) {
        SessionCoaching session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));
        return convertToDTO(session);
    }

    public PageResponse<SessionCoachingDTO> getSessionsByBeneficiaire(Long beneficiaireId, Pageable pageable) {
        Page<SessionCoaching> page = sessionRepository.findByBeneficiaireId(beneficiaireId, pageable);
        return convertToPageResponse(page);
    }

    public PageResponse<SessionCoachingDTO> getSessionsByCoach(Long coachId, Pageable pageable) {
        Page<SessionCoaching> page = sessionRepository.findByCoachId(coachId, pageable);
        return convertToPageResponse(page);
    }

    @Transactional
    public SessionCoachingDTO updateSession(Long id, SessionCoachingDTO dto) {
        SessionCoaching session = sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));

        if (dto.getDate() != null) session.setDate(dto.getDate());
        if (dto.getHeureDebut() != null) session.setHeureDebut(dto.getHeureDebut());
        if (dto.getHeureFin() != null) session.setHeureFin(dto.getHeureFin());
        if (dto.getModalite() != null) session.setModalite(dto.getModalite());
        if (dto.getStatut() != null) session.setStatut(dto.getStatut());
        if (dto.getObjectif() != null) session.setObjectif(dto.getObjectif());
        if (dto.getSituationInitiale() != null) session.setSituationInitiale(dto.getSituationInitiale());
        if (dto.getThemesAbordes() != null) session.setThemesAbordes(dto.getThemesAbordes());
        if (dto.getActionsBeneficiaire() != null) session.setActionsBeneficiaire(dto.getActionsBeneficiaire());
        if (dto.getActionsCoach() != null) session.setActionsCoach(dto.getActionsCoach());
        if (dto.getDifficultes() != null) session.setDifficultes(dto.getDifficultes());
        if (dto.getProchaineSession() != null) session.setProchaineSession(dto.getProchaineSession());

        session = sessionRepository.save(session);
        return convertToDTO(session);
    }

    @Transactional
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }

    private SessionCoachingDTO convertToDTO(SessionCoaching session) {
        SessionCoachingDTO dto = modelMapper.map(session, SessionCoachingDTO.class);
        dto.setBeneficiaireNom(session.getBeneficiaire().getPrenom() + " " + session.getBeneficiaire().getNom());
        dto.setCoachNom(session.getCoach().getPrenom() + " " + session.getCoach().getNom());
        return dto;
    }

    private PageResponse<SessionCoachingDTO> convertToPageResponse(Page<SessionCoaching> page) {
        return PageResponse.<SessionCoachingDTO>builder()
                .content(page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}