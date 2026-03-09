package com.tucasafacil.dto;

import jakarta.validation.constraints.NotBlank;

public class AnalyzeUrlRequest {

    @NotBlank(message = "La URL no puede estar vacía")
    private String url;

    // Getter y Setter
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}