package com.terra.assignment.domain.usage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terra.assignment.domain.usage.service.UsageService;
import com.terra.assignment.global.resData.ResCode;
import com.terra.assignment.global.resData.ResData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UsageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UsageService usageService;

    @BeforeEach
    void setUp(){

    }

//  ------------------------------------------------------   perMin 테스트 시작 ------------------------------------------------------
    @Test
    @DisplayName("perMin 요청 성공")
    public void testGetUsagePerMin () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perMin")
                        .param("year","2024")
                        .param("month","5")
                        .param("day","24")
                        .param("startHour","1")
                        .param("endHour","5"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        usageService.findUsagesPerMin(2024,5,24,1,5)
                                ))
                );
    }

    @Test
    @DisplayName("perMin 잘못된 파라미터, 요청 파라미터가 없을 때")
    public void testGetUsagePerMin_InvalidParams1 () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                    get("/api/v1/usages/perMin")
                        .param("year","2024")
                        .param("month","5")
                        .param("day","24")
                        .param("startHour","1"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                    content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.")
                                ))
                );
    }

    @Test
    @DisplayName("perMin 시작시간이 끝시간보다 큰 경우")
    public void testGetUsagePerMin_InvalidParams2 () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perMin")
                        .param("year","2024")
                        .param("month","5")
                        .param("day","24")
                        .param("startHour","5")
                        .param("endHour","1")
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"시작시간이 종료시간보다 클 수 없습니다.")
                                ))
                );
    }

    @Test
    @DisplayName("perMin 데이터 제공 기간이 지났을 때")
    public void testGetUsagePerMin_ExpiredDate () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perMin")
                        .param("year","2024")
                        .param("month","1")
                        .param("day","24")
                        .param("startHour","1")
                        .param("endHour","5")
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.")
                                ))
                );
    }

//  ------------------------------------------------------   perMin 테스트 끝 ------------------------------------------------------

//  ------------------------------------------------------   perHour 테스트 시작 ------------------------------------------------------

    @Test
    @DisplayName("perHour 요청 성공")
    public void testGetUsagePerHour () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perHour")
                        .param("year","2024")
                        .param("month","5")
                        .param("day","24"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        usageService.findUsagesPerHour(2024,5,24)
                                ))
                );
    }

    @Test
    @DisplayName("perHour 잘못된 파라미터, 요청 파라미터가 없을 때")
    public void testGetUsagePerHour_InvalidParams () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perHour")
                        .param("year","2024")
                        .param("month","5"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.")
                                ))
                );
    }

    @Test
    @DisplayName("perHour 데이터 제공 기간이 지났을 때")
    public void testGetUsagePerHour_ExpiredDate () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perHour")
                        .param("year","2024")
                        .param("month","1")
                        .param("day","24")
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.")
                                ))
                );
    }

//  ------------------------------------------------------   perHour 테스트 끝 ------------------------------------------------------

//  ------------------------------------------------------   perDay 테스트 시작 ------------------------------------------------------

    @Test
    @DisplayName("perDay 요청 성공")
    public void testGetUsagePerDay () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perDay")
                        .param("year","2024")
                        .param("month","5")
                        .param("startDay","21")
                        .param("endDay","24"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        usageService.findUsagesPerDay(2024,5,21,24)
                                ))
                );
    }

    @Test
    @DisplayName("perDay 잘못된 파라미터, 요청 파라미터가 없을 때")
    public void testGetUsagePerDay_InvalidParams1 () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perDay")
                        .param("year","2024")
                        .param("month","5"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"잘못된 요청 파라미터 입니다.")
                                ))
                );
    }

    @Test
    @DisplayName("perDay 시작 날짜가 끝 날짜보다 큰 경우")
    public void testGetUsagePerDay_InvalidParams2 () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perDay")
                        .param("year","2024")
                        .param("month","5")
                        .param("startDay","24")
                        .param("endDay","5")
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"시작일이 종료일 보다 클 수 없습니다.")
                                ))
                );
    }

    @Test
    @DisplayName("perDay 데이터 제공 기간이 지났을 때")
    public void testGetUsagePerDay_ExpiredDate () throws Exception {

        // when
        ResultActions result = mockMvc.perform(
                get("/api/v1/usages/perDay")
                        .param("year","2022")
                        .param("month","5")
                        .param("startDay","21")
                        .param("endDay","24"));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        content().json(
                                objectMapper.writeValueAsString(
                                        ResData.of(ResCode.F_06,"데이터 제공 기간이 지났습니다.")
                                ))
                );
    }

//  ------------------------------------------------------   perDay 테스트 끝 ------------------------------------------------------
}
