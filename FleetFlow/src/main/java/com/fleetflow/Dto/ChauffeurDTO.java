package com.fleetflow.Dto;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChauffeurDTO {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    @NotBlank(message = "Le telephone est obligatoire")
    private String telephone;
    @NotBlank(message = "Le permis est obligatoire")
    private String permisType;
    private Boolean disponible;
}