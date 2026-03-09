package com.tucasafacil.repository;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    boolean existsBySourceUrl(String sourceUrl);
    Optional<Property> findBySourceUrl(String sourceUrl);

    List<Property> findByUserId(Long userId);
    List<Property> findByUser(User user);

    @Query("SELECT p FROM Property p WHERE p.user.id = :userId AND " +
           "(:priceMin IS NULL OR p.price >= :priceMin) AND " +
           "(:priceMax IS NULL OR p.price <= :priceMax) AND " +
           "(:district IS NULL OR p.district = :district) AND " +
           "(:bedrooms IS NULL OR p.bedrooms >= :bedrooms) AND " +
           "(:bathrooms IS NULL OR p.bathrooms >= :bathrooms) AND " +
           "(:hasElevator IS NULL OR p.hasElevator = :hasElevator) AND " +
           "(:furnished IS NULL OR p.furnished = :furnished) AND " +
           "(:advertiserType IS NULL OR p.advertiserType = :advertiserType) AND " +
           "(:scoreMin IS NULL OR p.score >= :scoreMin)")
    List<Property> findFilteredPropertiesByUser(@Param("userId") Long userId,
                                                @Param("priceMin") BigDecimal priceMin,
                                                @Param("priceMax") BigDecimal priceMax,
                                                @Param("district") String district,
                                                @Param("bedrooms") Integer bedrooms,
                                                @Param("bathrooms") Integer bathrooms,
                                                @Param("hasElevator") Boolean hasElevator,
                                                @Param("furnished") Boolean furnished,
                                                @Param("advertiserType") String advertiserType,
                                                @Param("scoreMin") BigDecimal scoreMin);
}
