package com.fleetflow.Service.impl;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import com.fleetflow.Repository.ClientRepository;
import com.fleetflow.Service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé : " + id));
        return toDto(client);
    }

    @Override
    public ClientDto addClient(ClientDto dto) {
        if (clientRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé : " + dto.getEmail());
        }
        Client saved = clientRepository.save(toEntity(dto));
        return toDto(saved);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé : " + id));
        clientRepository.delete(client);
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé : " + id));
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setVille(dto.getVille());
        client.setTelephone(dto.getTelephone());
        return toDto(clientRepository.save(client));
    }


    @Override
    public Page<ClientDto> getAllClients(Pageable pageable) {

        return clientRepository.findAll(pageable)
                .map(this::toDto);
    }

    private ClientDto toDto(Client c) {
        ClientDto dto = new ClientDto();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setEmail(c.getEmail());
        dto.setVille(c.getVille());
        dto.setTelephone(c.getTelephone());
        return dto;
    }

    private Client toEntity(ClientDto dto) {
        return Client.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .ville(dto.getVille())
                .telephone(dto.getTelephone())
                .build();
    }
}
