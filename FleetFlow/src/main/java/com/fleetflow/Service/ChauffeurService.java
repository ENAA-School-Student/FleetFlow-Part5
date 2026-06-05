package com.fleetflow.Service;

import com.fleetflow.Dto.ChauffeurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChauffeurService {
    ChauffeurDTO getById(Long id);
    ChauffeurDTO create(ChauffeurDTO dto);
    ChauffeurDTO update(Long id, ChauffeurDTO dto);
    void delete(Long id);
    Page<ChauffeurDTO> getAll(Pageable pageable);

}