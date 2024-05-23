package com.terra.assignment.global.util;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@Component
public class OsBean {

    private OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    public Double getCPUProcess() {

        return Double.parseDouble(String.format("%.2f", osBean.getSystemCpuLoad() * 100));
    }
}
