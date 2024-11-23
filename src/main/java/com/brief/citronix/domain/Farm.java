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
    private double area; // in hectares
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "farm" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields;

    @Override
    public String toString() {
        return "Farm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", area=" + area +
                ", creationDate=" + creationDate +
                '}';
    }
}
