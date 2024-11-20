package com.brief.citronix.viewmodel;

import com.brief.citronix.dto.FieldDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TreeVM {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime datePlantation;
    private FieldDTO fieldDTO;
}
