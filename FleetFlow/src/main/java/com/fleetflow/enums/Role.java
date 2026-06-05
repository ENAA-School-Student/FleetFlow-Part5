package com.fleetflow.enums;

public enum Role {
    ADMIN,
    MANAGER,  // Gère clients + livraisons, consulte chauffeurs/véhicules
    CHAUFFEUR // Consulte et met à jour ses propres livraisons
}
