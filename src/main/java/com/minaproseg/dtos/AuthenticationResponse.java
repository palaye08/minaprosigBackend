package com.minaproseg.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private String refreshToken;

    @Builder.Default  // ✅ Ajoutez cette annotation
    private String type = "Bearer";

    private UtilisateurDTO utilisateur;
}