package com.brief.citronix.viewmodel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestDetailVM {

    private double quantity;
    private HarvestVM harvest;
    private TreeVM tree;
}
