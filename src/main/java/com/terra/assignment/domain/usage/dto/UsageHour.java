package com.terra.assignment.domain.usage.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "getUsagesPerHour의 응답 데이터")
public class UsageHour {

    @Schema(description = "년", example = "2024")
    private Integer yearColumn;

    @Schema(description = "월", example = "5")
    private Integer monthColumn;

    @Schema(description = "일", example = "24")
    private Integer dayColumn;

    @Schema(description = "시", example = "13")
    private Integer hourColumn;

    @Schema(description = "시간당 최저 cpu 사용률", example = "0.00")
    private Double minCpuUsage;

    @Schema(description = "시간당 최대 cpu 사용률", example = "4.23")
    private Double maxCpuUsage;

    @Schema(description = "시간당 평균 cpu 사용률", example = "2.11")
    private Double avgCpuUsage;

}
