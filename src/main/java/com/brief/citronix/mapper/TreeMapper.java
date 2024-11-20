package com.brief.citronix.mapper;


import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.dto.TreeDTO;
import com.brief.citronix.viewmodel.TreeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public interface TreeMapper  {
    /**
     * Map TreeCreateDTO to Tree
     *
     */
    @Mapping(source = "fieldId", target = "field.id")
    @Mapping(target = "id", ignore = true) // Ignore si l'id est auto-généré
    Tree toTree(TreeCreateDTO treeCreateDTO);

    /**
     * Map Tree to TreeDTO
     *
     */
    @Mapping(source="field", target="fieldDTO")
    TreeDTO toTreeDTO(Tree tree);

    /**
     * Map TreeDTO to TreeVM
     *
     */
   TreeVM toTreeVM(TreeDTO treeDTO);
}
