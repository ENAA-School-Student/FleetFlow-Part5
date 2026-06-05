package com.fleetflow.Service;

import com.fleetflow.Dto.VehiculeDto;
import com.fleetflow.Entity.Vehicule;
import com.fleetflow.Mapper.VehiculeMapper;
import com.fleetflow.Repository.VehiculeRepository;
import com.fleetflow.Service.VehiculeService;
import com.fleetflow.enums.StatutVehicule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ViheculeServiceTest {

    @Mock
    private VehiculeRepository vehiculeRepository;

    @InjectMocks
    private VehiculeService vehiculeService;

    @Mock
    private VehiculeMapper vehiculeMapper;


    @Test
    void shouldVehicule_disponible(){
        StatutVehicule status=StatutVehicule.valueOf("DISPONIBLE");
        Vehicule vehicule=new Vehicule();
        vehicule.setStatut(status);
        VehiculeDto dto=new VehiculeDto();
        dto.setStatut(status);
        when(vehiculeRepository.findByStatut(status)).thenReturn(List.of(vehicule));
        when(vehiculeMapper.toDto(List.of(vehicule))).thenReturn(List.of(dto));
        List<VehiculeDto>result=vehiculeService.getVehiculeByStatut(status);
        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(status,result.get(0).getStatut());
    }

    @Test
    void should_Logique_capacite(){
        Vehicule vehicule=new Vehicule();
        vehicule.setCapacite(20);
        VehiculeDto dto=new VehiculeDto();
        dto.setCapacite(20);

        when(vehiculeRepository.findByCapaciteGreaterThan(20)).thenReturn(List.of(vehicule));
        when(vehiculeMapper.toDto(List.of(vehicule))).thenReturn(List.of(dto));
        List<VehiculeDto>result=vehiculeService.getVehiculeCapaciteGreaterThan(20);

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(20,result.get(0).getCapacite());
    }
}