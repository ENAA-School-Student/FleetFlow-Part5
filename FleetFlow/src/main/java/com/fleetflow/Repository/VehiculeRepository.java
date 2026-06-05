package com.fleetflow.Repository;


import com.fleetflow.Entity.Vehicule;
import com.fleetflow.enums.StatutVehicule;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    @Query("select  v from Vehicule v where v.statut =:statut ")
    List<Vehicule>findByStatut(@Param("statut") StatutVehicule statut);

    @Query("select v from Vehicule  v where v.capacite> :seuil")
    List<Vehicule>findByCapaciteGreaterThan(@Param("seuil")double seuil);

    boolean existsByMatricule(String matricule);

}
