package com.bruno.banking_platform.auth.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime createdAt;

    // UserDetails — qual o "login" desse usuário
    @Override
    public String getUsername() {
        return email;
    }

    // UserDetails — permissões/roles (vazio por enquanto, implementamos depois)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // UserDetails — conta expirada?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // UserDetails — conta bloqueada?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // UserDetails — credenciais expiradas?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // UserDetails — conta ativa?
    @Override
    public boolean isEnabled() {
        return true;
    }
}