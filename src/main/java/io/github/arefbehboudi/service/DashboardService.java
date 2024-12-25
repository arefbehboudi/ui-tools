package io.github.arefbehboudi.service;

import com.sun.management.OperatingSystemMXBean;
import io.github.arefbehboudi.model.DashboardResponse;
import org.springframework.stereotype.Service;
import java.lang.management.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DashboardService {

    public DashboardResponse getData() {
        DashboardResponse dashboardResponse = new DashboardResponse();

        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        int threadCount = allStackTraces.size();
        dashboardResponse.setThreadCount(threadCount);

        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
        long uptime = rb.getUptime();

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class);

        dashboardResponse.setCpuUsage(Double.parseDouble((String.format("%.2f", osBean.getProcessCpuLoad() * 100))));

        dashboardResponse.setUptime(formatUptime(uptime));

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        double heapUsagePercentage = calculateUsagePercentage(heapUsage.getUsed(), heapUsage.getMax());

        MemoryUsage nonHeapUsage = memoryBean.getNonHeapMemoryUsage();
        double nonHeapUsagePercentage = calculateUsagePercentage(nonHeapUsage.getUsed(), nonHeapUsage.getMax());

        dashboardResponse.setHeapUsedPercentage(heapUsagePercentage);

        dashboardResponse.setNonHeapUsedPercentage(nonHeapUsagePercentage);

        List<MemoryPoolMXBean> memoryPoolBeans = ManagementFactory.getMemoryPoolMXBeans();

        for (MemoryPoolMXBean pool : memoryPoolBeans) {

            if ("G1 Survivor Space".equals(pool.getName())) {
                MemoryUsage usage = pool.getUsage();

                dashboardResponse.setG1SurvivorSpace(bytesToMB(usage.getUsed()));
            }

            if ("G1 Old Gen".equals(pool.getName())) {
                MemoryUsage usage = pool.getUsage();

                dashboardResponse.setG1OldGen(bytesToMB(usage.getUsed()));
            }

            if ("G1 Eden Space".equals(pool.getName())) {
                MemoryUsage usage = pool.getUsage();

                dashboardResponse.setG1EdenSpace(bytesToMB(usage.getUsed()));
            }
        }
        return dashboardResponse;
    }

    public int bytesToMB(long bytes) {
        final double BYTES_IN_MB = 1024 * 1024;
        return (int) (bytes / BYTES_IN_MB);
    }

    private static double calculateUsagePercentage(long used, long max) {
        double percentage = (max > 0) ? ((double) used / max) * 100 : 0.0;
        return Double.parseDouble(String.format("%.2f", percentage));
    }

    public static String formatUptime(long uptimeMillis) {
        long seconds = TimeUnit.MILLISECONDS.toSeconds(uptimeMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(uptimeMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(uptimeMillis);
        long days = TimeUnit.MILLISECONDS.toDays(uptimeMillis);

        long months = days / 30; // Approximation for months
        days %= 30;

        long weeks = days / 7;
        days %= 7;

        StringBuilder result = new StringBuilder();

        if (months > 0) {
            result.append(months).append(" month").append(months > 1 ? "s" : "").append(" ");
        }
        if (weeks > 0) {
            result.append(weeks).append(" week").append(weeks > 1 ? "s" : "").append(" ");
        }
        if (days > 0) {
            result.append(days).append(" day").append(days > 1 ? "s" : "").append(" ");
        }
        if (hours % 24 > 0) {
            result.append(hours % 24).append(" hour").append((hours % 24) > 1 ? "s" : "").append(" ");
        }
        if (minutes % 60 > 0) {
            result.append(minutes % 60).append(" minute").append((minutes % 60) > 1 ? "s" : "").append(" ");
        }
        if (seconds % 60 > 0 && result.isEmpty()) { // Include seconds only if no larger unit is added
            result.append(seconds % 60).append(" second").append((seconds % 60) > 1 ? "s" : "").append(" ");
        }

        return result.toString().trim();
    }
}
