package com.brief.citronix.viewmodel;

import com.brief.citronix.dto.FarmDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FieldVM {

   // private UUID id;
    private double area;
    private FarmDTO farmDTO;
    /**
     * SI on veut juste l'ID du farm
     */
    //private UUID farmId;

}
