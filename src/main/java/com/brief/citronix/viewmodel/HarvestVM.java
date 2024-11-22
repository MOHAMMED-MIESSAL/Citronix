package com.brief.citronix.viewmodel;

import com.brief.citronix.enums.Season;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HarvestVM {

    private LocalDateTime harvestDate;
    private double totalQuantity;
    @Enumerated(EnumType.STRING)
    private Season season;
}
