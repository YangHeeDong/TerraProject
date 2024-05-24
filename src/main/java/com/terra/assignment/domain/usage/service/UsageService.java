package com.terra.assignment.domain.usage.service;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.entity.Usage;
import com.terra.assignment.domain.usage.repository.UsageRepository;
import com.terra.assignment.global.util.OsBean;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsageService {

    private final UsageRepository usageRepository;
    private final OsBean osBean;
    private static final Logger logger = LoggerFactory.getLogger(Example.class);


    // 지정한 시간 구간의 분 단위 CPU 사용률을 조회합니다.
    public List<Usage> findUsagesPerMin(Integer year, Integer month, Integer day, Integer startHour, Integer endHour){
        List<Usage> usages = usageRepository.findUsagesPerMin(year,month,day,startHour,endHour);
        return usages;
    }

    // 지정한 날짜의 시 단위 CPU 최소/최대/평균 사용률을 조회합니다.
    public List<UsageHour> findUsagesPerHour(Integer year, Integer month, Integer day ){
        List<UsageHour> usages = usageRepository.findUsagesPerHourStatistic(year,month,day);
        return usages;
    }

    // 지정한 날짜 구간의 일 단위 CPU 최소/최대/평균 사용률을 조회합니다.
    public List<UsageDay> findUsagesPerDay(Integer year, Integer month, Integer startDay, Integer endDay ){
        List<UsageDay> usages = usageRepository.findUsagesPerDayStatistic(year,month,startDay,endDay);
        return usages;
    }


    // 1분 마다 CPU 데이터 저장
    // 에러시 파일에 로깅
    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void saveCpuUsage(){

        try {
            LocalDateTime localDateTime = LocalDateTime.now();

            Usage usage = Usage.builder()
                    .cpuUsage(osBean.getCPUProcess())
                    .yearColumn(localDateTime.getYear())
                    .monthColumn(localDateTime.getMonthValue())
                    .dayColumn(localDateTime.getDayOfMonth())
                    .hourColumn(localDateTime.getHour())
                    .minuteColumn(localDateTime.getMinute())
                    .build();

            usageRepository.save(usage);

        }catch (Exception e){
            logger.error("An error occurred: ", e);
        }
    }

}
