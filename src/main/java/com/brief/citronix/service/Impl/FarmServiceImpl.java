package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Tree;
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
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
  private final FieldServiceImpl fieldServiceImpl;
  private final TreeServiceImpl treeServiceImpl;
    private final FarmMapper farmMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public FarmServiceImpl(FarmRepository farmRepository, FieldServiceImpl fieldServiceImpl, TreeServiceImpl treeServiceImpl, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.fieldServiceImpl = fieldServiceImpl;
        this.treeServiceImpl = treeServiceImpl;
        this.farmMapper = farmMapper;
    }

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
    @Transactional
    public void delete(UUID id) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        if (farmOptional.isEmpty()) {
            throw new EntityNotFoundException("Farm with ID " + id + " not found");
        }

        List<Field> fields = fieldServiceImpl.findByFarmId(id);

        if (!fields.isEmpty()) {
            fields.forEach(field -> {
                List<Tree> trees = treeServiceImpl.findTreesByFieldId(field.getId());
                if (!trees.isEmpty()) {
                    trees.forEach(tree -> treeServiceImpl.delete(tree.getId()));
                }
                fieldServiceImpl.delete(field.getId());
            });
        }

        farmRepository.deleteById(id);
    }


    @Override
    public Page<Farm> searchFarms(String name, String location, Double minArea, Double maxArea, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> criteriaQuery = criteriaBuilder.createQuery(Farm.class);
        Root<Farm> farmRoot = criteriaQuery.from(Farm.class);

        // Créer une liste de predicates
        Predicate finalPredicate = criteriaBuilder.conjunction();

        // Ajouter les conditions dynamiquement
        if (name != null && !name.isEmpty()) {
            Predicate namePredicate = criteriaBuilder.like(farmRoot.get("name"), "%" + name + "%");
            finalPredicate = criteriaBuilder.and(finalPredicate, namePredicate);
        }

        if (location != null && !location.isEmpty()) {
            Predicate locationPredicate = criteriaBuilder.like(farmRoot.get("location"), "%" + location + "%");
            finalPredicate = criteriaBuilder.and(finalPredicate, locationPredicate);
        }

        if (minArea != null && minArea > 0) {
            Predicate minAreaPredicate = criteriaBuilder.greaterThanOrEqualTo(farmRoot.get("area"), minArea);
            finalPredicate = criteriaBuilder.and(finalPredicate, minAreaPredicate);
        }

        if (maxArea != null && maxArea > 0) {
            Predicate maxAreaPredicate = criteriaBuilder.lessThanOrEqualTo(farmRoot.get("area"), maxArea);
            finalPredicate = criteriaBuilder.and(finalPredicate, maxAreaPredicate);
        }

        if (startDate != null) {
            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(farmRoot.get("creationDate"), startDate);
            finalPredicate = criteriaBuilder.and(finalPredicate, startDatePredicate);
        }

        if (endDate != null) {
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(farmRoot.get("creationDate"), endDate);
            finalPredicate = criteriaBuilder.and(finalPredicate, endDatePredicate);
        }

        // Appliquer les conditions à la requête
        criteriaQuery.where(finalPredicate);

        // Appliquer la pagination
        TypedQuery<Farm> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Farm> farms = query.getResultList();

        return new PageImpl<>(farms, pageable, farms.size());


    }
}
