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


@RestController
@RequestMapping("/api/livraisons")
@RequiredArgsConstructor
public class LivraisonController {

    private final LivraisonService livraisonService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<LivraisonDTO>> getAll(
            @PageableDefault(size = 10, sort = "dateLivraison", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(livraisonService.getAll(pageable));
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(livraisonService.getById(id));
    }


    @GetMapping("/chauffeur/{chauffeurId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CHAUFFEUR')")
    public ResponseEntity<Page<LivraisonDTO>> getByChauffeur(
            @PathVariable Long chauffeurId,
            @PageableDefault(size = 10, sort = "dateLivraison", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(livraisonService.getByChauffeur(chauffeurId, pageable));
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> create(@Valid @RequestBody LivraisonDTO dto) {
        return ResponseEntity.ok(livraisonService.create(dto));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<LivraisonDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LivraisonDTO dto) {
        return ResponseEntity.ok(livraisonService.update(id, dto));
    }


    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'CHAUFFEUR')")
    public ResponseEntity<LivraisonDTO> updateStatut(
            @PathVariable Long id,
            @RequestParam StatutLivraison statut) {
        return ResponseEntity.ok(livraisonService.updateStatut(id, statut));
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livraisonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}