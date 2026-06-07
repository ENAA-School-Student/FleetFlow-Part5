package com.fleetflow.Mapper;

import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Entity.Chauffeur;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChauffeurMapper {
    ChauffeurDTO toDTO(Chauffeur chauffeur);
    Chauffeur toEntity(ChauffeurDTO chauffeurDTO);

    List<ChauffeurDTO> toDTOs(List<Chauffeur> chauffeurs);

}