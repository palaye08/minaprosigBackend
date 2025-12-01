// ===== DOCUMENT SERVICE =====
package com.minaproseg.services;

import com.minaproseg.dtos.*;
import com.minaproseg.entities.Document;
import com.minaproseg.entities.Utilisateur;
import com.minaproseg.enums.CategorieDocument;
import com.minaproseg.enums.StatutDocument;
import com.minaproseg.repositories.DocumentRepository;
import com.minaproseg.repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public DocumentDTO createDocument(DocumentDTO dto) {
        Utilisateur beneficiaire = utilisateurRepository.findById(dto.getBeneficiaireId())
                .orElseThrow(() -> new RuntimeException("Bénéficiaire non trouvé"));

        Document document = modelMapper.map(dto, Document.class);
        document.setBeneficiaire(beneficiaire);
        document = documentRepository.save(document);

        return convertToDTO(document);
    }

    public DocumentDTO getDocumentById(Long id) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));
        return convertToDTO(document);
    }

    public PageResponse<DocumentDTO> getDocumentsByBeneficiaire(Long beneficiaireId, Pageable pageable) {
        Page<Document> page = documentRepository.findByBeneficiaireId(beneficiaireId, pageable);
        return convertToPageResponse(page);
    }

    public PageResponse<DocumentDTO> searchDocuments(Long beneficiaireId, CategorieDocument categorie,
                                                     StatutDocument statut, String search, Pageable pageable) {
        Page<Document> page = documentRepository.searchDocuments(beneficiaireId, categorie, statut, search, pageable);
        return convertToPageResponse(page);
    }

    @Transactional
    public DocumentDTO updateDocument(Long id, DocumentDTO dto) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document non trouvé"));

        if (dto.getNom() != null) document.setNom(dto.getNom());
        if (dto.getCategorie() != null) document.setCategorie(dto.getCategorie());
        if (dto.getType() != null) document.setType(dto.getType());
        if (dto.getTaille() != null) document.setTaille(dto.getTaille());
        if (dto.getCheminFichier() != null) document.setCheminFichier(dto.getCheminFichier());
        if (dto.getStatut() != null) document.setStatut(dto.getStatut());
        if (dto.getTags() != null) document.setTags(dto.getTags());

        document = documentRepository.save(document);
        return convertToDTO(document);
    }

    @Transactional
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    private DocumentDTO convertToDTO(Document document) {
        DocumentDTO dto = modelMapper.map(document, DocumentDTO.class);
        dto.setBeneficiaireNom(document.getBeneficiaire().getPrenom() + " " + document.getBeneficiaire().getNom());
        return dto;
    }

    private PageResponse<DocumentDTO> convertToPageResponse(Page<Document> page) {
        return PageResponse.<DocumentDTO>builder()
                .content(page.getContent().stream().map(this::convertToDTO).collect(Collectors.toList()))
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
