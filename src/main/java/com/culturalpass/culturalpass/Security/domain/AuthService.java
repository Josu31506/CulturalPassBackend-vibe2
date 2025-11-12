package com.culturalpass.culturalpass.Security.domain;

import com.culturalpass.culturalpass.Security.dto.LoginRequestDto;
import com.culturalpass.culturalpass.Security.dto.LoginResponseDto;
import com.culturalpass.culturalpass.Security.dto.RegisterRequestDto;
import com.culturalpass.culturalpass.Security.dto.RegisterResponseDto;
import com.culturalpass.culturalpass.Security.exceptions.*;
import com.culturalpass.culturalpass.User.domain.User;
import com.culturalpass.culturalpass.User.domain.UserRole;
import com.culturalpass.culturalpass.User.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

            if (userOptional.isEmpty()) {
                throw new UserNotFoundException("Usuario no encontrado con email: " + loginRequest.getEmail());
            }
            User user = userOptional.get();
            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                throw new AuthenticationFailedException("Contraseña incorrecta");
            }
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            return LoginResponseDto.builder()
                    .token(token)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(user.getRole().name())
                    .build();
        } catch (UserNotFoundException | AuthenticationFailedException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthenticationFailedException("Error en el proceso de autenticación", e);
        }
    }

    public RegisterResponseDto register(RegisterRequestDto registerRequest) {
        try {
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                throw new UserAlreadyExistsException("Ya existe un usuario con el email: " + registerRequest.getEmail());
            }

            if (userRepository.existsByCellphone(registerRequest.getCellphone())) {
                throw new UserAlreadyExistsException("Ya existe un usuario con el celular: " + registerRequest.getCellphone());
            }

            User newUser = User.builder()
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(UserRole.CLIENTE)
                    .cellphone(registerRequest.getCellphone())
                    .build();

            User savedUser = userRepository.save(newUser);
            String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole().name());
            return new RegisterResponseDto(token);

        } catch (UserAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new RegistrationException("Error en el proceso de registro", e);
        }
    }
}