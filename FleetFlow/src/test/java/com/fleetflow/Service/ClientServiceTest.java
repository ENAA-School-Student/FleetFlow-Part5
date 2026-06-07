package com.fleetflow.Service;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import com.fleetflow.Mapper.ClientMapper;
import com.fleetflow.Repository.ClientRepository;
import com.fleetflow.Service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;



    @Test
    public void saveClient(){

        ClientDto dto = new ClientDto();
        dto.setName("Hiba"); dto.setEmail("hiba@gmail.com");
        dto.setVille("CASA"); dto.setTelephone("0634567890");

        Client savedClient = Client.builder()
                .id(2L).name("Hiba").email("hiba@gmail.com")
                .ville("CASA").telephone("0634567890").build();

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        ClientDto result = clientService.addClient(dto);

        assertNotNull(result);
        assertThat(result.getName()).isEqualTo("Hiba");
    }

    @Test
    public void should_saveClientEmailDejaExist(){
        ClientDto dto = new ClientDto();
        dto.setEmail("sara@gmail.com");

        when(clientRepository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> clientService.addClient(dto));
    }
}