package com.minaproseg.repositories;

import com.minaproseg.entities.ParticipationActivite;
import com.minaproseg.enums.StatutActivite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationActiviteRepository extends JpaRepository<ParticipationActivite, Long> {
    List<ParticipationActivite> findByActiviteId(Long activiteId);
    List<ParticipationActivite> findByBeneficiaireId(Long beneficiaireId);
    Page<ParticipationActivite> findByBeneficiaireId(Long beneficiaireId, Pageable pageable);

    Optional<ParticipationActivite> findByActiviteIdAndBeneficiaireId(Long activiteId, Long beneficiaireId);

    Long countByBeneficiaireId(Long beneficiaireId);  // ✅ AJOUTEZ CETTE LIGNE
    Long countByBeneficiaireIdAndStatut(Long beneficiaireId, StatutActivite statut);
    Long countByActiviteIdAndStatut(Long activiteId, StatutActivite statut);
}