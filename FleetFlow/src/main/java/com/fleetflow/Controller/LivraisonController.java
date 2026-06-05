package com.fleetflow.Controller;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.Service.LivraisonService;
import com.fleetflow.enums.StatutLivraison;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Livraison.
 *
 * Règles selon rôle :
 *  ADMIN   → tout
 *  MANAGER → GET + POST + PUT + DELETE
 *  CHAUFFEUR → GET /mes-livraisons + PATCH /{id}/statut uniquement
 */
@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

    private final LivraisonService livraisonService;

    // ADMIN + MANAGER : voir toutes les livraisons
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<LivraisonDTO>> getAll(
            @PageableDefault(size = 10, sort = "dateLivraison", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(livraisonService.getAll(pageable));
    }

    // ADMIN + MANAGER : une livraison par id
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livraisonService.getById(id));
    }

    /**
     * CHAUFFEUR : consulter SES livraisons.
     * L'URL contient son chauffeurId.
     * Ex : GET /api/livraisons/chauffeur/3?page=0&size=5
     */
    @GetMapping("/chauffeur/{chauffeurId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CHAUFFEUR')")
    public ResponseEntity<Page<LivraisonDTO>> getByChauffeur(
            @PathVariable Long chauffeurId,
            @PageableDefault(size = 10, sort = "dateLivraison", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(livraisonService.getByChauffeur(chauffeurId, pageable));
    }

    // ADMIN + MANAGER : créer une livraison
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> create(@Valid @RequestBody LivraisonDTO dto) {
        return ResponseEntity.ok(livraisonService.create(dto));
    }

    // ADMIN + MANAGER : modifier une livraison complète
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LivraisonDTO dto) {
        return ResponseEntity.ok(livraisonService.update(id, dto));
    }

    /**
     * CHAUFFEUR : modifier SEULEMENT le statut de la livraison.
     * PATCH au lieu de PUT = modification partielle.
     * Ex : PATCH /api/livraisons/5/statut?statut=LIVREE
     */
    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CHAUFFEUR')")
    public ResponseEntity<LivraisonDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.updateStatut(id, statut));
    }

    // ADMIN + MANAGER : supprimer
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livraisonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}