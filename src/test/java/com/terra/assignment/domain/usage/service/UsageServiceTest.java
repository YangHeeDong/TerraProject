package com.terra.assignment.domain.usage.service;

import com.terra.assignment.domain.usage.dto.UsageDay;
import com.terra.assignment.domain.usage.dto.UsageHour;
import com.terra.assignment.domain.usage.dto.UsageMin;
import com.terra.assignment.domain.usage.entity.Usage;
import com.terra.assignment.domain.usage.repository.UsageRepository;
import com.terra.assignment.global.resData.ResCode;
import com.terra.assignment.global.resData.ResData;
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
        ResData<List<UsageMin>> result = usageService.findUsagesPerMin(year, month, day, startHour, endHour);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.S_05);
        assertThat(result.getData()).isNotEmpty();
        verify(usageRepository, times(1)).findUsagesPerMin(year, month, day, startHour, endHour);
    }

    @Test
    @DisplayName("지정한 시간 구간의 분 단위 CPU 사용률 조회 데이터가 없을 때")
    void testFindUsagesPerMin_Empty() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        int startHour = 1;
        int endHour = 5;
        List<Usage> expectedUsages = Arrays.asList();

        // Mock인 usageRepository의 동작 정의
        when(usageRepository.findUsagesPerMin(year, month, day, startHour, endHour)).thenReturn(expectedUsages);

        // when
        ResData<List<UsageMin>> result = usageService.findUsagesPerMin(year, month, day, startHour, endHour);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.F_04);
        assertThat(result.getData()).isEqualTo(null);
        verify(usageRepository, times(1)).findUsagesPerMin(year, month, day, startHour, endHour);
    }

    @Test
    @DisplayName("지정한 날짜의 시 단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerHour() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        List<UsageHour> expectedStatistics = Arrays.asList(new UsageHour());
        when(usageRepository.findUsagesPerHour(year, month, day)).thenReturn(expectedStatistics);

        // when
        ResData<List<UsageHour>> result = usageService.findUsagesPerHour(year, month, day);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.S_05);
        assertThat(result.getData()).isEqualTo(expectedStatistics);
        verify(usageRepository, times(1)).findUsagesPerHour(year, month, day);
    }

    @Test
    @DisplayName("지정한 날짜의 시 단위 CPU 최소/최대/평균 사용률 조회 데이터가 없을 때")
    void testFindUsagesPerHour_Empty() {
        // given
        int year = 2024;
        int month = 5;
        int day = 1;
        List<UsageHour> expectedStatistics = Arrays.asList();
        when(usageRepository.findUsagesPerHour(year, month, day)).thenReturn(expectedStatistics);

        // when
        ResData<List<UsageHour>> result = usageService.findUsagesPerHour(year, month, day);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.F_04);
        assertThat(result.getData()).isEqualTo(null);
        verify(usageRepository, times(1)).findUsagesPerHour(year, month, day);
    }

    @Test
    @DisplayName("지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률 조회")
    void testFindUsagesPerDay() {
        // given
        int year = 2024;
        int month = 5;
        int startDay = 1;
        int endDay = 5;
        List<UsageDay> expectedStatistics = Arrays.asList(new UsageDay());
        when(usageRepository.findUsagesPerDay(year, month, startDay, endDay)).thenReturn(expectedStatistics);

        // when
        ResData<List<UsageDay>> result = usageService.findUsagesPerDay(year, month, startDay, endDay);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.S_05);
        assertThat(result.getData()).isEqualTo(expectedStatistics);
        verify(usageRepository, times(1)).findUsagesPerDay(year, month, startDay, endDay);
    }

    @Test
    @DisplayName("지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률 조회 데이터가 없을 때")
    void testFindUsagesPerDay_Empty() {
        // given
        int year = 2024;
        int month = 5;
        int startDay = 1;
        int endDay = 5;

        List<UsageDay> expectedStatistics = Arrays.asList();
        when(usageRepository.findUsagesPerDay(year, month, startDay, endDay)).thenReturn(expectedStatistics);

        // when
        ResData<List<UsageDay>> result = usageService.findUsagesPerDay(year, month, startDay, endDay);

        // then
        assertThat(result.getResCode()).isEqualTo(ResCode.F_04);
        assertThat(result.getData()).isEqualTo(null);
        verify(usageRepository, times(1)).findUsagesPerDay(year, month, startDay, endDay);
    }

}