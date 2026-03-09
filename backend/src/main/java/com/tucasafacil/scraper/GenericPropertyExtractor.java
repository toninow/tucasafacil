package com.tucasafacil.scraper;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.SourcePortal;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class GenericPropertyExtractor implements PropertyExtractor {

    @Override
    public boolean canExtract(String url) {
        return true; // Fallback
    }

    @Override
    public Property extract(String url) throws Exception {
        // Mock data para simulación
        Property property = new Property();
        property.setSourceUrl(url);
        property.setTitle("Piso en Madrid Centro - Mock");
        property.setDescription("Descripción mock de la vivienda. Incluye datos simulados para pruebas.");
        property.setPrice(BigDecimal.valueOf(1200));
        property.setSquareMeters(80);
        property.setBedrooms(2);
        property.setBathrooms(1);
        property.setHasElevator(true);
        property.setFurnished(false);
        property.setAdvertiserType("particular");
        property.setExtractedAt(LocalDateTime.now());

        // Nota: En producción, integrar Playwright aquí para scraping real
        // Ejemplo: Usar Playwright para navegar a url y extraer datos con selectores

        return property;
    }
}