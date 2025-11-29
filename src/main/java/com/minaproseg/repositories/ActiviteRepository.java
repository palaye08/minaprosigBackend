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


// ActiviteRepository.java
@Repository
public interface ActiviteRepository extends JpaRepository<Activite, Long> {
    // Activités par date
    List<Activite> findByDate(LocalDate date);
    List<Activite> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Page<Activite> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Activités par type
    Page<Activite> findByType(TypeActivite type, Pageable pageable);

    // Activités futures
    @Query("SELECT a FROM Activite a WHERE a.date >= CURRENT_DATE ORDER BY a.date ASC")
    List<Activite> findUpcomingActivites();

    Page<Activite> findByDateGreaterThanEqual(LocalDate date, Pageable pageable);

    // Activités d'un bénéficiaire
    @Query("SELECT a FROM Activite a JOIN a.participants p WHERE p.id = :beneficiaireId")
    Page<Activite> findActivitesByBeneficiaireId(@Param("beneficiaireId") Long beneficiaireId, Pageable pageable);

    // Recherche
    @Query("SELECT a FROM Activite a WHERE " +
            "(:type IS NULL OR a.type = :type) AND " +
            "(:modalite IS NULL OR a.modalite = :modalite) AND " +
            "(:startDate IS NULL OR a.date >= :startDate) AND " +
            "(:endDate IS NULL OR a.date <= :endDate) AND " +
            "(:search IS NULL OR LOWER(a.titre) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Activite> searchActivites(
            @Param("type") TypeActivite type,
            @Param("modalite") ModaliteActivite modalite,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("search") String search,
            Pageable pageable
    );

    // Statistiques
    Long countByType(TypeActivite type);
    Long countByDateBetween(LocalDate startDate, LocalDate endDate);
}
