package com.fleetflow.Service;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.enums.StatutLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivraisonService {
    LivraisonDTO getById(Long id);
    LivraisonDTO create(LivraisonDTO dto);
    LivraisonDTO update(Long id, LivraisonDTO dto);
    void delete(Long id);
    Page<LivraisonDTO> getAll(Pageable pageable);

    // Pour le rôle CHAUFFEUR : ses propres livraisons
    Page<LivraisonDTO> getByChauffeur(Long chauffeurId, Pageable pageable);

    // Pour le rôle CHAUFFEUR : mettre à jour uniquement le statut
    LivraisonDTO updateStatut(Long id, StatutLivraison statut);

    // Aliases utilisés par les tests
    LivraisonDTO createLivraison(LivraisonDTO dto);

    LivraisonDTO assignerChauffeurEtVehicule(Long livraisonId, Long chauffeurId, Long vehiculeId);

    LivraisonDTO modifierStatut(Long id, StatutLivraison statut);
}