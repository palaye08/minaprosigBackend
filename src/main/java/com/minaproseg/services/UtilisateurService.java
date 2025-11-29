package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UtilisateurService implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    public UtilisateurDTO getUtilisateurById(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return convertToDTO(utilisateur);
    }

    public PageResponse<UtilisateurSimpleDTO> getAllUtilisateurs(Pageable pageable) {
        Page<Utilisateur> page = utilisateurRepository.findAll(pageable);
        return convertToPageResponse(page);
    }

    public List<UtilisateurSimpleDTO> getUsersByProfile(ProfileRole profile) {
        return utilisateurRepository.findByProfile(profile).stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());
    }

    public PageResponse<UtilisateurSimpleDTO> searchUtilisateurs(
            ProfileRole profile, StatutBeneficiaire statut, String search, Pageable pageable) {
        Page<Utilisateur> page = utilisateurRepository.searchUtilisateurs(profile, statut, search, pageable);
        return convertToPageResponse(page);
    }

    @Transactional
    public UtilisateurDTO updateUtilisateur(Long id, UpdateUtilisateurRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (request.getPrenom() != null) utilisateur.setPrenom(request.getPrenom());
        if (request.getNom() != null) utilisateur.setNom(request.getNom());
        if (request.getTelephone() != null) utilisateur.setTelephone(request.getTelephone());
        if (request.getTelephoneSecondaire() != null) utilisateur.setTelephoneSecondaire(request.getTelephoneSecondaire());
        if (request.getAdresse() != null) utilisateur.setAdresse(request.getAdresse());
        if (request.getVille() != null) utilisateur.setVille(request.getVille());
        if (request.getNiveauEducation() != null) utilisateur.setNiveauEducation(request.getNiveauEducation());
        if (request.getSituationProfessionnelle() != null) utilisateur.setSituationProfessionnelle(request.getSituationProfessionnelle());
        if (request.getStatut() != null) utilisateur.setStatut(request.getStatut());
        if (request.getMotifSortie() != null) utilisateur.setMotifSortie(request.getMotifSortie());
        if (request.getNomEntreprise() != null) utilisateur.setNomEntreprise(request.getNomEntreprise());
        if (request.getSecteurActivite() != null) utilisateur.setSecteurActivite(request.getSecteurActivite());
        if (request.getStatutJuridique() != null) utilisateur.setStatutJuridique(request.getStatutJuridique());
        if (request.getAnneeCreation() != null) utilisateur.setAnneeCreation(request.getAnneeCreation());
        if (request.getNiveauAvancement() != null) utilisateur.setNiveauAvancement(request.getNiveauAvancement());
        if (request.getNombreEmployesPermanents() != null) utilisateur.setNombreEmployesPermanents(request.getNombreEmployesPermanents());
        if (request.getNombreEmployesTemporaires() != null) utilisateur.setNombreEmployesTemporaires(request.getNombreEmployesTemporaires());

        if (request.getCoachId() != null) {
            Utilisateur coach = utilisateurRepository.findById(request.getCoachId())
                    .orElseThrow(() -> new RuntimeException("Coach non trouvé"));
            utilisateur.setCoach(coach);
        }

        utilisateur = utilisateurRepository.save(utilisateur);
        return convertToDTO(utilisateur);
    }

    @Transactional
    public void deleteUtilisateur(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getOldPassword(), utilisateur.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        utilisateur.setPassword(passwordEncoder.encode(request.getNewPassword()));
        utilisateurRepository.save(utilisateur);
    }

    // Méthodes de conversion
    private UtilisateurDTO convertToDTO(Utilisateur utilisateur) {
        UtilisateurDTO dto = modelMapper.map(utilisateur, UtilisateurDTO.class);
        if (utilisateur.getCoach() != null) {
            dto.setCoachId(utilisateur.getCoach().getId());
            dto.setCoachNom(utilisateur.getCoach().getPrenom() + " " + utilisateur.getCoach().getNom());
        }
        return dto;
    }

    private UtilisateurSimpleDTO convertToSimpleDTO(Utilisateur utilisateur) {
        UtilisateurSimpleDTO dto = modelMapper.map(utilisateur, UtilisateurSimpleDTO.class);
        if (utilisateur.getCoach() != null) {
            dto.setCoachNom(utilisateur.getCoach().getPrenom() + " " + utilisateur.getCoach().getNom());
        }
        return dto;
    }

    private PageResponse<UtilisateurSimpleDTO> convertToPageResponse(Page<Utilisateur> page) {
        List<UtilisateurSimpleDTO> content = page.getContent().stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());

        return PageResponse.<UtilisateurSimpleDTO>builder()
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
