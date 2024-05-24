package com.terra.assignment.domain.usage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsageDay {

    private Integer yearColumn;
    private Integer monthColumn;
    private Integer dayColumn;

    private Double minCpuUsage;
    private Double maxCpuUsage;
    private Double avgCpuUsage;

}
