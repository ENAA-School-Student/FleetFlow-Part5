package com.fleetflow.Service;

import com.fleetflow.Dto.ClientDto;
import com.fleetflow.Entity.Client;
import com.fleetflow.Mapper.ClientMapper;
import com.fleetflow.Repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientMapper clientMapper;


    @Test
    public void saveClient(){

        ClientDto dto =new ClientDto(2L,"Hiba","Hiba@gamil.com","CASA","0634567890");
        Client client=new Client(2L,"Hiba","Hiba@gamil.com","CASA","0634567890");
        ClientDto saveDto =new ClientDto(2L,"Hiba","Hiba@gamil.com","CASA","0634567890");
        when(clientMapper.toEntity(dto)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);
        when(clientMapper.toDto(client)).thenReturn(saveDto);

        ClientDto result= clientService.addClient(dto);
        assertNotNull(result);
        assertThat(result.getName()).isEqualTo("Hiba");
    }

    @Test
    public void should_saveClientEmailDejaExist(){
        ClientDto dto =new ClientDto(2L,"sara","sara@gamil.com","CASA","23456789");
        when(clientRepository.findByEmail(dto.getEmail())).thenReturn(true);
        assertThrows(RuntimeException.class,()->clientService.addClient(dto));
    }
}