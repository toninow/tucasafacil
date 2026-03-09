package com.tucasafacil.repository;

import com.tucasafacil.entity.ExtractionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtractionLogRepository extends JpaRepository<ExtractionLog, Long> {
}