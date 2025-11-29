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
public interface CommentaireObjectifRepository extends JpaRepository<CommentaireObjectif, Long> {
    List<CommentaireObjectif> findByObjectifId(Long objectifId);
    List<CommentaireObjectif> findByObjectifIdOrderByCreatedAtDesc(Long objectifId);
}
