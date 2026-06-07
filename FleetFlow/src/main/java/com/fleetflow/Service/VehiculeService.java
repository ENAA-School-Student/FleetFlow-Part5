package com.fleetflow.Service;


import com.fleetflow.Dto.VehiculeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.fleetflow.enums.StatutVehicule;
import java.util.List;

public interface VehiculeService {
    VehiculeDto getById(Long id);
    VehiculeDto create(VehiculeDto dto);
    VehiculeDto update(Long id, VehiculeDto dto);
    void delete(Long id);
    Page<VehiculeDto> getAll(Pageable pageable);

    // Méthodes utilitaires utilisées par les tests
    List<VehiculeDto> getVehiculeByStatut(StatutVehicule statut);

    List<VehiculeDto> getVehiculeCapaciteGreaterThan(int capacite);
}