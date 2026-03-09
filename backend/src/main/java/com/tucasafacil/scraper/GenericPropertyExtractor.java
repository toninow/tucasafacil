package com.tucasafacil.scraper;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.PropertyImage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class GenericPropertyExtractor implements PropertyExtractor {

    @Override
    public boolean canExtract(String url) {
        return true; // Fallback
    }

    @Override
    public Property extract(String url) throws Exception {
        // Mock data para simulacion
        Property property = new Property();
        property.setSourceUrl(url);
        property.setTitle("Piso en Madrid Centro - Mock");
        property.setDescription(
            "Piso reformado con buena luz natural. Se solicita contrato indefinido, 3 ultimas nominas y 1 mes de fianza. " +
            "No mascotas. Zona con supermercados y transporte cercano. " +
            "Video: https://samplelib.com/lib/preview/mp4/sample-5s.mp4"
        );
        property.setPrice(BigDecimal.valueOf(1200));
        property.setSquareMeters(80);
        property.setBedrooms(2);
        property.setBathrooms(1);
        property.setHasElevator(true);
        property.setFurnished(true);
        property.setHasBalcony(true);
        property.setAdvertiserType("particular");
        property.setListingType("alquiler");
        property.setAddress("Calle Gran Via 99");
        property.setDistrict("Centro");
        property.setNeighborhood("Malasana");
        property.setPostalCode("28013");
        property.setExtractedAt(LocalDateTime.now());

        addMockImages(property);

        // Nota: En produccion, integrar Playwright aqui para scraping real.
        return property;
    }

    private void addMockImages(Property property) {
        String[] imageUrls = {
            "https://picsum.photos/seed/tcf-living/1200/800",
            "https://picsum.photos/seed/tcf-bedroom/1200/800",
            "https://picsum.photos/seed/tcf-kitchen/1200/800",
            "https://picsum.photos/seed/tcf-bathroom/1200/800"
        };

        for (int i = 0; i < imageUrls.length; i++) {
            PropertyImage image = new PropertyImage();
            image.setProperty(property);
            image.setImageUrl(imageUrls[i]);
            image.setSortOrder(i);
            property.getImages().add(image);
        }
    }
}
