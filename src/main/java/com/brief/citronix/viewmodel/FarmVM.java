package com.brief.citronix.viewmodel;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FarmVM {

    private String name;
    private String location;
    private double area;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime creationDate;

}
