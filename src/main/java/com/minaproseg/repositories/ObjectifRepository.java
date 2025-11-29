package com.minaproseg.repositories;

import com.minaproseg.entities.Objectif;
import com.minaproseg.enums.CategorieObjectif;
import com.minaproseg.enums.StatutObjectif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectifRepository extends JpaRepository<Objectif, Long> {
    List<Objectif> findByBeneficiaireId(Long beneficiaireId);
    Page<Objectif> findByBeneficiaireId(Long beneficiaireId, Pageable pageable);

    Page<Objectif> findByStatut(StatutObjectif statut, Pageable pageable);
    Page<Objectif> findByCategorie(CategorieObjectif categorie, Pageable pageable);

    List<Objectif> findByBeneficiaireIdAndStatut(Long beneficiaireId, StatutObjectif statut);

    @Query("SELECT o FROM Objectif o WHERE o.beneficiaire.coach.id = :coachId")
    Page<Objectif> findObjectifsByCoachId(@Param("coachId") Long coachId, Pageable pageable);

    @Query("SELECT o FROM Objectif o WHERE " +
            "(:beneficiaireId IS NULL OR o.beneficiaire.id = :beneficiaireId) AND " +
            "(:statut IS NULL OR o.statut = :statut) AND " +
            "(:categorie IS NULL OR o.categorie = :categorie)")
    Page<Objectif> searchObjectifs(
            @Param("beneficiaireId") Long beneficiaireId,
            @Param("statut") StatutObjectif statut,
            @Param("categorie") CategorieObjectif categorie,
            Pageable pageable
    );

    @Query("SELECT AVG(o.progress) FROM Objectif o WHERE o.beneficiaire.id = :beneficiaireId")
    Double getAverageProgressByBeneficiaire(@Param("beneficiaireId") Long beneficiaireId);

    Long countByBeneficiaireId(Long beneficiaireId);  // ✅ AJOUTEZ CETTE LIGNE
    Long countByBeneficiaireIdAndStatut(Long beneficiaireId, StatutObjectif statut);
}