package com.fleetflow.Service;

import com.fleetflow.Dto.LivraisonDTO;
import com.fleetflow.Entity.*;
import com.fleetflow.enums.StatutLivraison;
import com.fleetflow.enums.StatutVehicule;
import com.fleetflow.Mapper.LivraisonMapper;
import com.fleetflow.Repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests du LivraisonService")
class LivraisonServiceTest {

    @Mock private LivraisonRepository livraisonRepository;
    @Mock private ChauffeurRepository chauffeurRepository;
    @Mock private VehiculeRepository vehiculeRepository;
    @Mock private LivraisonMapper livraisonMapper;

    @InjectMocks
    private LivraisonService livraisonService;

    @Test
    void creerLivraison_statutInitialEstEnAttente() {

        LivraisonDTO dtoInput = new LivraisonDTO();
        dtoInput.setDateLivraison(LocalDate.now());
        dtoInput.setAdresseDepart("Casa");
        dtoInput.setAdresseDestination("Rabat");
        dtoInput.setClientId(1L);

        Livraison livraisonSaved = new Livraison();
        livraisonSaved.setId(10L);
        livraisonSaved.setDateLivraison(LocalDate.now());
        livraisonSaved.setAdresseDepart("Casa");
        livraisonSaved.setAdresseDestination("Rabat");
        livraisonSaved.setStatut(StatutLivraison.ENATTENTE);

        LivraisonDTO dtoResultat = new LivraisonDTO();
        dtoResultat.setId(10L);
        dtoResultat.setStatut(StatutLivraison.ENATTENTE);
        dtoResultat.setClientId(1L);

        when(livraisonMapper.toEntity(dtoInput)).thenReturn(livraisonSaved);
        when(livraisonRepository.save(any(Livraison.class))).thenReturn(livraisonSaved);
        when(livraisonMapper.toDTO(livraisonSaved)).thenReturn(dtoResultat);


        LivraisonDTO result = livraisonService.createLivraison(dtoInput);


        assertThat(result).isNotNull();
        assertThat(result.getStatut()).isEqualTo(StatutLivraison.ENATTENTE);
        verify(livraisonRepository).save(any(Livraison.class));
    }

    @Test
    void assignerChauffeurEtVehicule() {

        Livraison livraison = new Livraison();
        livraison.setId(1L);
        livraison.setStatut(StatutLivraison.ENATTENTE);

        Chauffeur chauffeur = new Chauffeur();
        chauffeur.setId(2L);
        chauffeur.setNom("hatim");
        chauffeur.setDisponible(true);

        Vehicule vehicule = new Vehicule();
        vehicule.setId(3L);
        vehicule.setMatricule("A-111");
        vehicule.setStatut(StatutVehicule.DISPONIBLE);

        LivraisonDTO dto = new LivraisonDTO();
        dto.setId(1L);
        dto.setChauffeurId(2L);
        dto.setVehiculeId(3L);
        dto.setStatut(StatutLivraison.ENCOURS);

        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        when(chauffeurRepository.findById(2L)).thenReturn(Optional.of(chauffeur));
        when(vehiculeRepository.findById(3L)).thenReturn(Optional.of(vehicule));
        when(livraisonRepository.save(any(Livraison.class))).thenReturn(livraison);
        when(chauffeurRepository.save(any(Chauffeur.class))).thenReturn(chauffeur);
        when(vehiculeRepository.save(any(Vehicule.class))).thenReturn(vehicule);
        when(livraisonMapper.toDTO(livraison)).thenReturn(dto);


        LivraisonDTO resultat = livraisonService.assignerChauffeurEtVehicule(1L, 2L, 3L);


        assertThat(resultat).isNotNull();
        assertThat(resultat.getChauffeurId()).isEqualTo(2L);
        assertThat(resultat.getVehiculeId()).isEqualTo(3L);
        verify(livraisonRepository).save(any(Livraison.class));
    }

    @Test
    void modifierStatutLivraison() {

        Livraison livraison = new Livraison();
        livraison.setId(1L);
        livraison.setStatut(StatutLivraison.ENATTENTE);
        LivraisonDTO dto = new LivraisonDTO();
        dto.setId(1L);
        dto.setStatut(StatutLivraison.ENCOURS);

        when(livraisonRepository.findById(1L)).thenReturn(Optional.of(livraison));
        when(livraisonRepository.save(any(Livraison.class))).thenReturn(livraison);
        when(livraisonMapper.toDTO(livraison)).thenReturn(dto);


        LivraisonDTO result = livraisonService.modifierStatut(1L, StatutLivraison.ENCOURS);


        assertThat(result.getStatut()).isEqualTo(StatutLivraison.ENCOURS);
        verify(livraisonRepository).save(any(Livraison.class));
    }
}