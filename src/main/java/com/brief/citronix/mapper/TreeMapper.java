package com.brief.citronix.mapper;


import com.brief.citronix.domain.Tree;
import com.brief.citronix.dto.TreeCreateDTO;
import com.brief.citronix.viewmodel.TreeVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FieldMapper.class , HarvestDetailMapper.class})
public interface TreeMapper {

    /**
     * Mapping from TreeCreateDTO to Tree
     */
    Tree toTree(TreeCreateDTO treeCreateDTO);

    /**
     * Mapping from Tree to TreeDTO
     */
    @Mapping(source = "field", target = "field")
    @Mapping(expression = "java(tree.getAge())", target = "age")
    @Mapping(expression = "java(tree.getAnnualProductivity())", target = "annualProductivity")
    TreeVM toTreeVM(Tree tree);
}
