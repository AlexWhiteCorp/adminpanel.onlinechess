package com.alexcorp.oc.adminpanel.statistics;

import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;

@Component
public class SystemUsageInfo {

    private DecimalFormat format = new DecimalFormat("##,#");
    private OperatingSystemMXBean OSBean;
    private Method getSystemCPUusage;
    private Method getSystemRAMfree;
    private Method getSystemTotalRAM;
    private double RAM_TOTAL;

    public SystemUsageInfo() {
        OSBean = ManagementFactory.getOperatingSystemMXBean();
        for (Method method : OSBean.getClass().getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.getName().startsWith("getSystemCpuLoad") && Modifier.isPublic(method.getModifiers())) {
                getSystemCPUusage = method;
                continue;
            }
            if (method.getName().startsWith("getTotalPhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
                getSystemTotalRAM = method;
                continue;
            }
            if (method.getName().startsWith("getFreePhysicalMemorySize") && Modifier.isPublic(method.getModifiers())) {
                getSystemRAMfree = method;
            }
        }

        RAM_TOTAL = getRAMtotal();
    }

    public double getCPUusage(){
        try {
            return normalisePerCents((double)getSystemCPUusage.invoke(OSBean));
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public double getRAMusage(){
        double perCents = (RAM_TOTAL - getRAMfree()) / RAM_TOTAL;
        return normalisePerCents(perCents);
    }

    private double getRAMfree(){
        try {
            return (long)getSystemRAMfree.invoke(OSBean);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private double getRAMtotal(){
        try {
            return (long)getSystemTotalRAM.invoke(OSBean);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static double normalisePerCents(double perCents){
        return DoubleRounder.round(perCents * 100, 1);
    }
}
