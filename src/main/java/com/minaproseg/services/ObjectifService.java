// ===== OBJECTIF SERVICE =====
package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.CommentaireObjectif;
import com.minaproseg.entities.Objectif;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.repositories.CommentaireObjectifRepository;
import com.minaproseg.repositories.ObjectifRepository;
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
public class ObjectifService {
    private final ObjectifRepository objectifRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final CommentaireObjectifRepository commentaireRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ObjectifDTO createObjectif(ObjectifDTO dto) {
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));

        Objectif objectif = modelMapper.map(dto, Objectif.class);
        objectif.setBeneficiaire(beneficiaire);
        objectif = objectifRepository.save(objectif);

        return convertToDTO(objectif);
    }

    public ObjectifDTO getObjectifById(Long id) {
        Objectif objectif = objectifRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objectif non trouvé"));
        return convertToDTO(objectif);
    }

    public PageResponse<ObjectifDTO> getObjectifsByBeneficiaire(Long beneficiaireId, Pageable pageable) {
        Page<Objectif> page = objectifRepository.findByBeneficiaireId(beneficiaireId, pageable);
        return convertToPageResponse(page);
    }

    @Transactional
    public ObjectifDTO updateObjectif(Long id, ObjectifDTO dto) {
        Objectif objectif = objectifRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objectif non trouvé"));

        if (dto.getTitre() != null) objectif.setTitre(dto.getTitre());
        if (dto.getDescription() != null) objectif.setDescription(dto.getDescription());
        if (dto.getCategorie() != null) objectif.setCategorie(dto.getCategorie());
        if (dto.getIndicateur() != null) objectif.setIndicateur(dto.getIndicateur());
        if (dto.getDateDebut() != null) objectif.setDateDebut(dto.getDateDebut());
        if (dto.getDateCible() != null) objectif.setDateCible(dto.getDateCible());
        if (dto.getStatut() != null) objectif.setStatut(dto.getStatut());
        if (dto.getProgress() != null) objectif.setProgress(dto.getProgress());

        objectif = objectifRepository.save(objectif);
        return convertToDTO(objectif);
    }

    @Transactional
    public CommentaireObjectifDTO addCommentaire(Long objectifId, CommentaireObjectifDTO dto) {
        Objectif objectif = objectifRepository.findById(objectifId)
                .orElseThrow(() -> new RuntimeException("Objectif non trouvé"));

        Utilisateur auteur = utilisateurRepository.findById(dto.getAuteurId())
                .orElseThrow(() -> new RuntimeException("Auteur non trouvé"));

        CommentaireObjectif commentaire = CommentaireObjectif.builder()
                .objectif(objectif)
                .auteur(auteur)
                .texte(dto.getTexte())
                .build();

        commentaire = commentaireRepository.save(commentaire);

        CommentaireObjectifDTO result = modelMapper.map(commentaire, CommentaireObjectifDTO.class);
        result.setAuteurNom(auteur.getPrenom() + " " + auteur.getNom());
        return result;
    }

    @Transactional
    public void deleteObjectif(Long id) {
        objectifRepository.deleteById(id);
    }

    private ObjectifDTO convertToDTO(Objectif objectif) {
        ObjectifDTO dto = modelMapper.map(objectif, ObjectifDTO.class);
        dto.setBeneficiaireNom(objectif.getBeneficiaire().getPrenom() + " " + objectif.getBeneficiaire().getNom());

        if (objectif.getCommentaires() != null) {
            dto.setCommentaires(objectif.getCommentaires().stream()
                    .map(c -> {
                        CommentaireObjectifDTO cdto = modelMapper.map(c, CommentaireObjectifDTO.class);
                        cdto.setAuteurNom(c.getAuteur().getPrenom() + " " + c.getAuteur().getNom());
                        return cdto;
                    })
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private PageResponse<ObjectifDTO> convertToPageResponse(Page<Objectif> page) {
        return PageResponse.<ObjectifDTO>builder()
                .content(page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .build();
    }
}

