package com.terra.assignment.domain.usage.entity;

import com.terra.assignment.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Usage extends BaseEntity {

    private Double cpuUsage;
    private Integer yearColumn;
    private Integer monthColumn;
    private Integer dayColumn;
    private Integer hourColumn;
    private Integer minuteColumn;

}
