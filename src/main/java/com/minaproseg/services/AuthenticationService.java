package com.minaproseg.services;


import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// AuthenticationService.java
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Transactional
    public AuthenticationResponse register(CreateUtilisateurRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        String idBeneficiaire = generateIdBeneficiaire();

        var utilisateur = Utilisateur.builder()
                .idBeneficiaire(idBeneficiaire)
                .prenom(request.getPrenom())
                .nom(request.getNom())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .profile(request.getProfile())
                .genre(request.getGenre())
                .dateNaissance(request.getDateNaissance())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .pays(request.getPays())
                .region(request.getRegion())
                .ville(request.getVille())
                .zone(request.getZone())
                .niveauEducation(request.getNiveauEducation())
                .situationProfessionnelle(request.getSituationProfessionnelle())
                .programme(request.getProgramme())
                .bailleur(request.getBailleur())
                .dateEnrolement(java.time.LocalDate.now())
                .statut(request.getProfile() == ProfileRole.BENEFICIAIRE ? StatutBeneficiaire.ACTIF : null)
                .enabled(true)
                .build();

        if (request.getCoachId() != null) {
            Utilisateur coach = utilisateurRepository.findById(request.getCoachId())
                    .orElseThrow(() -> new RuntimeException("Coach non trouvé"));
            utilisateur.setCoach(coach);
        }

        utilisateur = utilisateurRepository.save(utilisateur);

        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .utilisateur(modelMapper.map(utilisateur, UtilisateurDTO.class))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var utilisateur = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        var jwtToken = jwtService.generateToken(utilisateur);
        var refreshToken = jwtService.generateRefreshToken(utilisateur);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .utilisateur(modelMapper.map(utilisateur, UtilisateurDTO.class))
                .build();
    }

    private String generateIdBeneficiaire() {
        long count = utilisateurRepository.count() + 1;
        return String.format("BEN-%d-%03d", java.time.Year.now().getValue(), count);
    }
}
