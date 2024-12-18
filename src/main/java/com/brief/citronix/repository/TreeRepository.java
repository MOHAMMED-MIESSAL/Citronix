package com.brief.citronix.repository;

import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
    long countByField(Field field);
    List<Tree> findAllByField(Field field);
}
