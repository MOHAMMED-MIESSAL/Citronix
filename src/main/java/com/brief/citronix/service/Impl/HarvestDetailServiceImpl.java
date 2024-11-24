package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.enums.Season;
import com.brief.citronix.mapper.HarvestDetailMapper;
import com.brief.citronix.repository.HarvestDetailRepository;
import com.brief.citronix.service.HarvestDetailService;
import com.brief.citronix.service.HarvestService;
import com.brief.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HarvestDetailServiceImpl implements HarvestDetailService {

    private final HarvestDetailRepository harvestDetailRepository;
    private final HarvestService harvestService;
    private final TreeService treeService;
    private final HarvestDetailMapper harvestDetailMapper;


    @Override
    public Page<HarvestDetail> findAll(Pageable pageable) {
        return harvestDetailRepository.findAll(pageable);
    }


    @Override
    public HarvestDetail save(HarvestDetailCreateDTO harvestDetailCreateDTO) {
        Harvest harvest = harvestService.findHarvestById(harvestDetailCreateDTO.getHarvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest with ID " + harvestDetailCreateDTO.getHarvestId() + " not found"));

        Tree tree = treeService.findTreeById(harvestDetailCreateDTO.getTreeId())
                .orElseThrow(() -> new EntityNotFoundException("Tree with ID " + harvestDetailCreateDTO.getTreeId() + " not found"));

        HarvestDetail harvestDetail = harvestDetailMapper.toHarvestDetail(harvestDetailCreateDTO);

        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        harvestDetail.setQuantity(harvestDetailCreateDTO.getQuantity());

        return harvestDetailRepository.save(harvestDetail);
    }

    @Override
    public HarvestDetail update(UUID id, HarvestDetailCreateDTO harvestDetailCreateDTO) {
        Optional<HarvestDetail> harvestDetailOptional = harvestDetailRepository.findById(id);

        if (harvestDetailOptional.isEmpty()) {
            throw new EntityNotFoundException("Harvest Detail with ID " + id + " not found");
        }

        Harvest harvest = harvestService.findHarvestById(harvestDetailCreateDTO.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        Tree tree = treeService.findTreeById(harvestDetailCreateDTO.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));

        HarvestDetail harvestDetail = harvestDetailOptional.get();
        harvestDetail.setId(id);
        harvestDetail.setHarvest(harvest);
        harvestDetail.setTree(tree);
        harvestDetail.setQuantity(harvestDetailCreateDTO.getQuantity());

        return harvestDetailRepository.save(harvestDetail);
    }


    @Override
    public Optional<HarvestDetail> findHarvestDetailById(UUID id) {
        Optional<HarvestDetail> harvestDetail = harvestDetailRepository.findById(id);
        if (harvestDetail.isEmpty()) {
            throw new EntityNotFoundException("Harvest Detail with ID " + id + " not found");
        }
        return harvestDetail;

    }


    @Override
    public void delete(UUID id) {
        Optional<HarvestDetail> harvestDetail = findHarvestDetailById(id);
        if (harvestDetail.isEmpty()) {
            throw new EntityNotFoundException("Harvest Detail with ID " + id + " not found");
        }
        harvestDetailRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<HarvestDetail> saveAll(List<HarvestDetail> harvestDetails) {
        if (harvestDetails == null || harvestDetails.isEmpty()) {
            throw new IllegalArgumentException("La liste des détails de récolte est vide ou null.");
        }
        return harvestDetailRepository.saveAll(harvestDetails);
    }

    @Override
    public boolean existsByFieldAndSeason(UUID fieldId, Season season) {
        return harvestDetailRepository.existsByFieldAndSeason(fieldId, season);
    }

    @Override
    public boolean existsByTreeAndHarvest_Season( Tree tree, Season season) {
        return harvestDetailRepository.existsByTreeAndHarvest_Season(tree, season);
    }

    @Override
    public boolean isTreeInHarvest(Tree tree, Harvest harvest) {
        return harvestDetailRepository.isTreeInHarvest(tree, harvest);
    }

    @Override
    public boolean deleteAllByHarvest(Harvest harvest) {
        return harvestDetailRepository.deleteAllByHarvest(harvest);
    }

}
