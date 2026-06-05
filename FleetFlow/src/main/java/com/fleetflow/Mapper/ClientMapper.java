package com.fleetflow.Mapper;


import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDto toDto(Client client);

    @Mapping(target = "livraisons", ignore = true)
    Client toEntity(ClientDto dto);

    List<ClientDto> toDto(List<Client> clients);

    @Mapping(target = "id", ignore = true)
    void updateClientDto(ClientDto dto, @MappingTarget Client client);
}