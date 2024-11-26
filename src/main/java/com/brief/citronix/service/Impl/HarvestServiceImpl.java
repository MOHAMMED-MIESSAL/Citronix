package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Field;
import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.HarvestCreateDTO;
import com.brief.citronix.enums.Season;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.HarvestMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.service.FieldService;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestDetailService harvestDetailService;
    private final FieldService fieldService;
    private final HarvestMapper harvestMapper;
    private final TreeService treeService;


    @Override
    public Page<Harvest> findAll(Pageable pageable) {
        return harvestRepository.findAll(pageable);
    }

    @Override
    public Harvest save(UUID fieldId, HarvestCreateDTO harvestCreateDTO) {
        Optional<Field> field = fieldService.findFieldById(fieldId);
        if (field.isEmpty()) {
            throw new EntityNotFoundException("Field with Id : " + fieldId + " not found");
        }

        // Check if the field contains trees
        List<Tree> trees = treeService.findAllByField(fieldId);
        if (trees.isEmpty()) {
            throw new CustomValidationException("This field has no trees");
        }

        // Récupérer la saison à partir du DTO
        Season season = harvestCreateDTO.getSeason();


        //  Vérifier si une récolte existe déjà pour ce champ dans la saison donnée
        boolean fieldAlreadyHarvested = harvestDetailService.existsByTreeAndSeason(fieldId, season);

        if (fieldAlreadyHarvested) {
            throw new EntityNotFoundException("Field with Id : " + fieldId + " already harvested in season : " + season);
        }

        // Vérifier si un arbre du champ est déjà récolté dans cette saison
        boolean treeAlreadyHarvested = trees.stream()
                .anyMatch(tree -> harvestDetailService.existsByTreeAndSeason(tree.getId(), season));

        if (treeAlreadyHarvested) {
            throw new EntityNotFoundException("One or more trees in the field are already harvested in season : " + season);
        }

        // Calculer la quantité totale en utilisant la productivité des arbres
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();

        // Mettre à jour la quantité totale dans la récolte
        harvestCreateDTO.setTotalQuantity(totalQuantity);

        // Enregistrer la récolte
        Harvest savedHarvest = harvestRepository.save(harvestMapper.toHarvest(harvestCreateDTO));

        // Enregistrer les détails de la récolte
        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> {
                    HarvestDetail harvestDetail = new HarvestDetail();
                    harvestDetail.setHarvest(savedHarvest);
                    harvestDetail.setTree(tree);
                    harvestDetail.setQuantity(tree.getAnnualProductivity()); // Utilise la méthode de productivité
                    return harvestDetail;
                })
                .toList();

        harvestDetailService.saveAllHarvestDetails(harvestDetails);

        return savedHarvest;
    }

    @Transactional
    @Override
    public Harvest update(UUID fieldId, UUID harvestId, HarvestCreateDTO harvestCreateDTO) {
        Optional<Field> field = fieldService.findFieldById(fieldId);
        if (field.isEmpty()) {
            throw new EntityNotFoundException("Field with Id : " + fieldId + " not found");
        }

        // Check if the field contains trees
        List<Tree> trees = treeService.findAllByField(fieldId);
        if (trees.isEmpty()) {
            throw new CustomValidationException("This field has no trees");
        }

        // Vérifier si la récolte existe
        Harvest existingHarvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new EntityNotFoundException("Harvest with Id : " + harvestId + " not found"));


        // Utiliser la saison existante si aucune nouvelle saison n'est spécifiée
        Season season = harvestCreateDTO.getSeason() != null ? harvestCreateDTO.getSeason() : existingHarvest.getSeason();

        // Vérifier si le champ ou ses arbres ont déjà été récoltés dans cette saison (uniquement si la saison change)
        if (!season.equals(existingHarvest.getSeason())) {
            boolean fieldAlreadyHarvested = harvestDetailService.existsByTreeAndSeason(fieldId, season);
            if (fieldAlreadyHarvested) {
                throw new EntityNotFoundException("Field with Id : " + fieldId + " already harvested in season : " + season);
            }

            boolean treeAlreadyHarvested = trees.stream()
                    .anyMatch(tree -> harvestDetailService.existsByTreeAndSeason(tree.getId(), season));
            if (treeAlreadyHarvested) {
                throw new EntityNotFoundException("One or more trees in the field are already harvested in season : " + season);
            }
        }

        // Calculer la nouvelle quantité totale basée sur la productivité des arbres
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();

        // Mettre à jour la récolte existante
        existingHarvest.setSeason(season);
        existingHarvest.setTotalQuantity(totalQuantity);

        // Supprimer les détails de récolte existants pour cette récolte
        harvestDetailService.deleteAllByHarvestId(harvestId);

        // Créer et enregistrer les nouveaux détails de récolte
        List<HarvestDetail> updatedHarvestDetails = trees.stream()
                .map(tree -> {
                    HarvestDetail harvestDetail = new HarvestDetail();
                    harvestDetail.setHarvest(existingHarvest);
                    harvestDetail.setTree(tree);
                    harvestDetail.setQuantity(tree.getAnnualProductivity());
                    return harvestDetail;
                })
                .toList();

        harvestDetailService.saveAllHarvestDetails(updatedHarvestDetails);

        // Enregistrer les modifications dans la récolte
        return harvestRepository.save(existingHarvest);
    }


    @Override
    public void delete(UUID id) {
        Optional<Harvest> harvest = harvestRepository.findById(id);
        if (harvest.isEmpty()) {
            throw new EntityNotFoundException("Harvest with Id : " + id + " not found");
        }
        harvestRepository.deleteById(id);
    }

    @Override
    public Optional<Harvest> findHarvestById(UUID id) {
        if (harvestRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Harvest with Id : " + id + " not found");
        }
        return harvestRepository.findById(id);
    }

}
