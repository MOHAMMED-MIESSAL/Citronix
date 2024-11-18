package com.brief.citronix.viewmodel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FarmVM {
    private String name;
    private String location;
    private double area;
    private LocalDateTime creationDate;
}
