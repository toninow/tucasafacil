package com.tucasafacil.repository;

import com.tucasafacil.entity.SavedComparison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedComparisonRepository extends JpaRepository<SavedComparison, Long> {

    List<SavedComparison> findByUserId(Long userId);
}