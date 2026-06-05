package com.fleetflow.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chauffeur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chauffeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "permisType", nullable = false)
    private String permisType;

    private Boolean disponible;
}