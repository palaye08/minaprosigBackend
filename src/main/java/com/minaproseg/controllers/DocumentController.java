
// ===== DOCUMENT CONTROLLER =====
package com.minaproseg.controllers;

import com.minaproseg.services.DocumentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.minaproseg.dtos.*;
import com.minaproseg.entities.*;
import com.minaproseg.enums.*;
import com.minaproseg.repositories.*;
import com.minaproseg.dtos.*;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@Tag(name = "Documents", description = "Gestion des documents")
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Créer un document")
    public ResponseEntity<ApiResponse<DocumentDTO>> createDocument(@Valid @RequestBody DocumentDTO dto) {
        DocumentDTO created = documentService.createDocument(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(created, "Document créé"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Détails d'un document")
    public ResponseEntity<ApiResponse<DocumentDTO>> getDocumentById(@PathVariable Long id) {
        DocumentDTO document = documentService.getDocumentById(id);
        return ResponseEntity.ok(ApiResponse.success(document, "Document trouvé"));
    }

    @GetMapping("/beneficiaire/{beneficiaireId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Documents d'un bénéficiaire")
    public ResponseEntity<ApiResponse<PageResponse<DocumentDTO>>> getDocumentsByBeneficiaire(
            @PathVariable Long beneficiaireId,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<DocumentDTO> response = documentService.getDocumentsByBeneficiaire(beneficiaireId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Documents du bénéficiaire"));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH', 'BENEFICIAIRE')")
    @Operation(summary = "Recherche de documents")
    public ResponseEntity<ApiResponse<PageResponse<DocumentDTO>>> searchDocuments(
            @RequestParam(required = false) Long beneficiaireId,
            @RequestParam(required = false) CategorieDocument categorie,
            @RequestParam(required = false) StatutDocument statut,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {
        PageResponse<DocumentDTO> response = documentService.searchDocuments(beneficiaireId, categorie, statut, search, pageable);
        return ResponseEntity.ok(ApiResponse.success(response, "Résultats de recherche"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Modifier un document")
    public ResponseEntity<ApiResponse<DocumentDTO>> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentDTO dto) {
        DocumentDTO updated = documentService.updateDocument(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Document modifié"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COACH')")
    @Operation(summary = "Supprimer un document")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Document supprimé"));
    }
}
