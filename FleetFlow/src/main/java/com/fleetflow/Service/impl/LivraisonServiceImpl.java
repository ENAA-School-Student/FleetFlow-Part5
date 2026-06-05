package com.fleetflow.Service.impl;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.Entity.Chauffeur;
import com.fleetflow.Entity.Client;
import com.fleetflow.Entity.Livraison;
import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Repository.ChauffeurRepository;
import com.fleetflow.Repository.ClientRepository;
import com.fleetflow.Repository.LivraisonRepository;
import com.fleetflow.Repository.VehiculeRepository;
import com.fleetflow.Service.LivraisonService;
import com.fleetflow.enums.StatutLivraison;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivraisonServiceImpl implements LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final ClientRepository clientRepository;
    private final ChauffeurRepository chauffeurRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public LivraisonDTO getById(Long id) {
        return toDto(livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id)));
    }

    @Override
    public LivraisonDTO create(LivraisonDTO dto) {
        return toDto(livraisonRepository.save(toEntity(dto)));
    }

    @Override
    public LivraisonDTO update(Long id, LivraisonDTO dto) {
        Livraison l = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        l.setDateLivraison(dto.getDateLivraison());
        l.setAdresseDepart(dto.getAdresseDepart());
        l.setAdresseDestination(dto.getAdresseDestination());
        l.setStatut(dto.getStatut());
        return toDto(livraisonRepository.save(l));
    }

    @Override
    public void delete(Long id) {
        livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        livraisonRepository.deleteById(id);
    }

    @Override
    public Page<LivraisonDTO> getAll(Pageable pageable) {
        return livraisonRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * Utilisé par le rôle CHAUFFEUR pour voir UNIQUEMENT ses propres livraisons.
     */
    @Override
    public Page<LivraisonDTO> getByChauffeur(Long chauffeurId, Pageable pageable) {
        return livraisonRepository.findByChauffeurId(chauffeurId, pageable)
                .map(this::toDto);
    }

    /**
     * Un CHAUFFEUR peut seulement changer le statut de ses livraisons,
     * pas modifier le reste (client, véhicule, adresses...).
     */
    @Override
    public LivraisonDTO updateStatut(Long id, StatutLivraison statut) {
        Livraison l = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        l.setStatut(statut);
        return toDto(livraisonRepository.save(l));
    }

    // ─── Mappers ─────────────────────────────────────────────────────────

    private LivraisonDTO toDto(Livraison l) {
        LivraisonDTO dto = new LivraisonDTO();
        dto.setId(l.getId());
        dto.setDateLivraison(l.getDateLivraison());
        dto.setAdresseDepart(l.getAdresseDepart());
        dto.setAdresseDestination(l.getAdresseDestination());
        dto.setStatut(l.getStatut());
        if (l.getClient() != null) dto.setClientId(l.getClient().getId());
        if (l.getChauffeur() != null) dto.setChauffeurId(l.getChauffeur().getId());
        if (l.getVehicule() != null) dto.setVehiculeId(l.getVehicule().getId());
        return dto;
    }

    private Livraison toEntity(LivraisonDTO dto) {
        Client client = dto.getClientId() != null ?
                clientRepository.findById(dto.getClientId()).orElse(null) : null;
        Chauffeur chauffeur = dto.getChauffeurId() != null ?
                chauffeurRepository.findById(dto.getChauffeurId()).orElse(null) : null;
        Vehicule vehicule = dto.getVehiculeId() != null ?
                vehiculeRepository.findById(dto.getVehiculeId()).orElse(null) : null;

        return Livraison.builder()
                .dateLivraison(dto.getDateLivraison())
                .adresseDepart(dto.getAdresseDepart())
                .adresseDestination(dto.getAdresseDestination())
                .statut(dto.getStatut())
                .client(client)
                .chauffeur(chauffeur)
                .vehicule(vehicule)
                .build();
    }
}
