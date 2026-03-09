package com.tucasafacil.repository;

import com.tucasafacil.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Long> {

    // Para single-user, asumimos solo uno
    UserPreference findFirstByOrderByIdAsc();
}