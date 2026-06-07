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
import com.fleetflow.enums.StatutVehicule;
import com.fleetflow.Mapper.LivraisonMapper;
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
    private final LivraisonMapper livraisonMapper;

    @Override
    public LivraisonDTO getById(Long id) {
        return livraisonMapper.toDTO(livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id)));
    }

    @Override
    public LivraisonDTO create(LivraisonDTO dto) {
        Livraison entity = livraisonMapper.toEntity(dto);
        return livraisonMapper.toDTO(livraisonRepository.save(entity));
    }


    public LivraisonDTO createLivraison(LivraisonDTO dto) {
        if (dto.getStatut() == null) dto.setStatut(StatutLivraison.ENATTENTE);
        return create(dto);
    }

    @Override
    public LivraisonDTO update(Long id, LivraisonDTO dto) {
        Livraison l = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        l.setDateLivraison(dto.getDateLivraison());
        l.setAdresseDepart(dto.getAdresseDepart());
        l.setAdresseDestination(dto.getAdresseDestination());
        l.setStatut(dto.getStatut());
        return livraisonMapper.toDTO(livraisonRepository.save(l));
    }

    @Override
    public void delete(Long id) {
        livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        livraisonRepository.deleteById(id);
    }

    @Override
    public Page<LivraisonDTO> getAll(Pageable pageable) {
        return livraisonRepository.findAll(pageable).map(livraisonMapper::toDTO);
    }


    @Override
    public Page<LivraisonDTO> getByChauffeur(Long chauffeurId, Pageable pageable) {
        return livraisonRepository.findByChauffeurId(chauffeurId, pageable)
                .map(livraisonMapper::toDTO);
    }


    @Override
    public LivraisonDTO updateStatut(Long id, StatutLivraison statut) {
        Livraison l = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + id));
        l.setStatut(statut);
        return livraisonMapper.toDTO(livraisonRepository.save(l));
    }

    public LivraisonDTO modifierStatut(Long id, StatutLivraison statut) {
        return updateStatut(id, statut);
    }

    public LivraisonDTO assignerChauffeurEtVehicule(Long livraisonId, Long chauffeurId, Long vehiculeId) {
        Livraison l = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée : " + livraisonId));
        Chauffeur c = chauffeurRepository.findById(chauffeurId)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé : " + chauffeurId));
        Vehicule v = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé : " + vehiculeId));

        l.setChauffeur(c);
        l.setVehicule(v);
        l.setStatut(StatutLivraison.ENCOURS);

        c.setDisponible(false);
        v.setStatut(StatutVehicule.EN_LIVRAISON);

        chauffeurRepository.save(c);
        vehiculeRepository.save(v);
        livraisonRepository.save(l);

        return livraisonMapper.toDTO(l);
    }


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
