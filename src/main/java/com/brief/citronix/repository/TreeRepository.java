package com.brief.citronix.repository;

import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TreeRepository extends JpaRepository<Tree, UUID> {
    List<Tree> findTreesByFieldId(UUID fieldId);
    @Modifying
    @Query("DELETE FROM Tree t WHERE t.field.id = :fieldId")
    void deleteByFieldId(UUID fieldId);

}
