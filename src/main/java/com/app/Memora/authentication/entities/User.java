package com.app.Memora.authentication.entities;


import com.app.Memora.enroll.entities.Enroll;
import com.app.Memora.settings.entities.Settings;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    public String getFullName() {
        return fullName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Enroll> enrollments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Settings settings;

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @JsonIgnore
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Settings getSettings() {
        return settings;
    }
}