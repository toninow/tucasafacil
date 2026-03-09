package com.tucasafacil.controller;

import com.tucasafacil.dto.FavoritePropertyDto;
import com.tucasafacil.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{propertyId}")
    public ResponseEntity<FavoritePropertyDto> addFavorite(@PathVariable Long propertyId) {
        FavoritePropertyDto favorite = favoriteService.addFavorite(propertyId);
        return ResponseEntity.ok(favorite);
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long propertyId) {
        favoriteService.removeFavorite(propertyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoritePropertyDto>> getFavorites() {
        List<FavoritePropertyDto> favorites = favoriteService.getFavorites();
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{propertyId}/is-favorite")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long propertyId) {
        boolean isFav = favoriteService.isFavorite(propertyId);
        return ResponseEntity.ok(isFav);
    }
}