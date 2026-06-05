package com.fleetflow.Service;
import com.fleetflow.Dto.ChauffeurDTO;
import com.fleetflow.Entity.Chauffeur;
import com.fleetflow.Mapper.ChauffeurMapper;
import com.fleetflow.Repository.ChauffeurRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests du ChauffeurService")
class ChauffeurServiceTest {

    @Mock
    private ChauffeurRepository chauffeurRepository;

    @Mock
    private ChauffeurMapper chauffeurMapper;

    @InjectMocks
    private ChauffeurService chauffeurService;

    @Test
    @DisplayName("Lister uniquement les chauffeurs disponibles")
    void listerChauffeursDisponibles() {

        Chauffeur c1 = new Chauffeur();
        c1.setId(1L);
        c1.setNom("hatim");
        c1.setDisponible(true);
        c1.setPermisType("B");

        Chauffeur c2 = new Chauffeur();
        c2.setId(2L);
        c2.setNom("Karim");
        c2.setDisponible(true);
        c2.setPermisType("C");

        ChauffeurDTO dto1 = new ChauffeurDTO();
        dto1.setId(1L);
        dto1.setNom("hatim");
        dto1.setDisponible(true);
        dto1.setPermisType("B");

        ChauffeurDTO dto2 = new ChauffeurDTO();
        dto2.setId(2L);
        dto2.setNom("Karim");
        dto2.setDisponible(true);
        dto2.setPermisType("C");

        when(chauffeurRepository.findByDisponibleTrue()).thenReturn(List.of(c1, c2));
        when(chauffeurMapper.toDTOs(List.of(c1, c2))).thenReturn(List.of(dto1, dto2));


        List<ChauffeurDTO> result = chauffeurService.getChauffeursdisponibles();


        assertThat(result).hasSize(2);
        assertThat(result).allMatch(cDto -> cDto.getDisponible() == true);
    }
}