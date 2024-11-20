package com.brief.citronix.web.rest;


import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.dto.FieldDTO;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.service.FieldService;
import com.brief.citronix.viewmodel.FieldVM;
import jakarta.validation.Valid;
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
public class FieldController {

    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(FieldService fieldService, FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    @GetMapping
    public ResponseEntity<Page<FieldVM>> getAllFields(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FieldDTO> fieldDTOPage = fieldService.findAll(pageable);
        Page<FieldVM> fieldVMPage = fieldDTOPage.map(fieldMapper::toFieldVM);
        return ResponseEntity.ok(fieldVMPage);

    }

    @PostMapping
    public ResponseEntity<FieldVM> createField(@Valid @RequestBody FieldCreateDTO fieldCreateDTO) {
        FieldDTO fieldDTO = fieldService.save(fieldCreateDTO);
        FieldVM fieldVM = fieldMapper.toFieldVM(fieldDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(fieldVM);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldVM> getFieldById(@PathVariable UUID id) {
        Optional<FieldDTO> fieldDTO = fieldService.findFieldById(id);
        return fieldDTO.map(fieldMapper::toFieldVM)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable UUID id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldVM> updateField(@PathVariable UUID id, @Valid @RequestBody FieldCreateDTO fieldCreateDTO) {
        FieldDTO fieldDTO = fieldService.update(id, fieldCreateDTO);
        FieldVM fieldVM = fieldMapper.toFieldVM(fieldDTO);
        return ResponseEntity.ok(fieldVM);
    }


}
