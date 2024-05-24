package com.terra.assignment.domain.usage.repository;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.entity.Usage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class UsageRepositoryTest {

    @Autowired
    private UsageRepository usageRepository;

    // 테스트 데이터 생성
    @BeforeEach
    void setUp() {
        for(int month = 1; month<=5; month++){
            for(int day= 1; day<=5; day++){
                for(int hour=1; hour<=5; hour++){
                    for(int min=1; min<=5; min++){
                        Usage usage = Usage.builder()
                                .cpuUsage(0.12)
                                .yearColumn(2024)
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

    @Test
    @DisplayName("저장 테스트")
    public void testSaveUsage() {

        // given
        Usage usage = Usage.builder()
                .cpuUsage(0.12)
                .build();

        // when
        Usage savedUsage = usageRepository.save(usage);

        // then
        assertThat(savedUsage).isNotNull();
        assertThat(savedUsage.getId()).isNotNull();
        assertThat(savedUsage.getCpuUsage()).isEqualTo(0.12);

    }


    @Test
    @DisplayName("지정한 시간 구간의 분 단위 CPU 사용률 조회")
    void testFindUsagesPerMin() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        int startHour = 1;
        int endHour = 5;

        // when
        List<Usage> usages = usageRepository.findUsagesPerMin(year, month, day, startHour, endHour);

        // then
        assertThat(usages).isNotEmpty();
        assertThat(usages).allMatch(usage -> usage.getYearColumn() == year && usage.getMonthColumn() == month && usage.getDayColumn() == day);
    }

    @Test
    @DisplayName("지정한 날짜의 시 단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerHourStatistic() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;

        // when
        List<UsageHour> statistics = usageRepository.findUsagesPerHourStatistic(year, month, day);

        // then
        assertThat(statistics).isNotEmpty();
        assertThat(statistics).allMatch(stat -> stat.getYearColumn() == year && stat.getMonthColumn() == month && stat.getDayColumn() == day);
    }

    @Test
    @DisplayName("지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerDayStatistic() {
        // given
        int year = 2024;
        int month = 5;
        int startDay = 1;
        int endDay = 5;

        // when
        List<UsageDay> statistics = usageRepository.findUsagesPerDayStatistic(year, month, startDay, endDay);

        // then
        assertThat(statistics).isNotEmpty();
        assertThat(statistics).allMatch(stat -> stat.getYearColumn() == year && stat.getMonthColumn() == month && stat.getDayColumn() >= startDay && stat.getDayColumn() <= endDay);
    }

}
