// ===== PARTICIPATION ACTIVITE SERVICE (Bonus) =====
package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.ParticipationActivite;
import com.minaproseg.entities.Activite;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.repositories.ActiviteRepository;
import com.minaproseg.repositories.ParticipationActiviteRepository;
import com.minaproseg.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ParticipationActiviteService {
    private final ParticipationActiviteRepository participationRepository;
    private final ActiviteRepository activiteRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ParticipationActiviteDTO markPresence(ParticipationActiviteDTO dto) {
        Activite activite = activiteRepository.findById(dto.getActiviteId())
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));

        // Vérifier si une participation existe déjà
        ParticipationActivite participation = participationRepository
                .findByActiviteIdAndBeneficiaireId(dto.getActiviteId(), dto.getBeneficiaireId())
                .orElse(ParticipationActivite.builder()
                        .activite(activite)
                        .beneficiaire(beneficiaire)
                        .build());

        participation.setStatut(dto.getStatut());
        participation.setCommentaire(dto.getCommentaire());

        participation = participationRepository.save(participation);
        return convertToDTO(participation);
    }

    public List<ParticipationActiviteDTO> getParticipationsByActivite(Long activiteId) {
        return participationRepository.findByActiviteId(activiteId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ParticipationActiviteDTO convertToDTO(ParticipationActivite participation) {
        ParticipationActiviteDTO dto = modelMapper.map(participation, ParticipationActiviteDTO.class);
        dto.setActiviteTitre(participation.getActivite().getTitre());
        dto.setBeneficiaireNom(participation.getBeneficiaire().getPrenom() + " " + participation.getBeneficiaire().getNom());
        return dto;
    }
}