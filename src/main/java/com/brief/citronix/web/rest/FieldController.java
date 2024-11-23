package com.brief.citronix.web.rest;


import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.service.FieldService;
import com.brief.citronix.viewmodel.FieldVM;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    /**
     * Get all fields
     */
    @GetMapping
    public ResponseEntity<Page<FieldVM>> getAllFields(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fieldService.findAll(pageable).map(fieldMapper::toFieldVM));

    }

    /**
     * Create a new field
     */
    @PostMapping
    public ResponseEntity<FieldVM> createField(@Valid @RequestBody FieldCreateDTO fieldCreateDTO) {
        Field field = fieldService.save(fieldCreateDTO);
        FieldVM fieldVM = fieldMapper.toFieldVM(field);
        return ResponseEntity.status(HttpStatus.CREATED).body(fieldVM);

    }

    /**
     * Get field by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldVM> getFieldById(@PathVariable UUID id) {
        Optional<Field> field = fieldService.findFieldById(id);
        FieldVM fieldVM = fieldMapper.toFieldVM(field.get());
        return ResponseEntity.ok(fieldVM);

    }

    /**
     * Delete field
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Update field
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldVM> updateField(@PathVariable UUID id, @Valid @RequestBody FieldCreateDTO fieldCreateDTO) {
        Field field = fieldService.update(id, fieldCreateDTO);
        FieldVM fieldVM = fieldMapper.toFieldVM(field);
        return ResponseEntity.ok(fieldVM);
    }


}
