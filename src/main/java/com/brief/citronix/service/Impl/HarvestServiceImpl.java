package com.brief.citronix.service.Impl;

import com.brief.citronix.domain.Harvest;
import com.brief.citronix.domain.HarvestDetail;
import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.HarvestCreateDTO;
import com.brief.citronix.enums.Season;
import com.brief.citronix.mapper.HarvestMapper;
import com.brief.citronix.repository.HarvestRepository;
import com.brief.citronix.service.HarvestDetailService;
import com.brief.citronix.service.HarvestService;
import com.brief.citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
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
    private final TreeService treeService;
    private final HarvestDetailService harvestDetailService;
    private final HarvestMapper harvestMapper;


    @Override
    public Page<Harvest> findAll(Pageable pageable) {
        return harvestRepository.findAll(pageable);
    }


    @Override
    public Harvest save(UUID fieldId, HarvestCreateDTO harvestCreateDTO) {
        // Vérifier si le champ existe
        List<Tree> trees = treeService.findAllByField(fieldId);
        if (trees.isEmpty()) {
            throw new EntityNotFoundException("Field with Id : " + fieldId + " not found");
        }


        // Récupérer la saison à partir du DTO
        Season season = harvestCreateDTO.getSeason();

        // Vérifier si une récolte existe déjà pour ce champ dans la saison donnée
        boolean fieldAlreadyHarvested = harvestDetailService.existsByFieldAndSeason(fieldId, season);
        if (fieldAlreadyHarvested) {
            throw new IllegalArgumentException("Field with Id : " + fieldId + " has already been harvested in season: " + season);
        }

        // Vérifier si un arbre du champ est déjà récolté dans cette saison
        boolean anyTreeAlreadyHarvested = trees.stream()
                .anyMatch(tree -> harvestDetailService.existsByTreeAndHarvest_Season(tree, season));
        if (anyTreeAlreadyHarvested) {
            throw new IllegalArgumentException("Some trees in the field have already been harvested in season: " + season);
        }


        // Calculer la quantité totale en utilisant la productivité des arbres
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();

        // Mettre à jour la quantité totale dans la récolte
        harvestCreateDTO.setTotalQuantity(totalQuantity);

        // Enregistrer la récolte
        Harvest savedHarvest = harvestRepository.save(harvestMapper.toHarvest(harvestCreateDTO));

        // Créer et enregistrer les détails de la récolte
        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> {
                    HarvestDetail harvestDetail = new HarvestDetail();
                    harvestDetail.setHarvest(savedHarvest);
                    harvestDetail.setTree(tree);
                    harvestDetail.setQuantity(tree.getAnnualProductivity()); // Utilise la méthode de productivité
                    return harvestDetail;
                })
                .toList();

        harvestDetailService.saveAll(harvestDetails);

        return savedHarvest;


    }


    @Override
    public Harvest update(UUID harvestId, UUID fieldId, HarvestCreateDTO harvestUpdateDTO) {
        // Vérifier si la récolte existe
        Harvest existingHarvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new EntityNotFoundException("Harvest with Id : " + harvestId + " not found"));

        // Vérifier si le champ existe
        List<Tree> trees = treeService.findAllByField(fieldId);
        if (trees.isEmpty()) {
            throw new EntityNotFoundException("Field with Id : " + fieldId + " not found");
        }

        // Récupérer la saison à partir du DTO
        Season season = harvestUpdateDTO.getSeason();

        // Vérifier si une autre récolte existe déjà pour ce champ dans la saison donnée
        boolean fieldAlreadyHarvested = harvestDetailService.existsByFieldAndSeason(fieldId, season);
        if (fieldAlreadyHarvested && !existingHarvest.getSeason().equals(season)) {
            throw new IllegalArgumentException("Field with Id : " + fieldId + " has already been harvested in season: " + season);
        }

        // Vérifier si un arbre du champ est déjà récolté dans cette saison pour une autre récolte
        boolean anyTreeAlreadyHarvested = trees.stream()
                .anyMatch(tree -> harvestDetailService.existsByTreeAndHarvest_Season(tree, season)
                        && !harvestDetailService.isTreeInHarvest(tree, existingHarvest));
        if (anyTreeAlreadyHarvested) {
            throw new IllegalArgumentException("Some trees in the field have already been harvested in season: " + season);
        }

        // Calculer la nouvelle quantité totale en utilisant la productivité des arbres
        double totalQuantity = trees.stream()
                .mapToDouble(Tree::getAnnualProductivity)
                .sum();

        // Mettre à jour les informations de la récolte
        existingHarvest.setId(harvestId);
        existingHarvest.setSeason(season);
        existingHarvest.setTotalQuantity(totalQuantity);
        existingHarvest.setHarvestDate(harvestUpdateDTO.getHarvestDate());

        // Enregistrer les modifications de la récolte
        Harvest updatedHarvest = harvestRepository.save(existingHarvest);

        // Supprimer les anciens détails de récolte
        harvestDetailService.deleteAllByHarvest(existingHarvest);

        // Créer et enregistrer les nouveaux détails de la récolte
        List<HarvestDetail> harvestDetails = trees.stream()
                .map(tree -> {
                    HarvestDetail harvestDetail = new HarvestDetail();
                    harvestDetail.setHarvest(updatedHarvest);
                    harvestDetail.setTree(tree);
                    harvestDetail.setQuantity(tree.getAnnualProductivity()); // Utilise la méthode de productivité
                    return harvestDetail;
                })
                .toList();

        harvestDetailService.saveAll(harvestDetails);

        return updatedHarvest;
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
