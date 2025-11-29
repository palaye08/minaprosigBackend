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

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByBeneficiaireId(Long beneficiaireId);
    Page<Document> findByBeneficiaireId(Long beneficiaireId, Pageable pageable);

    Page<Document> findByCategorie(CategorieDocument categorie, Pageable pageable);
    Page<Document> findByStatut(StatutDocument statut, Pageable pageable);

    @Query("SELECT d FROM Document d WHERE " +
            "(:beneficiaireId IS NULL OR d.beneficiaire.id = :beneficiaireId) AND " +
            "(:categorie IS NULL OR d.categorie = :categorie) AND " +
            "(:statut IS NULL OR d.statut = :statut) AND " +
            "(:search IS NULL OR LOWER(d.nom) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Document> searchDocuments(
            @Param("beneficiaireId") Long beneficiaireId,
            @Param("categorie") CategorieDocument categorie,
            @Param("statut") StatutDocument statut,
            @Param("search") String search,
            Pageable pageable
    );

    Long countByBeneficiaireIdAndStatut(Long beneficiaireId, StatutDocument statut);
}
