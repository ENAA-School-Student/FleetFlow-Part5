package com.fleetflow.Entity;


import com.fleetflow.enums.StatutVehicule;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vehicule")
public class Vehicule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String matricule;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private Integer capacite;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVehicule statut;

    @OneToMany(mappedBy = "vehicule",cascade = CascadeType.ALL)
    @ToString.Exclude
     private List<Livraison> livraisons;
}
