package com.example.angula.database.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class AngulaUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 20, unique = true)
    String username;

    @JsonIgnore
    String password;

    @Column(length = 10)
    @Builder.Default
    String role = "USER";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(role.split(","))
            .map(String::trim).map(role -> "ROLE_" + role).map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    public String toString() {

        return this.id + " " +
                this.username + " " +
                this.password + " " +
                this.role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AngulaUser)) {
            return false;
        }
        return id != null && id.equals(((AngulaUser) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}