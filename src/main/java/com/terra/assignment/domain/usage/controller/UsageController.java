package com.terra.assignment.domain.usage.controller;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.dto.UsageMin;
import com.terra.assignment.domain.usage.service.UsageService;
import com.terra.assignment.global.resData.ResCode;
import com.terra.assignment.global.resData.ResData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usages")
@Tag(name = "Usage", description = "Usage API")
public class UsageController {

    private final UsageService usageService;


    @GetMapping("/perMin")
    @Operation(summary = "Get usages per min", description = "분당 CPU 사용률 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "S-05", description = "Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"S-05\", \"keyWord\": \"Found\"}, \"msg\": \"조회 성공\", \"data\": [{\"cpuUsage\": 2.22, \"year\": 2024, \"month\": 5, \"day\": 22, \"hour\": 1, \"minute\": 12}], \"isSuccess\": true}")
                    )),
            @ApiResponse(responseCode = "F-01", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-01\", \"keyWord\": \"Bad Request\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-04", description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-04\", \"keyWord\": \"Not Found\"}, \"msg\": \"해당 날짜의 데이터가 존재하지 않습니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-06", description = "Validation Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-06\", \"keyWord\": \"Validation Error\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    ))
    })
    public ResData<List<UsageMin>> getUsagesPerMin(@RequestParam(value="year", required=false) Integer year,
                                                   @RequestParam(value="month", required=false) Integer month,
                                                   @RequestParam(value="day", required=false) Integer day,
                                                   @RequestParam(value="startHour", required=false) Integer startHour,
                                                   @RequestParam(value="endHour", required=false) Integer endHour) {

        // 데이터 검증
        if(year == null || month == null || day == null || startHour == null || endHour == null ) {
            return ResData.of(ResCode.F_01,"잘못된 요청 파라미터 입니다.");
        }
        if(startHour > endHour){
            return ResData.of(ResCode.F_06,"시작시간이 종료시간보다 클 수 없습니다.");
        }

        // 최근 1주 데이터 제공
        // 요청 날짜가 최근 1주 보다 더 전이라면
        if(LocalDate.of(year,month,day).isBefore(LocalDate.now().minusWeeks(1L))){
            return ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.");
        }

        return usageService.findUsagesPerMin(year,month,day,startHour,endHour);
    }


    @GetMapping("/perHour")
    @Operation(summary = "Get usages per hour", description = "시간당 CPU 사용률 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "S-05", description = "Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"S-05\", \"keyWord\": \"Found\"}, \"msg\": \"조회 완료\", \"data\": [{\"yearColumn\": 2024, \"monthColumn\": 5, \"dayColumn\": 1, \"hourColumn\": 1, \"minCpuUsage\": 8.41, \"maxCpuUsage\": 8.41, \"avgCpuUsage\": 8.41}], \"isSuccess\": true}")
                    )),
            @ApiResponse(responseCode = "F-01", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-01\", \"keyWord\": \"Bad Request\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-04", description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-04\", \"keyWord\": \"Not Found\"}, \"msg\": \"해당 날짜의 데이터가 존재하지 않습니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-06", description = "Validation Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-06\", \"keyWord\": \"Validation Error\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    ))
    })
    public ResData<List<UsageHour>> getUsagesPerHour(@RequestParam(value="year", required=false) Integer year,
                                   @RequestParam(value="month", required=false) Integer month,
                                   @RequestParam(value="day", required=false) Integer day) {
        // 데이터 검증
        if(year == null || month == null || day == null ) {
            return ResData.of(ResCode.F_01,"잘못된 요청 파라미터 입니다.");
        }

        // 최근 3달 데이터 제공
        // 요청 날짜가 최근 3달 보다 더 전이라면
        if(LocalDate.of(year,month,day).isBefore(LocalDate.now().minusMonths(3))){
            return ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.");
        }

        return usageService.findUsagesPerHour(year,month,day);
    }

    @GetMapping("/perDay")
    @Operation(summary = "Get usages per day", description = "일당 CPU 사용률 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "S-05", description = "Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"S-05\",\"keyWord\": \"Found\"},\"msg\": \"조회 완료\",\"data\": [{\"yearColumn\": 2024,\"monthColumn\": 1,\"dayColumn\": 1,\"minCpuUsage\": 5.25,\"maxCpuUsage\": 5.25,\"avgCpuUsage\": 5.25}],\"isSuccess\": true}")
                    )),
            @ApiResponse(responseCode = "F-01", description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-01\", \"keyWord\": \"Bad Request\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-04", description = "Not Found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-04\", \"keyWord\": \"Not Found\"}, \"msg\": \"해당 날짜의 데이터가 존재하지 않습니다.\", \"data\": null, \"isSuccess\": false}")
                    )),
            @ApiResponse(responseCode = "F-06", description = "Validation Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResData.class),
                            examples = @ExampleObject(value = "{\"resCode\": {\"code\": \"F-06\", \"keyWord\": \"Validation Error\"}, \"msg\": \"잘못된 요청 파라미터 입니다.\", \"data\": null, \"isSuccess\": false}")
                    ))
    })
    public ResData<List<UsageDay>> getUsagesPerDay(@RequestParam(value="year", required=false) Integer year,
                                  @RequestParam(value="month", required=false) Integer month,
                                  @RequestParam(value="startDay", required=false) Integer startDay,
                                  @RequestParam(value="endDay", required=false) Integer endDay) {
        // 데이터 검증
        if(year == null || month == null || startDay == null || endDay == null ) {
            return ResData.of(ResCode.F_01,"잘못된 요청 파라미터 입니다.");
        }
        if(startDay > endDay){
            return ResData.of(ResCode.F_06,"시작일이 종료일 보다 클 수 없습니다.");
        }

        // 최근 1년 데이터 제공
        // 요청 날짜가 최근 1년 보다 더 전이라면
        if(LocalDate.of(year,month,startDay).isBefore(LocalDate.now().minusYears(1))){
            return ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.");
        }

        return usageService.findUsagesPerDay(year,month,startDay,endDay);
    }

}
