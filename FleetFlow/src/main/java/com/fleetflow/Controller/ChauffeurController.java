package com.fleetflow.Controller;

import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Service.ChauffeurService;
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
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

    private final ChauffeurService chauffeurService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Page<ChauffeurDTO>> getAll(
            @PageableDefault(size = 10, sort = "nom", direction = Sort.Direction.ASC)
            Pageable pageable) {
        return ResponseEntity.ok(chauffeurService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ChauffeurDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(chauffeurService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChauffeurDTO> create(@Valid @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.ok(chauffeurService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChauffeurDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ChauffeurDTO dto) {
        return ResponseEntity.ok(chauffeurService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chauffeurService.delete(id);
        return ResponseEntity.noContent().build();
    }
}