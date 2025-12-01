// ===== INDICATEUR FINANCIER SERVICE =====
package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.IndicateurFinancier;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.repositories.IndicateurFinancierRepository;
import com.minaproseg.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IndicateurFinancierService {
    private final IndicateurFinancierRepository indicateurRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public IndicateurFinancierDTO createIndicateur(IndicateurFinancierDTO dto) {
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));

        // Vérifier si un indicateur existe déjà pour ce mois
        indicateurRepository.findByBeneficiaireIdAndAnneeAndMois(
                dto.getBeneficiaireId(), dto.getAnnee(), dto.getMois()
        ).ifPresent(existing -> {
            throw new RuntimeException("Un indicateur existe déjà pour cette période");
        });

        IndicateurFinancier indicateur = modelMapper.map(dto, IndicateurFinancier.class);
        indicateur.setBeneficiaire(beneficiaire);

        // Calculer la marge
        if (indicateur.getChiffreAffaires() != null && indicateur.getDepenses() != null) {
            double marge = ((indicateur.getChiffreAffaires() - indicateur.getDepenses())
                    / indicateur.getChiffreAffaires()) * 100;
            indicateur.setMarge(marge);
        }

        indicateur = indicateurRepository.save(indicateur);
        return convertToDTO(indicateur);
    }

    public IndicateurFinancierDTO getIndicateurById(Long id) {
        IndicateurFinancier indicateur = indicateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Indicateur non trouvé"));
        return convertToDTO(indicateur);
    }

    public List<IndicateurFinancierDTO> getIndicateursByBeneficiaire(Long beneficiaireId) {
        return indicateurRepository.findByBeneficiaireIdOrderByAnneeDescMoisDesc(beneficiaireId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<IndicateurFinancierDTO> getIndicateursByBeneficiaireAndYear(Long beneficiaireId, Integer annee) {
        return indicateurRepository.findByBeneficiaireIdAndAnnee(beneficiaireId, annee)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public IndicateurFinancierDTO updateIndicateur(Long id, IndicateurFinancierDTO dto) {
        IndicateurFinancier indicateur = indicateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Indicateur non trouvé"));

        if (dto.getChiffreAffaires() != null) indicateur.setChiffreAffaires(dto.getChiffreAffaires());
        if (dto.getDepenses() != null) indicateur.setDepenses(dto.getDepenses());

        // Recalculer la marge
        if (indicateur.getChiffreAffaires() != null && indicateur.getDepenses() != null) {
            double marge = ((indicateur.getChiffreAffaires() - indicateur.getDepenses())
                    / indicateur.getChiffreAffaires()) * 100;
            indicateur.setMarge(marge);
        }

        indicateur = indicateurRepository.save(indicateur);
        return convertToDTO(indicateur);
    }

    @Transactional
    public void deleteIndicateur(Long id) {
        indicateurRepository.deleteById(id);
    }

    public Double getTotalCAByBeneficiaire(Long beneficiaireId) {
        Double total = indicateurRepository.getTotalCAByBeneficiaire(beneficiaireId);
        return total != null ? total : 0.0;
    }

    private IndicateurFinancierDTO convertToDTO(IndicateurFinancier indicateur) {
        IndicateurFinancierDTO dto = modelMapper.map(indicateur, IndicateurFinancierDTO.class);
        dto.setBeneficiaireNom(indicateur.getBeneficiaire().getPrenom() + " " + indicateur.getBeneficiaire().getNom());
        return dto;
    }
}