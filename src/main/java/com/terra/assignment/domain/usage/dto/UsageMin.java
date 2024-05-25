package com.terra.assignment.domain.usage.dto;

import com.terra.assignment.domain.usage.entity.Usage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "getUsagesPerMin의 응답 데이터")
public class UsageMin {

    @Schema(description = "년", example = "2024")
    private Integer yearColumn;

    @Schema(description = "월", example = "5")
    private Integer monthColumn;

    @Schema(description = "일", example = "24")
    private Integer dayColumn;

    @Schema(description = "시", example = "13")
    private Integer hourColumn;

    @Schema(description = "분", example = "1")
    private Integer minColumn;

    @Schema(description = "cpu 사용률", example = "1.24")
    private Double cpuUsage;

    public UsageMin (Usage usage) {
        this.yearColumn = usage.getYearColumn();
        this.monthColumn = usage.getMonthColumn();
        this.dayColumn = usage.getYearColumn();
        this.hourColumn = usage.getHourColumn();
        this.minColumn = usage.getMinuteColumn();
        this.cpuUsage = usage.getCpuUsage();
    }

}
