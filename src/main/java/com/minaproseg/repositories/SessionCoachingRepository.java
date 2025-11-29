package com.minaproseg.repositories;

import com.minaproseg.entities.SessionCoaching;
import com.minaproseg.enums.StatutCoaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SessionCoachingRepository extends JpaRepository<SessionCoaching, Long> {
    List<SessionCoaching> findByBeneficiaireId(Long beneficiaireId);
    Page<SessionCoaching> findByBeneficiaireId(Long beneficiaireId, Pageable pageable);

    List<SessionCoaching> findByCoachId(Long coachId);
    Page<SessionCoaching> findByCoachId(Long coachId, Pageable pageable);

    Page<SessionCoaching> findByStatut(StatutCoaching statut, Pageable pageable);

    List<SessionCoaching> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT s FROM SessionCoaching s WHERE s.date >= CURRENT_DATE ORDER BY s.date ASC")
    List<SessionCoaching> findUpcomingSessions();

    @Query("SELECT s FROM SessionCoaching s WHERE " +
            "(:beneficiaireId IS NULL OR s.beneficiaire.id = :beneficiaireId) AND " +
            "(:coachId IS NULL OR s.coach.id = :coachId) AND " +
            "(:statut IS NULL OR s.statut = :statut) AND " +
            "(:startDate IS NULL OR s.date >= :startDate) AND " +
            "(:endDate IS NULL OR s.date <= :endDate)")
    Page<SessionCoaching> searchSessions(
            @Param("beneficiaireId") Long beneficiaireId,
            @Param("coachId") Long coachId,
            @Param("statut") StatutCoaching statut,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable
    );

    Long countByBeneficiaireId(Long beneficiaireId);  // ✅ AJOUTEZ CETTE LIGNE
    Long countByBeneficiaireIdAndStatut(Long beneficiaireId, StatutCoaching statut);
    Long countByCoachId(Long coachId);
}