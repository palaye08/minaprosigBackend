package com.minaproseg.repositories;

import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// UtilisateurRepository.java
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    Optional<Utilisateur> findByIdBeneficiaire(String idBeneficiaire);
    Boolean existsByEmail(String email);

    // Recherche par profil
    List<Utilisateur> findByProfile(ProfileRole profile);
    Page<Utilisateur> findByProfile(ProfileRole profile, Pageable pageable);

    // Recherche par statut (pour bénéficiaires)
    Page<Utilisateur> findByProfileAndStatut(ProfileRole profile, StatutBeneficiaire statut, Pageable pageable);

    // Bénéficiaires d'un coach
    List<Utilisateur> findByCoachId(Long coachId);
    Page<Utilisateur> findByCoachId(Long coachId, Pageable pageable);

    // Recherche multi-critères
    @Query("SELECT u FROM Utilisateur u WHERE " +
            "(:profile IS NULL OR u.profile = :profile) AND " +
            "(:statut IS NULL OR u.statut = :statut) AND " +
            "(:search IS NULL OR LOWER(u.nom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.prenom) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Utilisateur> searchUtilisateurs(
            @Param("profile") ProfileRole profile,
            @Param("statut") StatutBeneficiaire statut,
            @Param("search") String search,
            Pageable pageable
    );

    // Statistiques
    Long countByProfile(ProfileRole profile);
    Long countByProfileAndStatut(ProfileRole profile, StatutBeneficiaire statut);
    Long countByCoachId(Long coachId);
}