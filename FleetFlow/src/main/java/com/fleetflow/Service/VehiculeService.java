package com.fleetflow.Service;


import com.fleetflow.Dto.VehiculeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehiculeService {
    VehiculeDto getById(Long id);
    VehiculeDto create(VehiculeDto dto);
    VehiculeDto update(Long id, VehiculeDto dto);
    void delete(Long id);
    Page<VehiculeDto> getAll(Pageable pageable);
}