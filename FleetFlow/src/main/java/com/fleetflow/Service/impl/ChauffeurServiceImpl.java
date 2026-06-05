package com.fleetflow.Service.impl;

import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Entity.Chauffeur;
import com.fleetflow.Repository.ChauffeurRepository;
import com.fleetflow.Service.ChauffeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChauffeurServiceImpl implements ChauffeurService {

    private final ChauffeurRepository chauffeurRepository;

    @Override
    public ChauffeurDTO getById(Long id) {
        return toDto(chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé : " + id)));
    }

    @Override
    public ChauffeurDTO create(ChauffeurDTO dto) {
        return toDto(chauffeurRepository.save(toEntity(dto)));
    }

    @Override
    public ChauffeurDTO update(Long id, ChauffeurDTO dto) {
        Chauffeur c = chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé : " + id));
        c.setNom(dto.getNom());
        c.setTelephone(dto.getTelephone());
        c.setPermisType(dto.getPermisType());
        c.setDisponible(dto.getDisponible());
        return toDto(chauffeurRepository.save(c));
    }

    @Override
    public void delete(Long id) {
        chauffeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chauffeur non trouvé : " + id));
        chauffeurRepository.deleteById(id);
    }

    @Override
    public Page<ChauffeurDTO> getAll(Pageable pageable) {
        return chauffeurRepository.findAll(pageable).map(this::toDto);
    }

    private ChauffeurDTO toDto(Chauffeur c) {
        ChauffeurDTO dto = new ChauffeurDTO();
        dto.setId(c.getId());
        dto.setNom(c.getNom());
        dto.setTelephone(c.getTelephone());
        dto.setPermisType(c.getPermisType());
        dto.setDisponible(c.getDisponible());
        return dto;
    }

    private Chauffeur toEntity(ChauffeurDTO dto) {
        return Chauffeur.builder()
                .nom(dto.getNom())
                .telephone(dto.getTelephone())
                .permisType(dto.getPermisType())
                .disponible(dto.getDisponible())
                .build();
    }
}
