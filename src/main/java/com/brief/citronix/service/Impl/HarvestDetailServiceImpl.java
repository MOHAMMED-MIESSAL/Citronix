package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.dto.HarvestDetailCreateDTO;
import com.brief.citronix.enums.Season;
import com.brief.citronix.mapper.HarvestDetailMapper;
import com.brief.citronix.repository.HarvestDetailRepository;
import com.brief.citronix.service.HarvestDetailService;
import jakarta.persistence.EntityNotFoundException;
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
    private final HarvestDetailMapper harvestDetailMapper;


    @Override
    public Page<HarvestDetail> findAll(Pageable pageable) {
        return harvestDetailRepository.findAll(pageable);
    }


    @Override
    public HarvestDetail save(HarvestDetailCreateDTO harvestDetailCreateDTO) {
        return harvestDetailRepository.save(harvestDetailMapper.toHarvestDetail(harvestDetailCreateDTO));
    }

    @Override
    public HarvestDetail update(UUID id, HarvestDetailCreateDTO harvestDetailCreateDTO) {
        Optional<HarvestDetail> harvestDetail = findHarvestDetailById(id);
        if (harvestDetail.isEmpty()) {
            throw new EntityNotFoundException("Harvest Detail with ID " + id + " not found");
        }
        return harvestDetailRepository.save(harvestDetailMapper.toHarvestDetail(harvestDetailCreateDTO));
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
    public boolean existsByTreeAndSeason(UUID treeId, Season season) {
        return harvestDetailRepository.existsByTreeAndSeason(treeId, season);
    }

    @Override
    public List<HarvestDetail> saveAllHarvestDetails(List<HarvestDetail> harvestDetails) {
        return harvestDetailRepository.saveAll(harvestDetails);
    }


    @Override
    public void deleteAllByHarvestId(UUID harvestId) {
        harvestDetailRepository.deleteAllByHarvestId(harvestId);
    }
}
