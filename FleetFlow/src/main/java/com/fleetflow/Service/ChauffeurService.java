package com.fleetflow.Service;

import com.fleetflow.Dto.ChauffeurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChauffeurService {
    ChauffeurDTO getById(Long id);
    ChauffeurDTO create(ChauffeurDTO dto);
    ChauffeurDTO update(Long id, ChauffeurDTO dto);
    void delete(Long id);
    Page<ChauffeurDTO> getAll(Pageable pageable);

    List<ChauffeurDTO> getChauffeursdisponibles();

}