package com.tucasafacil.repository;

import com.tucasafacil.entity.SourcePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourcePortalRepository extends JpaRepository<SourcePortal, Long> {

    Optional<SourcePortal> findByDomain(String domain);
}