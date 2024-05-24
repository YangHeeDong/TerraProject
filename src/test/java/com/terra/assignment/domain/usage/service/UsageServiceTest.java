package com.terra.assignment.domain.usage.service;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.entity.Usage;
import com.terra.assignment.domain.usage.repository.UsageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsageServiceTest {

    @Mock
    private UsageRepository usageRepository;

    @InjectMocks
    private UsageService usageService;

    @Test
    @DisplayName("지정한 시간 구간의 분 단위 CPU 사용률 조회")
    void testFindUsagesPerMin() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        int startHour = 1;
        int endHour = 5;
        List<Usage> expectedUsages = Arrays.asList(new Usage());

        // Mock인 usageRepository의 동작 정의
        when(usageRepository.findUsagesPerMin(year, month, day, startHour, endHour)).thenReturn(expectedUsages);

        // when
        List<Usage> usages = usageService.findUsagesPerMin(year, month, day, startHour, endHour);

        // then
        assertThat(usages).isEqualTo(expectedUsages);
        verify(usageRepository, times(1)).findUsagesPerMin(year, month, day, startHour, endHour);
    }

    @Test
    @DisplayName("지정한 날짜의 시 단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerHourStatistic() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        List<UsageHour> expectedStatistics = Arrays.asList(new UsageHour());
        when(usageRepository.findUsagesPerHourStatistic(year, month, day)).thenReturn(expectedStatistics);

        // when
        List<UsageHour> statistics = usageService.findUsagesPerHour(year, month, day);

        // then
        assertThat(statistics).isEqualTo(expectedStatistics);
        verify(usageRepository, times(1)).findUsagesPerHourStatistic(year, month, day);
    }

    @Test
    @DisplayName("지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerDayStatistic() {
        // given
        int year = 2024;
        int month = 5;
        int startDay = 1;
        int endDay = 5;
        List<UsageDay> expectedStatistics = Arrays.asList(new UsageDay());
        when(usageRepository.findUsagesPerDayStatistic(year, month, startDay, endDay)).thenReturn(expectedStatistics);

        // when
        List<UsageDay> statistics = usageService.findUsagesPerDay(year, month, startDay, endDay);

        // then
        assertThat(statistics).isEqualTo(expectedStatistics);
        verify(usageRepository, times(1)).findUsagesPerDayStatistic(year, month, startDay, endDay);
    }

}