package com.fleetflow.Service;

import com.fleetflow.Dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClientService {
    ClientDto getClientById(Long id);
    ClientDto addClient(ClientDto dto);
    void deleteClient(Long id);
    ClientDto updateClient(Long id, ClientDto dto);
    Page<ClientDto> getAllClients(Pageable pageable); // ✅ Pagination + tri
}
