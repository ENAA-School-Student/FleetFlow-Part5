package com.fleetflow.Dto;

import com.fleetflow.enums.StatutLivraison;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LivraisonDTO {
    private Long id;

    @NotNull(message = "La date est obligatoire")
    private LocalDate dateLivraison;
    @NotBlank(message = "L'adresse de départ est obligatoire")
    private String adresseDepart;

    @NotBlank(message = "L'adresse destination est obligatoire")
    private String adresseDestination;
    private StatutLivraison statut;

    private Long clientId;
    private Long chauffeurId;
    private Long vehiculeId;
}