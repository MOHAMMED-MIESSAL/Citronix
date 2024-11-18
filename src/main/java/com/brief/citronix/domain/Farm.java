package com.brief.citronix.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String location;
    private double area;
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "farm")
    private List<Field> fields;
}
