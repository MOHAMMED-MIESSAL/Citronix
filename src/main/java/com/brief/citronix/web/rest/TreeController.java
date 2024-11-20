package com.brief.citronix.web.rest;

import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.dto.TreeDTO;
import com.brief.citronix.mapper.TreeMapper;
import com.brief.citronix.service.TreeService;
import com.brief.citronix.viewmodel.TreeVM;
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
@RequestMapping("/api/trees")
public class TreeController {

    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    @GetMapping
    public ResponseEntity<Page<TreeVM>> getAllTrees(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TreeDTO> treeDTOPage = treeService.findAll(pageable);
        Page<TreeVM> treeVMPage = treeDTOPage.map(treeMapper::toTreeVM);
        return ResponseEntity.ok(treeVMPage);
    }

    @PostMapping
    public ResponseEntity<TreeVM> createTree(@Valid @RequestBody TreeCreateDTO treeCreateDTO) {
        TreeDTO treeDTO = treeService.save(treeCreateDTO);
        TreeVM treeVM = treeMapper.toTreeVM(treeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(treeVM);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreeVM> updateTree(@PathVariable UUID id, @Valid @RequestBody TreeCreateDTO treeCreateDTO) {
        TreeDTO treeDTO = treeService.update(id, treeCreateDTO);
        TreeVM treeVM = treeMapper.toTreeVM(treeDTO);
        return ResponseEntity.ok(treeVM);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable UUID id) {
        treeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeVM> getTree(@PathVariable UUID id) {
        Optional<TreeDTO> treeDTO = treeService.findTreeById(id);
        return treeDTO.map(tree -> ResponseEntity.ok(treeMapper.toTreeVM(tree)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
