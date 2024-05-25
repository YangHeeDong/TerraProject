package com.terra.assignment.global.initData;

import com.terra.assignment.domain.usage.entity.Usage;
import com.terra.assignment.domain.usage.repository.UsageRepository;
import com.terra.assignment.global.util.OsBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"dev", "test"})
public class NotProd {

    @Bean
    CommandLineRunner initData(OsBean osBean, UsageRepository usageRepository) {

        return args -> {
            for(int year = 2023; year<= 2024; year++){
                for(int month = 1; month<=5; month++){
                    for(int day= 1; day<=30; day++){
                        for(int hour=1; hour<=5; hour++){
                            for(int min=1; min<=5; min++){
                                Usage usage = Usage.builder()
                                        .cpuUsage(osBean.getCPUProcess())
                                        .yearColumn(year)
                                        .monthColumn(month)
                                        .dayColumn(day)
                                        .hourColumn(hour)
                                        .minuteColumn(min)
                                        .build();

                                usageRepository.save(usage);
                            }
                        }
                    }
                }

            }
        };
    }

}
