package com.brief.citronix.repository;

import com.brief.citronix.domain.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FieldRepository extends JpaRepository<Field, UUID> {
    List<Field> findByFarmId(UUID farmId);
    long countByFarmId(UUID farmId);
}
