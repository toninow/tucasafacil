package com.tucasafacil.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "extraction_log")
public class ExtractionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sourceUrl;

    private String sourcePortal;
    private String status; // SUCCESS, ERROR, etc.
    private String message;
    private String rawDataSnippet;
    private LocalDateTime createdAt;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public String getSourcePortal() { return sourcePortal; }
    public void setSourcePortal(String sourcePortal) { this.sourcePortal = sourcePortal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRawDataSnippet() { return rawDataSnippet; }
    public void setRawDataSnippet(String rawDataSnippet) { this.rawDataSnippet = rawDataSnippet; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}