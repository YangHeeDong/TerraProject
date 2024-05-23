package com.terra.assignment.domain.usage.service;

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

@Service
@RequiredArgsConstructor
public class UsageService {

    private final UsageRepository usageRepository;
    private final OsBean osBean;
    private static final Logger logger = LoggerFactory.getLogger(Example.class);


    // 1초 마다 CPU 데이터 저장
    // 에러시 파일에 로깅
    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void saveCpuUsage(){

        try {
            Usage usage = Usage.builder()
                    .cpuUsage(osBean.getCPUProcess())
                    .build();

            usageRepository.save(usage);
            
        }catch (Exception e){
            logger.error("An error occurred: ", e);
        }


    }



}
