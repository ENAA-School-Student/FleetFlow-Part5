package com.fleetflow.Repository;

import com.fleetflow.Entity.Livraison;
import com.fleetflow.enums.StatutLivraison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LivraisonRepository extends JpaRepository<Livraison, Long> {


    Page<Livraison> findByChauffeurId(Long chauffeurId, Pageable pageable);

    List<Livraison> findByStatut(StatutLivraison statut);

    List<Livraison> findByClientId(Long clientId);

    @Query("SELECT l FROM Livraison l WHERE l.dateLivraison BETWEEN :dateDebut AND :dateFin")
    List<Livraison> findLivraisonsBetweenDates(
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    @Query("SELECT l FROM Livraison l WHERE l.adresseDestination LIKE %:ville%")
    List<Livraison> findByVilleDestination(@Param("ville") String ville);
}