package io.github.arefbehboudi.model;


public class DashboardResponse {

    private double cpuUsage;

    private double heapUsedPercentage;

    private double nonHeapUsedPercentage;

    private int g1EdenSpace;

    private int g1OldGen;

    private int g1SurvivorSpace;

    private String uptime;

    private int threadCount;

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public void setHeapUsedPercentage(double heapUsedPercentage) {
        this.heapUsedPercentage = heapUsedPercentage;
    }

    public void setNonHeapUsedPercentage(double nonHeapUsedPercentage) {
        this.nonHeapUsedPercentage = nonHeapUsedPercentage;
    }

    public void setG1EdenSpace(int g1EdenSpace) {
        this.g1EdenSpace = g1EdenSpace;
    }

    public void setG1OldGen(int g1OldGen) {
        this.g1OldGen = g1OldGen;
    }

    public void setG1SurvivorSpace(int g1SurvivorSpace) {
        this.g1SurvivorSpace = g1SurvivorSpace;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getHeapUsedPercentage() {
        return heapUsedPercentage;
    }

    public double getNonHeapUsedPercentage() {
        return nonHeapUsedPercentage;
    }

    public int getG1EdenSpace() {
        return g1EdenSpace;
    }

    public int getG1OldGen() {
        return g1OldGen;
    }

    public int getG1SurvivorSpace() {
        return g1SurvivorSpace;
    }

    public String getUptime() {
        return uptime;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
