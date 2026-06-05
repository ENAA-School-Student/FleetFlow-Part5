package com.fleetflow.Mapper;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.Entity.Livraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivraisonMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "chauffeur.id", target = "chauffeurId")
    @Mapping(source = "vehicule.id", target = "vehiculeId")
    LivraisonDTO toDTO(Livraison livraison);

    @Mapping(source = "clientId", target = "client.id")
    @Mapping(target = "chauffeur", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    @Mapping(source = "chauffeurId", target = "chauffeur.id")
    @Mapping(source = "vehiculeId", target = "vehicule.id")
    Livraison toEntity(LivraisonDTO livraisonDTO);
}