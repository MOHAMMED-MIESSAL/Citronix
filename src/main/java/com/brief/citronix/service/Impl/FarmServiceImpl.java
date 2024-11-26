package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.dto.FarmCreateDTO;
import com.brief.citronix.mapper.FarmMapper;
import com.brief.citronix.repository.FarmRepository;
import com.brief.citronix.service.FarmService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {


    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Farm> findAll(Pageable pageable) {
        return farmRepository.findAll(pageable);
    }

    @Override
    public Farm save(FarmCreateDTO farmCreateDTO) {
        Farm farm = farmMapper.toFarm(farmCreateDTO);
        return farmRepository.save(farm);
    }

    @Override
    public Farm update(UUID id, FarmCreateDTO farmCreateDTO) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm with ID " + id + " not found"));

        farm.setId(id);
        farm.setName(farmCreateDTO.getName());
        farm.setLocation(farmCreateDTO.getLocation());
        farm.setArea(farmCreateDTO.getArea());
        farm.setCreationDate(farmCreateDTO.getCreationDate());
        return farmRepository.save(farm);
    }

    @Override
    public Optional<Farm> findFarmById(UUID id) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        if (farmOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + id + " not found");
        }
        return farmOptional;
    }

    @Override
    public void delete(UUID id) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        if (farmOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + id + " not found");
        }
        farmRepository.deleteById(id);
    }

    @Override
    public Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = criteriaBuilder.createQuery(Farm.class);
        Root<Farm> root = criteriaQuery.from(Farm.class);

        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (location != null) {
            predicates.add(criteriaBuilder.like(root.get("location"), "%" + location + "%"));
        }
        if (minArea != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("area"), minArea));
        }
        if (maxArea != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("area"), maxArea));
        }
        if (startDate != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), startDate));
        }
        if (endDate != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), endDate));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        // Add sorting
        if (pageable.getSort().isSorted()) {
            pageable.getSort().forEach(order -> {
                if (order.isAscending()) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order.getProperty())));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(order.getProperty())));
                }
            });
        }

        TypedQuery<Farm> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Farm> farms = query.getResultList();
        long total = farms.size();
        return new PageImpl<>(farms, pageable, total);
    }
}
