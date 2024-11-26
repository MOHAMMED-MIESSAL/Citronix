package com.brief.citronix;

import com.brief.citronix.domain.Farm;
import com.brief.citronix.domain.Field;
import com.brief.citronix.dto.FieldCreateDTO;
import com.brief.citronix.exception.CustomValidationException;
import com.brief.citronix.mapper.FieldMapper;
import com.brief.citronix.repository.FieldRepository;
import com.brief.citronix.service.FarmService;
import com.brief.citronix.service.Impl.FieldServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FieldServiceImplTest {

    private static final double FARM_AREA = 100.0;
    private static final double VALID_FIELD_AREA = 49.0;
    private static final double INVALID_FIELD_AREA = 51.0;
    private static final int MAX_FIELDS_PER_FARM = 10;

    @InjectMocks
    private FieldServiceImpl fieldService;

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FarmService farmService;

    @Mock
    private FieldMapper fieldMapper;

    private Field field;
    private Farm farm;
    private FieldCreateDTO fieldCreateDTO;
    private UUID farmId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        farm = new Farm();
        farm.setId(UUID.randomUUID());
        farm.setArea(FARM_AREA);
        farm.setFields(new ArrayList<>());

        field = new Field();
        field.setId(UUID.randomUUID());
        field.setArea(10.0);
        field.setFarm(farm);

        fieldCreateDTO = new FieldCreateDTO();
        fieldCreateDTO.setFarmId(farm.getId());
        fieldCreateDTO.setArea(10.0);

        farmId = UUID.randomUUID();
    }

    @Nested
    class SaveFieldTests {
        /**
         * Tests pour la création de champs
         * - Création réussie
         * - Validation de la surface (<50% de la ferme)
         * - Gestion des erreurs (ferme non trouvée, surface excessive)
         * - Limite du nombre de champs (max 10)
         */
        @Test
        void shouldSaveFieldSuccessfully() {
            // Mock dependencies
            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));
            when(fieldMapper.toField(any(FieldCreateDTO.class))).thenReturn(field);
            when(fieldRepository.save(any(Field.class))).thenReturn(field);

            // Execute the method
            Field savedField = fieldService.save(fieldCreateDTO);

            // Verify behavior
            assertNotNull(savedField);
            assertEquals(10.0, savedField.getArea());
            verify(fieldRepository, times(1)).save(any(Field.class));
        }

        @Test
        void shouldSaveFieldWhenAreaIsLessThanFiftyPercentOfFarmArea() {
            // Arrange
            field.setArea(VALID_FIELD_AREA);
            fieldCreateDTO.setArea(VALID_FIELD_AREA);

            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));
            when(fieldMapper.toField(any(FieldCreateDTO.class))).thenReturn(field);
            when(fieldRepository.save(any(Field.class))).thenReturn(field);

            // Act
            Field savedField = fieldService.save(fieldCreateDTO);

            // Assert
            assertNotNull(savedField);
            assertEquals(VALID_FIELD_AREA, savedField.getArea());
            verify(fieldRepository, times(1)).save(any(Field.class));
        }

        @Test
        void shouldThrowExceptionWhenFarmNotFound() {
            // Mock dependencies
            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.empty());

            // Execute and assert
            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.save(fieldCreateDTO));
            assertEquals("Farm with ID " + fieldCreateDTO.getFarmId() + " not found",
                    exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenFieldAreaExceedsFarmArea() {
            // Add an existing field to the farm
            field.setArea(90.0);
            farm.getFields().add(field);

            // Mock dependencies
            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));

            // Execute and assert
            CustomValidationException exception = assertThrows(CustomValidationException.class,
                    () -> fieldService.save(fieldCreateDTO));
            assertEquals("The total area of the fields must be strictly less than the total area of the farm.",
                    exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenFieldAreaExceedsFiftyPercentOfFarmArea() {
            // Arrange
            fieldCreateDTO.setArea(INVALID_FIELD_AREA);

            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));

            // Act & Assert
            CustomValidationException exception = assertThrows(
                    CustomValidationException.class,
                    () -> fieldService.save(fieldCreateDTO)
            );
            assertEquals(
                    "Field area cannot exceed 50% of the farm's total area.",
                    exception.getMessage()
            );

            verify(fieldRepository, never()).save(any(Field.class));
        }

        @Test
        void shouldThrowExceptionWhenFarmHasMoreThan10Fields() {
            // Arrange
            List<Field> existingFields = new ArrayList<>();
            for (int i = 0; i < MAX_FIELDS_PER_FARM; i++) {
                Field existingField = new Field();
                existingField.setId(UUID.randomUUID());
                existingFields.add(existingField);
            }
            farm.setFields(existingFields);

            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));
            when(fieldRepository.countByFarmId(any(UUID.class))).thenReturn((long) MAX_FIELDS_PER_FARM);

            // Act & Assert
            CustomValidationException exception = assertThrows(CustomValidationException.class,
                    () -> fieldService.save(fieldCreateDTO));

            assertEquals("Farm with ID " + fieldCreateDTO.getFarmId() +
                    " cannot have more than 10 fields.", exception.getMessage());
            verify(fieldRepository, times(1)).countByFarmId(any(UUID.class));
            verify(fieldRepository, never()).save(any(Field.class));
        }
    }

    @Nested
    class UpdateFieldTests {
        /**
         * Tests pour la mise à jour des champs
         * - Mise à jour réussie
         * - Validation des contraintes de surface
         * - Gestion des erreurs
         */
        private UUID fieldId;
        private Field existingField;
        private FieldCreateDTO updateDTO;

        @BeforeEach
        void setUp() {
            fieldId = UUID.randomUUID();
            existingField = new Field();
            existingField.setId(fieldId);
            existingField.setArea(30.0);

            updateDTO = new FieldCreateDTO();
            updateDTO.setFarmId(farm.getId());
        }

        @Test
        void shouldValidateFieldAreaConstraints() {
            // Arrange
            farm.setFields(new ArrayList<>(List.of(existingField)));
            updateDTO.setArea(200.0);

            when(farmService.findFarmById(farm.getId())).thenReturn(Optional.of(farm));
            when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));

            // Act & Assert
            CustomValidationException exception = assertThrows(CustomValidationException.class,
                    () -> fieldService.update(fieldId, updateDTO));

            assertEquals("The total area of the fields must be strictly less than the total area of the farm.",
                    exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenUpdatedFieldAreaExceedsFiftyPercentOfFarmArea() {
            // Arrange
            farm.setFields(new ArrayList<>(List.of(existingField)));
            updateDTO.setArea(INVALID_FIELD_AREA);

            when(farmService.findFarmById(farm.getId())).thenReturn(Optional.of(farm));
            when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(existingField));

            // Act & Assert
            CustomValidationException exception = assertThrows(
                    CustomValidationException.class,
                    () -> fieldService.update(fieldId, updateDTO)
            );
            assertEquals(
                    "Field area cannot exceed 50% of the farm's total area.",
                    exception.getMessage()
            );

            verify(fieldRepository, never()).save(any(Field.class));
        }

        @Test
        void shouldUpdateFieldSuccessfully() {
            // Simuler les objets nécessaires
            when(farmService.findFarmById(any(UUID.class))).thenReturn(Optional.of(farm));
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.of(existingField));
            when(fieldRepository.save(any(Field.class))).thenReturn(existingField); // Simuler la mise à jour

            // Exécuter la méthode de mise à jour
            Field updatedField = fieldService.update(fieldId, updateDTO);

            // Vérifier que la mise à jour a eu lieu correctement
            assertEquals(updateDTO.getArea(), updatedField.getArea());
            verify(fieldRepository, times(1)).save(any(Field.class));
        }

        @Test
        void shouldThrowExceptionWhenFieldNotFoundForUpdate() {
            // Simuler l'absence du champ dans le repository
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // Exécuter et vérifier que l'exception est lancée
            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.update(fieldId, updateDTO));
            assertEquals("Field with ID " + fieldId + " not found", exception.getMessage());
        }
    }

    @Nested
    class FindFieldTests {
        /**
         * Tests pour la recherche de champs
         * - Recherche par ID
         * - Recherche de tous les champs
         * - Recherche par ID de ferme
         */
        @Test
        void shouldFindFieldByIdSuccessfully() {
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.of(field));

            Optional<Field> foundField = fieldService.findFieldById(field.getId());

            assertTrue(foundField.isPresent());
            assertEquals(field.getId(), foundField.get().getId());
        }

        @Test
        void shouldThrowExceptionWhenFieldNotFoundById() {
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.findFieldById(field.getId()));
            assertEquals("Farm with ID " + field.getId() + " not found", exception.getMessage());
        }

        @Test
        void shouldFindAllFields() {
            List<Field> fields = List.of(field);
            Page<Field> fieldPage = new PageImpl<>(fields);
            when(fieldRepository.findAll(any(PageRequest.class))).thenReturn(fieldPage);

            Page<Field> result = fieldService.findAll(PageRequest.of(0, 10));

            assertNotNull(result);
            assertEquals(1, result.getContent().size());
            verify(fieldRepository, times(1)).findAll(any(PageRequest.class));
        }

        @Test
        void shouldReturnFieldsForGivenFarmId() {
            Field field1 = new Field();
            field1.setId(UUID.randomUUID());
            field1.setArea(100);
            field1.setId(farmId);

            Field field2 = new Field();
            field2.setId(UUID.randomUUID());
            field2.setArea(200);
            field1.setId(farmId);

            List<Field> expectedFields = Arrays.asList(field1, field2);
            when(fieldRepository.findByFarmId(farmId)).thenReturn(expectedFields);

            List<Field> actualFields = fieldService.findByFarmId(farmId);

            assertEquals(expectedFields, actualFields);
        }
    }

    @Nested
    class DeleteFieldTests {
        /**
         * Tests pour la suppression de champs
         */

        @Test
        void shouldDeleteFieldSuccessfully() {
            // Simuler la présence du champ dans le repository
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.of(field));
            doNothing().when(fieldRepository).deleteById(any(UUID.class)); // Simuler l'absence d'exception lors de la suppression

            // Exécuter la méthode de suppression
            fieldService.delete(field.getId());

            // Vérifier que la suppression a bien été appelée
            verify(fieldRepository, times(1)).deleteById(any(UUID.class));
        }

        @Test
        void shouldThrowExceptionWhenFieldNotFoundForDelete() {
            // Simuler l'absence du champ dans le repository
            when(fieldRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            // Exécuter et vérifier que l'exception est lancée
            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                    () -> fieldService.delete(field.getId()));
            assertEquals("Field with ID " + field.getId() + " not found", exception.getMessage());
        }
    }

    @Nested
    class CountFieldTests {
        /**
         * Tests pour le comptage des champs
         */
        @Test
        void shouldReturnCountOfFieldsForGivenFarmId() {
            long expectedCount = 5;
            when(fieldRepository.countByFarmId(farmId)).thenReturn(expectedCount);

            long actualCount = fieldService.countByFarmId(farmId);

            assertEquals(expectedCount, actualCount);
        }
    }
}