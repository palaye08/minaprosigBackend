package com.minaproseg.repositories;

import com.minaproseg.entities.*;


import com.minaproseg.enums.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByBeneficiaireId(Long beneficiaireId);
    List<Score> findByBeneficiaireIdOrderByCreatedAtDesc(Long beneficiaireId);
    Page<Score> findByBeneficiaireId(Long beneficiaireId, Pageable pageable);

    Optional<Score> findFirstByBeneficiaireIdOrderByCreatedAtDesc(Long beneficiaireId);

    @Query("SELECT AVG(s.scoreGlobal) FROM Score s WHERE s.beneficiaire.id = :beneficiaireId")
    Double getAverageScoreByBeneficiaire(@Param("beneficiaireId") Long beneficiaireId);

    @Query("SELECT AVG(s.scoreGlobal) FROM Score s WHERE s.beneficiaire.profile = :profile")
    Double getAverageScoreByProfile(@Param("profile") ProfileRole profile);
}
