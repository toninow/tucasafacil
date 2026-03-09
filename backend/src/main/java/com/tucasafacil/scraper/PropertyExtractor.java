package com.tucasafacil.scraper;

import com.tucasafacil.entity.Property;

public interface PropertyExtractor {

    boolean canExtract(String url);

    Property extract(String url) throws Exception;
}