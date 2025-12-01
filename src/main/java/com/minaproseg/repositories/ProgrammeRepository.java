package com.minaproseg.repositories;

import com.minaproseg.entities.Programme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {

    // Recherche par statut
    Page<Programme> findByStatut(String statut, Pageable pageable);

    // Recherche par partenaire
    Page<Programme> findByPartenaire(String partenaire, Pageable pageable);

    // Programmes actifs
    @Query("SELECT p FROM Programme p WHERE p.statut = 'ACTIF' AND p.dateFin >= CURRENT_DATE")
    List<Programme> findActiveProgrammes();

    // Programmes d'un coach
    @Query("SELECT p FROM Programme p JOIN p.coachs c WHERE c.id = :coachId")
    Page<Programme> findProgrammesByCoachId(@Param("coachId") Long coachId, Pageable pageable);

    // Programmes d'un bénéficiaire
    @Query("SELECT p FROM Programme p JOIN p.beneficiaires b WHERE b.id = :beneficiaireId")
    Page<Programme> findProgrammesByBeneficiaireId(@Param("beneficiaireId") Long beneficiaireId, Pageable pageable);

    // Recherche avancée
    @Query("SELECT p FROM Programme p WHERE " +
            "(:nom IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
            "(:partenaire IS NULL OR LOWER(p.partenaire) LIKE LOWER(CONCAT('%', :partenaire, '%'))) AND " +
            "(:statut IS NULL OR p.statut = :statut) AND " +
            "(:startDate IS NULL OR p.dateDebut >= :startDate) AND " +
            "(:endDate IS NULL OR p.dateFin <= :endDate)")
    Page<Programme> searchProgrammes(
            @Param("nom") String nom,
            @Param("partenaire") String partenaire,
            @Param("statut") String statut,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    // Statistiques
    Long countByStatut(String statut);

    @Query("SELECT SUM(p.montantTotal) FROM Programme p WHERE p.statut = :statut")
    Double sumMontantByStatut(@Param("statut") String statut);
}