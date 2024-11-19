package com.brief.citronix.web.rest;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldDto;
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

@RestController
@RequestMapping("/api/fields")
public class FieldController {
    private final FieldService fieldService;
    private final FieldMapper fieldMapper;

    public FieldController(FieldService fieldService , FieldMapper fieldMapper) {
        this.fieldService = fieldService;
        this.fieldMapper = fieldMapper;
    }

    /*
     * Get all fields
     */
    @GetMapping
    Page<FieldDto> getAllFields(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
     return fieldService.findAll(pageable);
    }

    /*
     * Create a new field
     */
    @PostMapping
    public ResponseEntity<FieldVM> createField(@RequestBody @Valid FieldDto fieldDto) {
        // Convertir le DTO en entité Field
        Field field = fieldMapper.fieldDtoToField(fieldDto);

        // Associer une ferme via l'ID du DTO
        Farm farm = new Farm();
        farm.setId(fieldDto.getFarm_id());
        field.setFarm(farm);

        // Enregistrer le champ et retourner le DTO résultant
        FieldDto savedFieldDto = fieldService.save(field);

        return new ResponseEntity<>(fieldMapper.fieldToFieldVM(field), HttpStatus.CREATED);
    }



}
