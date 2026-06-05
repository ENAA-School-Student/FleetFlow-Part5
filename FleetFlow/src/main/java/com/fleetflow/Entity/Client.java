package com.fleetflow.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String ville;
    @Column(nullable = false)
    private String telephone;

    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Livraison>livraisons;


    public Client(long l, String hiba, String mail, String casa, String number) {
    }
}
