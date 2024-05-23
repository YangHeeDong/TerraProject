package com.terra.assignment.domain.usage.controller;

import com.sun.management.OperatingSystemMXBean;
import com.terra.assignment.domain.usage.service.UsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/usages")
public class UsageController {

    private final UsageService usageService;

    /**
     * cpu 사용량
     */
    @GetMapping("/")
    public String getCPUProcess() {

        return "";
    }

}
