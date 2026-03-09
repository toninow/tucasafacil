package com.tucasafacil.service;

import com.tucasafacil.dto.FavoritePropertyDto;
import com.tucasafacil.entity.FavoriteProperty;
import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.User;
import com.tucasafacil.repository.FavoritePropertyRepository;
import com.tucasafacil.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoritePropertyRepository favoriteRepository;
    private final PropertyRepository propertyRepository;
    private final CurrentUserService currentUserService;

    public FavoriteService(FavoritePropertyRepository favoriteRepository,
                           PropertyRepository propertyRepository,
                           CurrentUserService currentUserService) {
        this.favoriteRepository = favoriteRepository;
        this.propertyRepository = propertyRepository;
        this.currentUserService = currentUserService;
    }

    public FavoritePropertyDto addFavorite(Long propertyId) {
        User currentUser = getCurrentUser();
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Propiedad no encontrada"));

        if (favoriteRepository.existsByUserIdAndPropertyId(currentUser.getId(), propertyId)) {
            throw new RuntimeException("Ya es favorito");
        }

        FavoriteProperty favorite = new FavoriteProperty();
        favorite.setUser(currentUser);
        favorite.setProperty(property);
        FavoriteProperty saved = favoriteRepository.save(favorite);

        return new FavoritePropertyDto(saved.getId(), propertyId, property.getTitle(), property.getSourceUrl(), saved.getAddedAt());
    }

    public void removeFavorite(Long propertyId) {
        User currentUser = getCurrentUser();
        FavoriteProperty favorite = favoriteRepository.findByUserIdAndPropertyId(currentUser.getId(), propertyId)
                .orElseThrow(() -> new RuntimeException("Favorito no encontrado"));
        favoriteRepository.delete(favorite);
    }

    public List<FavoritePropertyDto> getFavorites() {
        User currentUser = getCurrentUser();
        return favoriteRepository.findByUserId(currentUser.getId()).stream()
                .map(fav -> new FavoritePropertyDto(
                        fav.getId(),
                        fav.getProperty().getId(),
                        fav.getProperty().getTitle(),
                        fav.getProperty().getSourceUrl(),
                        fav.getAddedAt()))
                .collect(Collectors.toList());
    }

    public boolean isFavorite(Long propertyId) {
        User currentUser = getCurrentUser();
        return favoriteRepository.existsByUserIdAndPropertyId(currentUser.getId(), propertyId);
    }

    private User getCurrentUser() {
        return currentUserService.getCurrentUserOrDemo();
    }
}
