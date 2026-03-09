package com.tucasafacil.repository;

import com.tucasafacil.entity.FavoriteProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritePropertyRepository extends JpaRepository<FavoriteProperty, Long> {

    List<FavoriteProperty> findByUserId(Long userId);
    Optional<FavoriteProperty> findByUserIdAndPropertyId(Long userId, Long propertyId);
    boolean existsByUserIdAndPropertyId(Long userId, Long propertyId);
}