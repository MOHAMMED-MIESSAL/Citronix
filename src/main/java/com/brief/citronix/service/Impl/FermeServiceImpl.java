package com.brief.citronix.service.Impl;



import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmDto;
import com.brief.citronix.mapper.FarmMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.FarmService;
import com.brief.citronix.viewmodel.FarmVM;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class FermeServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    @PersistenceContext
    private EntityManager entityManager;

    public FermeServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }

    @Override
    public Page<FarmVM> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable).map(farmMapper::farmToFarmVM);
    }

    @Override
    public void delete(UUID id) {
        farmRepository.deleteById(id);
    }

    @Override
    public Optional<FarmVM> findFarmById(UUID id) {
        return farmRepository.findById(id).map(farmMapper::farmToFarmVM);
    }

    @Override
    public FarmVM save(FarmDto farmDto) {
        Farm farm = farmMapper.farmDtoToFarm(farmDto);
        return farmMapper.farmToFarmVM(farmRepository.save(farm));
    }

    @Override
    public FarmVM update(UUID id,FarmDto farmDto) {
        Farm farm = farmMapper.farmDtoToFarm(farmDto);
        farm.setId(id);
        return farmMapper.farmToFarmVM(farmRepository.save(farm));
    }


@Override
public Page<FarmVM> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Farm> cq = cb.createQuery(Farm.class);
    Root<Farm> farm = cq.from(Farm.class);
    Predicate predicate = cb.conjunction();
    if (name != null) {
        predicate = cb.and(predicate, cb.like(farm.get("name"), "%" + name + "%"));
    }
    if (location != null) {
        predicate = cb.and(predicate, cb.like(farm.get("location"), "%" + location + "%"));
    }
    if (minArea != null) {
        predicate = cb.and(predicate, cb.ge(farm.get("area"), minArea));
    }
    if (maxArea != null) {
        predicate = cb.and(predicate, cb.le(farm.get("area"), maxArea));
    }
    if (startDate != null) {
        predicate = cb.and(predicate, cb.greaterThanOrEqualTo(farm.get("creationDate"), startDate));
    }
    if (endDate != null) {
        predicate = cb.and(predicate, cb.lessThanOrEqualTo(farm.get("creationDate"), endDate));
    }
    cq.where(predicate);
    TypedQuery<Farm> query = entityManager.createQuery(cq);
    query.setFirstResult((int) pageable.getOffset());
    query.setMaxResults(pageable.getPageSize());
    List<FarmVM> farms = query.getResultList().stream().map(farmMapper::farmToFarmVM).collect(Collectors.toList());
    return new PageImpl<>(farms, pageable, farms.size());
}
}