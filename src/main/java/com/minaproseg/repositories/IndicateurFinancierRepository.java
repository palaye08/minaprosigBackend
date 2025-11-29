package com.minaproseg.repositories;

import com.minaproseg.entities.IndicateurFinancier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndicateurFinancierRepository extends JpaRepository<IndicateurFinancier, Long> {
    List<IndicateurFinancier> findByBeneficiaireId(Long beneficiaireId);
    List<IndicateurFinancier> findByBeneficiaireIdOrderByAnneeDescMoisDesc(Long beneficiaireId);

    Optional<IndicateurFinancier> findByBeneficiaireIdAndAnneeAndMois(Long beneficiaireId, Integer annee, Integer mois);

    @Query("SELECT i FROM IndicateurFinancier i WHERE i.beneficiaire.id = :beneficiaireId " +
            "AND i.annee = :annee ORDER BY i.mois DESC")
    List<IndicateurFinancier> findByBeneficiaireIdAndAnnee(
            @Param("beneficiaireId") Long beneficiaireId,
            @Param("annee") Integer annee
    );

    @Query("SELECT SUM(i.chiffreAffaires) FROM IndicateurFinancier i WHERE i.beneficiaire.id = :beneficiaireId")
    Double getTotalCAByBeneficiaire(@Param("beneficiaireId") Long beneficiaireId);
}