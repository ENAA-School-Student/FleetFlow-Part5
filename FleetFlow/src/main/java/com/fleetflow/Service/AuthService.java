package com.fleetflow.Service;

import com.fleetflow.Dto.AuthResponse;
import com.fleetflow.Dto.LoginRequest;
import com.fleetflow.Dto.RegisterRequest;


public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
