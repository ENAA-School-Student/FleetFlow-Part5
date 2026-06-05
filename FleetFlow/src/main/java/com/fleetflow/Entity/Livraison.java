package com.fleetflow.Entity;


import com.fleetflow.enums.StatutLivraison;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "livraison")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateLivraison;

    @Column(nullable = false)
    private String adresseDepart;

    @Column(nullable = false)
    private String adresseDestination;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutLivraison statut;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "chauffeur_id")
    private Chauffeur chauffeur;

    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    private Vehicule vehicule;


}