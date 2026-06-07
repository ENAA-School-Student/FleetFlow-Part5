package com.fleetflow.Service.impl;

import com.fleetflow.Dto.VehiculeDto;
import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Repository.VehiculeRepository;
import com.fleetflow.Service.VehiculeService;
import com.fleetflow.Mapper.VehiculeMapper;
import com.fleetflow.enums.StatutVehicule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDto getById(Long id) {
        return toDto(vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé : " + id)));
    }

    @Override
    public VehiculeDto create(VehiculeDto dto) {
        if (vehiculeRepository.existsByMatricule(dto.getMatricule())) {
            throw new RuntimeException("Matricule déjà existant : " + dto.getMatricule());
        }
        return toDto(vehiculeRepository.save(toEntity(dto)));
    }

    @Override
    public VehiculeDto update(Long id, VehiculeDto dto) {
        Vehicule v = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé : " + id));
        v.setMatricule(dto.getMatricule());
        v.setType(dto.getType());
        v.setCapacite(dto.getCapacite());
        v.setStatut(dto.getStatut());
        return toDto(vehiculeRepository.save(v));
    }

    @Override
    public void delete(Long id) {
        vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé : " + id));
        vehiculeRepository.deleteById(id);
    }

    @Override
    public Page<VehiculeDto> getAll(Pageable pageable) {
        return vehiculeRepository.findAll(pageable).map(this::toDto);
    }

    // Méthodes utilitaires ajoutées pour les tests (alias pratiques)
    public List<VehiculeDto> getVehiculeByStatut(StatutVehicule statut) {
        return vehiculeMapper.toDto(vehiculeRepository.findByStatut(statut));
    }

    public List<VehiculeDto> getVehiculeCapaciteGreaterThan(int capacite) {
        return vehiculeMapper.toDto(vehiculeRepository.findByCapaciteGreaterThan(capacite));
    }

    private VehiculeDto toDto(Vehicule v) {
        VehiculeDto dto = new VehiculeDto();
        dto.setId(v.getId());
        dto.setMatricule(v.getMatricule());
        dto.setType(v.getType());
        dto.setCapacite(v.getCapacite());
        dto.setStatut(v.getStatut());
        return dto;
    }

    private Vehicule toEntity(VehiculeDto dto) {
        return Vehicule.builder()
                .matricule(dto.getMatricule())
                .type(dto.getType())
                .capacite(dto.getCapacite())
                .statut(dto.getStatut())
                .build();
    }
}
