package com.brief.citronix.repository;

import com.brief.citronix.domain.Farm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FarmRepository extends JpaRepository<Farm, UUID> {
    Page<Farm> findAll(Pageable pageable);

}
