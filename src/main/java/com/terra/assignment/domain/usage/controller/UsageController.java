package com.terra.assignment.domain.usage.controller;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.entity.Usage;
import com.terra.assignment.domain.usage.service.UsageService;
import com.terra.assignment.global.resData.ResCode;
import com.terra.assignment.global.resData.ResData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usages")
public class UsageController {

    private final UsageService usageService;


    @GetMapping("/perMin")
    public ResData getUsagesPerMin(@RequestParam(value="year", required=false) Integer year,
                                   @RequestParam(value="month", required=false) Integer month,
                                   @RequestParam(value="day", required=false) Integer day,
                                   @RequestParam(value="startHour", required=false) Integer startHour,
                                   @RequestParam(value="endHour", required=false) Integer endHour) {

        // 데이터 검증
        if(year == null || month == null || day == null || startHour == null || endHour == null ) {
            return ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.");
        }
        if(startHour > endHour){
            return ResData.of(ResCode.F_06,"시작시간이 종료시간보다 클 수 없습니다.");
        }
        // 데이터 제공 기간 확인
        if(LocalDateTime.now().minusWeeks(1L).isBefore(LocalDateTime.of(year,month,day,startHour,0))){
            return ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.");
        }

        // 최근 1주 데이터 제공

        List<Usage> usages = usageService.findUsagesPerMin(year,month,day,startHour,endHour);

        return ResData.of(ResCode.S_05,"조회 성공", usages);
    }


    @GetMapping("/perHour")
    public ResData<List<UsageHour>> getUsagesPerHour(@RequestParam(value="year", required=false) Integer year,
                                   @RequestParam(value="month", required=false) Integer month,
                                   @RequestParam(value="day", required=false) Integer day) {
        // 데이터 검증
        if(year == null || month == null || day == null ) {
            ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.");
        }
        // 최근 3달 데이터 제공

        List<UsageHour> usages = usageService.findUsagesPerHour(year,month,day);

        return ResData.of(ResCode.S_05,"조회 성공", usages);
    }

    @GetMapping("/perDay")
    public ResData<List<UsageDay>> getUsagesPerDay(@RequestParam(value="year", required=false) Integer year,
                                  @RequestParam(value="month", required=false) Integer month,
                                  @RequestParam(value="startDay", required=false) Integer startDay,
                                  @RequestParam(value="endDay", required=false) Integer endDay) {
        // 데이터 검증
        if(year == null || month == null || startDay == null || endDay == null ) {
            return ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.");
        }
        if(startDay > endDay){
            return ResData.of(ResCode.F_06,"시작일이 종료일 보다 클 수 없습니다.");
        }

        List<UsageDay> usages = usageService.findUsagesPerDay(year,month,startDay,endDay);

        return ResData.of(ResCode.S_05,"조회 성공", usages);
    }

}
