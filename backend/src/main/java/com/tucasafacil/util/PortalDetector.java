package com.tucasafacil.util;

import com.tucasafacil.entity.SourcePortal;
import com.tucasafacil.repository.SourcePortalRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Optional;

@Component
public class PortalDetector {

    private final SourcePortalRepository sourcePortalRepository;

    public PortalDetector(SourcePortalRepository sourcePortalRepository) {
        this.sourcePortalRepository = sourcePortalRepository;
    }

    public Optional<SourcePortal> detectPortal(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host != null) {
                return sourcePortalRepository.findByDomain(host);
            }
        } catch (Exception e) {
            // Log error
        }
        return Optional.empty();
    }
}