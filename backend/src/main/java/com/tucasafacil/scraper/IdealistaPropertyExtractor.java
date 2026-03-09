package com.tucasafacil.scraper;

import com.tucasafacil.entity.Property;
import com.tucasafacil.entity.PropertyImage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(10)
public class IdealistaPropertyExtractor implements PropertyExtractor {

    private static final Pattern PRICE_PATTERN = Pattern.compile("([0-9][0-9\\.,]*)\\s*€");
    private static final Pattern M2_PATTERN = Pattern.compile("(\\d+)\\s*m(?:2|\\u00B2)", Pattern.CASE_INSENSITIVE);
    private static final Pattern BEDROOMS_PATTERN = Pattern.compile("(\\d+)\\s*(hab\\.|habitaciones?)", Pattern.CASE_INSENSITIVE);
    private static final Pattern BATHROOMS_PATTERN = Pattern.compile("(\\d+)\\s*ba(?:n|\\u00F1)os?", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean canExtract(String url) {
        return url != null && url.contains("idealista.");
    }

    @Override
    public Property extract(String url) throws Exception {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                .timeout(15000)
                .get();

        String bodyText = doc.body() != null ? doc.body().text() : "";
        String title = firstNonBlank(
                text(doc, "h1.main-info__title-main"),
                text(doc, "h1"),
                attr(doc, "meta[property=og:title]", "content")
        );
        String location = firstNonBlank(
                text(doc, ".main-info__title-minor"),
                text(doc, ".main-info__title-location"),
                extractLocationFromBody(bodyText)
        );
        String description = firstNonBlank(
                text(doc, ".comment p"),
                text(doc, ".adCommentsLanguage p"),
                attr(doc, "meta[property=og:description]", "content")
        );

        BigDecimal price = extractPrice(firstNonBlank(
                text(doc, ".info-data-price"),
                bodyText
        ));

        Integer squareMeters = extractInteger(M2_PATTERN, bodyText);
        Integer bedrooms = extractInteger(BEDROOMS_PATTERN, bodyText);
        Integer bathrooms = extractInteger(BATHROOMS_PATTERN, bodyText);

        Property property = new Property();
        property.setSourceUrl(url);
        property.setTitle(title != null ? title : "Anuncio en idealista");
        property.setDescription(description);
        property.setPrice(price);
        property.setSquareMeters(squareMeters);
        property.setBedrooms(bedrooms);
        property.setBathrooms(bathrooms);
        property.setAddress(location);
        property.setListingType("alquiler");
        property.setExtractedAt(LocalDateTime.now());

        addImages(property, doc);
        parseLocation(property, location);

        return property;
    }

    private void addImages(Property property, Document doc) {
        Set<String> urls = new LinkedHashSet<>();

        String ogImage = attr(doc, "meta[property=og:image]", "content");
        if (ogImage != null) urls.add(ogImage);

        for (Element image : doc.select("img[src], img[data-src], source[srcset]")) {
            String src = image.hasAttr("src") ? image.absUrl("src") : "";
            if (src.isBlank() && image.hasAttr("data-src")) src = image.absUrl("data-src");
            if (src.isBlank() && image.hasAttr("srcset")) src = firstSrcSet(image.attr("srcset"));
            if (src.isBlank()) continue;
            if (!src.startsWith("http")) continue;
            if (!isLikelyImage(src)) continue;
            urls.add(src);
            if (urls.size() >= 12) break;
        }

        int idx = 0;
        for (String imageUrl : urls) {
            PropertyImage image = new PropertyImage();
            image.setProperty(property);
            image.setImageUrl(imageUrl);
            image.setSortOrder(idx++);
            property.getImages().add(image);
        }
    }

    private static String firstSrcSet(String srcset) {
        if (srcset == null || srcset.isBlank()) return "";
        String first = srcset.split(",")[0].trim();
        String[] parts = first.split("\\s+");
        return parts.length > 0 ? parts[0] : "";
    }

    private static boolean isLikelyImage(String url) {
        String lower = url.toLowerCase();
        return lower.contains("idealista.com")
                && (lower.contains(".jpg") || lower.contains(".jpeg") || lower.contains(".png") || lower.contains(".webp"));
    }

    private static void parseLocation(Property property, String location) {
        if (location == null || location.isBlank()) return;

        String[] parts = location.split(",");
        if (parts.length == 1) {
            String single = parts[0].trim();
            property.setAddress(single);
            property.setCity(single);
            return;
        }

        if (parts.length > 0) {
            property.setAddress(parts[0].trim());
        }
        if (parts.length > 1) {
            property.setCity(parts[parts.length - 1].trim());
        }
        if (parts.length > 2) {
            property.setDistrict(parts[1].trim());
        }
    }

    private static String extractLocationFromBody(String body) {
        if (body == null || body.isBlank()) return null;
        Matcher matcher = Pattern.compile("([\\p{L}\\d\\s\\-]+)\\s+Ver mapa").matcher(body);
        if (matcher.find()) return matcher.group(1).trim();
        return null;
    }

    private static BigDecimal extractPrice(String text) {
        if (text == null) return null;
        Matcher matcher = PRICE_PATTERN.matcher(text);
        if (!matcher.find()) return null;
        String numeric = matcher.group(1).replace(".", "").replace(",", ".");
        try {
            return new BigDecimal(numeric);
        } catch (Exception e) {
            return null;
        }
    }

    private static Integer extractInteger(Pattern pattern, String text) {
        if (text == null) return null;
        Matcher matcher = pattern.matcher(text);
        if (!matcher.find()) return null;
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (Exception e) {
            return null;
        }
    }

    private static String text(Document doc, String selector) {
        Element el = doc.selectFirst(selector);
        if (el == null) return null;
        String value = el.text();
        return value != null && !value.isBlank() ? value.trim() : null;
    }

    private static String attr(Document doc, String selector, String attrName) {
        Element el = doc.selectFirst(selector);
        if (el == null) return null;
        String value = el.attr(attrName);
        return value != null && !value.isBlank() ? value.trim() : null;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) return value.trim();
        }
        return null;
    }
}
