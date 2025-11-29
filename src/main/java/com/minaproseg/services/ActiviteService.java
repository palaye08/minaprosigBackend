package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActiviteService {
    private final ActiviteRepository activiteRepository;
    private final ParticipationActiviteRepository participationRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    public ActiviteDTO createActivite(ActiviteDTO dto) {
        Activite activite = modelMapper.map(dto, Activite.class);
        activite = activiteRepository.save(activite);
        return modelMapper.map(activite, ActiviteDTO.class);
    }

    public ActiviteDTO getActiviteById(Long id) {
        Activite activite = activiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activité non trouvée"));
        return modelMapper.map(activite, ActiviteDTO.class);
    }

    public PageResponse<ActiviteDTO> getActivitesByBeneficiaire(Long beneficiaireId, Pageable pageable) {
        Page<Activite> page = activiteRepository.findActivitesByBeneficiaireId(beneficiaireId, pageable);
        return convertToPageResponse(page);
    }

    public PageResponse<ActiviteDTO> searchActivites(TypeActivite type, ModaliteActivite modalite,
                                                     LocalDate startDate, LocalDate endDate,
                                                     String search, Pageable pageable) {
        Page<Activite> page = activiteRepository.searchActivites(type, modalite, startDate, endDate, search, pageable);
        return convertToPageResponse(page);
    }

    private PageResponse<ActiviteDTO> convertToPageResponse(Page<Activite> page) {
        List<ActiviteDTO> content = page.getContent().stream()
                .map(a -> modelMapper.map(a, ActiviteDTO.class))
                .collect(Collectors.toList());

        return PageResponse.<ActiviteDTO>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}