package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.Programme;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.enums.ProfileRole;
import com.minaproseg.repositories.ProgrammeRepository;
import com.minaproseg.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgrammeService {
    private final ProgrammeRepository programmeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ProgrammeDTO createProgramme(CreateProgrammeRequest request) {
        Programme programme = Programme.builder()
                .nom(request.getNom())
                .description(request.getDescription())
                .partenaire(request.getPartenaire())
                .montantTotal(request.getMontantTotal())
                .activites(request.getActivites())
                .dateDebut(request.getDateDebut())
                .dateFin(request.getDateFin())
                .statut("ACTIF")
                .coachs(new ArrayList<>())
                .beneficiaires(new ArrayList<>())
                .build();

        if (request.getCoachIds() != null && !request.getCoachIds().isEmpty()) {
            List<Utilisateur> coachs = utilisateurRepository.findAllById(request.getCoachIds());
            coachs.forEach(coach -> {
                if (coach.getProfile() != ProfileRole.COACH) {
                    throw new RuntimeException("L'utilisateur " + coach.getId() + " n'est pas un coach");
                }
            });
            programme.setCoachs(coachs);
        }

        programme = programmeRepository.save(programme);
        return convertToDTO(programme);
    }

    @Transactional(readOnly = true)  // ✅ AJOUT ICI
    public ProgrammeDTO getProgrammeById(Long id) {
        Programme programme = programmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));
        return convertToDTO(programme);
    }

    @Transactional(readOnly = true)  // ✅ AJOUT ICI
    public PageResponse<ProgrammeDTO> getAllProgrammes(Pageable pageable) {
        Page<Programme> page = programmeRepository.findAll(pageable);
        return convertToPageResponse(page);
    }

    @Transactional(readOnly = true)  // ✅ AJOUT ICI
    public PageResponse<ProgrammeDTO> searchProgrammes(
            String nom, String partenaire, String statut,
            LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<Programme> page = programmeRepository.searchProgrammes(
                nom, partenaire, statut, startDate, endDate, pageable);
        return convertToPageResponse(page);
    }

    @Transactional(readOnly = true)  // ✅ AJOUT ICI
    public List<ProgrammeDTO> getActiveProgrammes() {
        return programmeRepository.findActiveProgrammes().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProgrammeDTO updateProgramme(Long id, UpdateProgrammeRequest request) {
        Programme programme = programmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));

        if (request.getNom() != null) programme.setNom(request.getNom());
        if (request.getDescription() != null) programme.setDescription(request.getDescription());
        if (request.getPartenaire() != null) programme.setPartenaire(request.getPartenaire());
        if (request.getMontantTotal() != null) programme.setMontantTotal(request.getMontantTotal());
        if (request.getActivites() != null) programme.setActivites(request.getActivites());
        if (request.getDateDebut() != null) programme.setDateDebut(request.getDateDebut());
        if (request.getDateFin() != null) programme.setDateFin(request.getDateFin());
        if (request.getStatut() != null) programme.setStatut(request.getStatut());

        if (request.getCoachIds() != null) {
            List<Utilisateur> coachs = utilisateurRepository.findAllById(request.getCoachIds());
            coachs.forEach(coach -> {
                if (coach.getProfile() != ProfileRole.COACH) {
                    throw new RuntimeException("L'utilisateur " + coach.getId() + " n'est pas un coach");
                }
            });
            programme.setCoachs(coachs);
        }

        programme = programmeRepository.save(programme);
        return convertToDTO(programme);
    }

    @Transactional
    public ProgrammeDTO associateBeneficiaires(Long programmeId, AssociateBeneficiairesRequest request) {
        Programme programme = programmeRepository.findById(programmeId)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));

        List<Utilisateur> beneficiaires = utilisateurRepository.findAllById(request.getBeneficiaireIds());

        beneficiaires.forEach(beneficiaire -> {
            if (beneficiaire.getProfile() != ProfileRole.BENEFICIAIRE) {
                throw new RuntimeException("L'utilisateur " + beneficiaire.getId() + " n'est pas un bénéficiaire");
            }
        });

        if (programme.getBeneficiaires() == null) {
            programme.setBeneficiaires(new ArrayList<>());
        }
        programme.getBeneficiaires().addAll(beneficiaires);

        programme = programmeRepository.save(programme);
        return convertToDTO(programme);
    }

    @Transactional
    public ProgrammeDTO removeBeneficiaire(Long programmeId, Long beneficiaireId) {
        Programme programme = programmeRepository.findById(programmeId)
                .orElseThrow(() -> new RuntimeException("Programme non trouvé"));

        if (programme.getBeneficiaires() != null) {
            programme.getBeneficiaires().removeIf(b -> b.getId().equals(beneficiaireId));
        }

        programme = programmeRepository.save(programme);
        return convertToDTO(programme);
    }

    @Transactional
    public void deleteProgramme(Long id) {
        if (!programmeRepository.existsById(id)) {
            throw new RuntimeException("Programme non trouvé");
        }
        programmeRepository.deleteById(id);
    }

    private ProgrammeDTO convertToDTO(Programme programme) {
        ProgrammeDTO dto = ProgrammeDTO.builder()
                .id(programme.getId())
                .nom(programme.getNom())
                .description(programme.getDescription())
                .partenaire(programme.getPartenaire())
                .montantTotal(programme.getMontantTotal())
                .activites(programme.getActivites())
                .dateDebut(programme.getDateDebut())
                .dateFin(programme.getDateFin())
                .statut(programme.getStatut())
                .createdAt(programme.getCreatedAt())
                .updatedAt(programme.getUpdatedAt())
                .build();

        if (programme.getCoachs() != null && !programme.getCoachs().isEmpty()) {
            dto.setCoachIds(programme.getCoachs().stream()
                    .map(Utilisateur::getId)
                    .collect(Collectors.toList()));
            dto.setCoachs(programme.getCoachs().stream()
                    .map(c -> UtilisateurSimpleDTO.builder()
                            .id(c.getId())
                            .idBeneficiaire(c.getIdBeneficiaire())
                            .prenom(c.getPrenom())
                            .nom(c.getNom())
                            .email(c.getEmail())
                            .profile(c.getProfile())
                            .avatar(c.getAvatar())
                            .build())
                    .collect(Collectors.toList()));
        }

        if (programme.getBeneficiaires() != null && !programme.getBeneficiaires().isEmpty()) {
            dto.setBeneficiaireIds(programme.getBeneficiaires().stream()
                    .map(Utilisateur::getId)
                    .collect(Collectors.toList()));
            dto.setBeneficiaires(programme.getBeneficiaires().stream()
                    .map(b -> UtilisateurSimpleDTO.builder()
                            .id(b.getId())
                            .idBeneficiaire(b.getIdBeneficiaire())
                            .prenom(b.getPrenom())
                            .nom(b.getNom())
                            .email(b.getEmail())
                            .profile(b.getProfile())
                            .statut(b.getStatut())
                            .avatar(b.getAvatar())
                            .build())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private PageResponse<ProgrammeDTO> convertToPageResponse(Page<Programme> page) {
        List<ProgrammeDTO> content = page.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return PageResponse.<ProgrammeDTO>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .first(page.isFirst())
                .build();
    }
}