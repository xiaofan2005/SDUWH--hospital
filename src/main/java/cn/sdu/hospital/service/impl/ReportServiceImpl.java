package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.AppointmentMapper;
import cn.sdu.hospital.mapper.DoctorMapper;
import cn.sdu.hospital.mapper.DepartmentMapper;
import cn.sdu.hospital.service.ReportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 报表业务层实现类
 * @author Administrator
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public Map<String, Object> generateAppointmentStatisticsReport(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        Map<String, Object> report = new HashMap<>();
        
        String startDateStr = startDate.toString();
        String endDateStr = endDate.toString();
        
        // 基础统计数据
        int totalAppointments;
        if (departmentId != null) {
            totalAppointments = appointmentMapper.getDepartmentAppointmentCount(departmentId, startDateStr, endDateStr);
        } else {
            totalAppointments = appointmentMapper.getSystemAppointmentCount(startDateStr, endDateStr);
        }
        
        // 状态分布统计
        Map<String, Integer> statusDistribution = appointmentMapper.getAppointmentStatusStatistics()
                .stream()
                .collect(HashMap::new, 
                    (map, item) -> map.put((String) item.get("status"), 
                                         ((Number) item.get("count")).intValue()), 
                    HashMap::putAll);
        
        // 按日期分组的预约数据
        List<Map<String, Object>> dailyStatistics = generateDailyStatistics(startDate, endDate, departmentId);
        
        report.put("reportTitle", "预约统计报表");
        report.put("dateRange", startDateStr + " 至 " + endDateStr);
        report.put("totalAppointments", totalAppointments);
        report.put("statusDistribution", statusDistribution);
        report.put("dailyStatistics", dailyStatistics);
        report.put("generatedTime", new Date());
        
        if (departmentId != null) {
            // 获取科室信息
            cn.sdu.hospital.pojo.Department department = departmentMapper.selectById(departmentId);
            if (department != null) {
                report.put("departmentName", department.getName());
            }
        }
        
        return report;
    }

    @Override
    public Map<String, Object> generateDoctorWorkloadReport(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        Map<String, Object> report = new HashMap<>();
        
        String startDateStr = startDate.toString();
        String endDateStr = endDate.toString();
        
        // 获取医生工作量数据
        List<Map<String, Object>> doctorWorkloads = generateDoctorWorkloadData(startDate, endDate, departmentId);
        
        // 计算总体统计
        int totalDoctors = doctorWorkloads.size();
        int totalAppointments = doctorWorkloads.stream()
                .mapToInt(doctor -> (Integer) doctor.get("appointmentCount"))
                .sum();
        
        double avgAppointmentsPerDoctor = totalDoctors > 0 ? (double) totalAppointments / totalDoctors : 0;
        
        report.put("reportTitle", "医生工作量报表");
        report.put("dateRange", startDateStr + " 至 " + endDateStr);
        report.put("totalDoctors", totalDoctors);
        report.put("totalAppointments", totalAppointments);
        report.put("avgAppointmentsPerDoctor", Math.round(avgAppointmentsPerDoctor * 100.0) / 100.0);
        report.put("doctorWorkloads", doctorWorkloads);
        report.put("generatedTime", new Date());
        
        return report;
    }

    @Override
    public Map<String, Object> generateDepartmentStatisticsReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        String startDateStr = startDate.toString();
        String endDateStr = endDate.toString();
        
        // 获取所有科室
        List<cn.sdu.hospital.pojo.Department> departments = departmentMapper.selectList(null);
        List<Map<String, Object>> departmentStats = new ArrayList<>();
        
        int totalAppointments = 0;
        
        for (cn.sdu.hospital.pojo.Department dept : departments) {
            Map<String, Object> deptStat = new HashMap<>();
            int appointmentCount = appointmentMapper.getDepartmentAppointmentCount(dept.getId(), startDateStr, endDateStr);
            
            deptStat.put("departmentId", dept.getId());
            deptStat.put("departmentName", dept.getName());
            deptStat.put("appointmentCount", appointmentCount);
            
            // 计算医生数量
            QueryWrapper<cn.sdu.hospital.pojo.Doctor> wrapper = new QueryWrapper<>();
            wrapper.eq("department_id", dept.getId());
            int doctorCount = doctorMapper.selectCount(wrapper).intValue();
            deptStat.put("doctorCount", doctorCount);
            
            // 计算平均每医生预约数
            double avgPerDoctor = doctorCount > 0 ? (double) appointmentCount / doctorCount : 0;
            deptStat.put("avgAppointmentsPerDoctor", Math.round(avgPerDoctor * 100.0) / 100.0);
            
            departmentStats.add(deptStat);
            totalAppointments += appointmentCount;
        }
        
        // 按预约数量排序
        departmentStats.sort((a, b) -> 
            Integer.compare((Integer) b.get("appointmentCount"), (Integer) a.get("appointmentCount")));
        
        report.put("reportTitle", "科室预约统计报表");
        report.put("dateRange", startDateStr + " 至 " + endDateStr);
        report.put("totalDepartments", departments.size());
        report.put("totalAppointments", totalAppointments);
        report.put("departmentStatistics", departmentStats);
        report.put("generatedTime", new Date());
        
        return report;
    }

    @Override
    public byte[] exportAppointmentStatisticsCSV(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        Map<String, Object> reportData = generateAppointmentStatisticsReport(startDate, endDate, departmentId);
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
            
            // 写入BOM以支持Excel正确显示中文
            baos.write(0xEF);
            baos.write(0xBB);
            baos.write(0xBF);
            
            // CSV头部信息
            writer.write("预约统计报表\n");
            writer.write("报表日期范围," + reportData.get("dateRange") + "\n");
            writer.write("生成时间," + reportData.get("generatedTime") + "\n");
            writer.write("总预约数," + reportData.get("totalAppointments") + "\n\n");
            
            // 状态分布表
            writer.write("预约状态分布\n");
            writer.write("状态,数量\n");
            
            @SuppressWarnings("unchecked")
            Map<String, Integer> statusDistribution = (Map<String, Integer>) reportData.get("statusDistribution");
            for (Map.Entry<String, Integer> entry : statusDistribution.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            
            writer.write("\n");
            
            // 日统计表
            writer.write("每日预约统计\n");
            writer.write("日期,预约数量\n");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> dailyStats = (List<Map<String, Object>>) reportData.get("dailyStatistics");
            for (Map<String, Object> daily : dailyStats) {
                writer.write(daily.get("date") + "," + daily.get("count") + "\n");
            }
            
            writer.flush();
            return baos.toByteArray();
            
        } catch (IOException e) {
            throw new RuntimeException("生成CSV失败", e);
        }
    }

    @Override
    public byte[] exportDoctorWorkloadCSV(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        Map<String, Object> reportData = generateDoctorWorkloadReport(startDate, endDate, departmentId);
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(baos, StandardCharsets.UTF_8)) {
            
            // 写入BOM以支持Excel正确显示中文
            baos.write(0xEF);
            baos.write(0xBB);
            baos.write(0xBF);
            
            // CSV头部信息
            writer.write("医生工作量报表\n");
            writer.write("报表日期范围," + reportData.get("dateRange") + "\n");
            writer.write("生成时间," + reportData.get("generatedTime") + "\n");
            writer.write("医生总数," + reportData.get("totalDoctors") + "\n");
            writer.write("总预约数," + reportData.get("totalAppointments") + "\n");
            writer.write("平均每医生预约数," + reportData.get("avgAppointmentsPerDoctor") + "\n\n");
            
            // 医生工作量详情
            writer.write("医生工作量详情\n");
            writer.write("医生姓名,科室,预约数量,完成数量,取消数量\n");
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> doctorWorkloads = (List<Map<String, Object>>) reportData.get("doctorWorkloads");
            for (Map<String, Object> doctor : doctorWorkloads) {
                writer.write(
                    doctor.get("doctorName") + "," +
                    doctor.get("departmentName") + "," +
                    doctor.get("appointmentCount") + "," +
                    doctor.get("completedCount") + "," +
                    doctor.get("cancelledCount") + "\n"
                );
            }
            
            writer.flush();
            return baos.toByteArray();
            
        } catch (IOException e) {
            throw new RuntimeException("生成CSV失败", e);
        }
    }

    @Override
    public Map<String, Object> generateComprehensiveAnalysisReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        // 获取各种统计数据
        Map<String, Object> appointmentStats = generateAppointmentStatisticsReport(startDate, endDate, null);
        Map<String, Object> doctorStats = generateDoctorWorkloadReport(startDate, endDate, null);
        Map<String, Object> departmentStats = generateDepartmentStatisticsReport(startDate, endDate);
        
        // 生成分析建议
        List<String> analysisInsights = generateAnalysisInsights(appointmentStats, doctorStats, departmentStats);
        
        report.put("reportTitle", "综合分析报告");
        report.put("dateRange", startDate.toString() + " 至 " + endDate.toString());
        report.put("appointmentStatistics", appointmentStats);
        report.put("doctorWorkloadStatistics", doctorStats);
        report.put("departmentStatistics", departmentStats);
        report.put("analysisInsights", analysisInsights);
        report.put("generatedTime", new Date());
        
        return report;
    }

    // 辅助方法：生成每日统计数据
    private List<Map<String, Object>> generateDailyStatistics(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        List<Map<String, Object>> dailyStats = new ArrayList<>();
        
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Map<String, Object> dailyStat = new HashMap<>();
            String dateStr = currentDate.toString();
            
            int count;
            if (departmentId != null) {
                count = appointmentMapper.getDepartmentAppointmentCount(departmentId, dateStr, dateStr);
            } else {
                count = appointmentMapper.getSystemAppointmentCount(dateStr, dateStr);
            }
            
            dailyStat.put("date", dateStr);
            dailyStat.put("count", count);
            dailyStats.add(dailyStat);
            
            currentDate = currentDate.plusDays(1);
        }
        
        return dailyStats;
    }

    // 辅助方法：生成医生工作量数据
    private List<Map<String, Object>> generateDoctorWorkloadData(LocalDate startDate, LocalDate endDate, Integer departmentId) {
        List<Map<String, Object>> workloads = new ArrayList<>();
        
        // 获取医生列表
        QueryWrapper<cn.sdu.hospital.pojo.Doctor> wrapper = new QueryWrapper<>();
        if (departmentId != null) {
            wrapper.eq("department_id", departmentId);
        }
        List<cn.sdu.hospital.pojo.Doctor> doctors = doctorMapper.selectList(wrapper);
        
        for (cn.sdu.hospital.pojo.Doctor doctor : doctors) {
            Map<String, Object> workload = new HashMap<>();
            
            // 获取医生基本信息
            workload.put("doctorId", doctor.getId());
            workload.put("doctorName", doctor.getName());
            
            // 获取科室信息
            if (doctor.getDepartmentId() != null) {
                cn.sdu.hospital.pojo.Department department = departmentMapper.selectById(doctor.getDepartmentId());
                workload.put("departmentName", department != null ? department.getName() : "未分配");
            } else {
                workload.put("departmentName", "未分配");
            }
            
            // 计算预约数量
            String startDateStr = startDate.toString();
            String endDateStr = endDate.toString();
            
            int totalCount = appointmentMapper.getMonthAppointmentCount(doctor.getId(), startDateStr, endDateStr);
            // 这里可以扩展，统计不同状态的预约数量
            workload.put("appointmentCount", totalCount);
            workload.put("completedCount", 0); // 需要实现具体的状态统计
            workload.put("cancelledCount", 0); // 需要实现具体的状态统计
            
            workloads.add(workload);
        }
        
        // 按预约数量排序
        workloads.sort((a, b) -> 
            Integer.compare((Integer) b.get("appointmentCount"), (Integer) a.get("appointmentCount")));
        
        return workloads;
    }

    // 辅助方法：生成分析洞察
    private List<String> generateAnalysisInsights(Map<String, Object> appointmentStats, 
                                                  Map<String, Object> doctorStats, 
                                                  Map<String, Object> departmentStats) {
        List<String> insights = new ArrayList<>();
        
        // 预约量分析
        int totalAppointments = (Integer) appointmentStats.get("totalAppointments");
        if (totalAppointments > 100) {
            insights.add("预约量较高，建议关注医生排班安排和资源配置");
        } else if (totalAppointments < 20) {
            insights.add("预约量较低，可考虑增加宣传或优化预约流程");
        }
        
        // 医生工作量分析
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> doctorWorkloads = (List<Map<String, Object>>) doctorStats.get("doctorWorkloads");
        if (!doctorWorkloads.isEmpty()) {
            int maxWorkload = (Integer) doctorWorkloads.get(0).get("appointmentCount");
            int minWorkload = (Integer) doctorWorkloads.get(doctorWorkloads.size() - 1).get("appointmentCount");
            
            if (maxWorkload - minWorkload > 20) {
                insights.add("医生工作量分布不均，建议优化排班以平衡工作负荷");
            }
        }
        
        // 科室分析
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> deptStats = (List<Map<String, Object>>) departmentStats.get("departmentStatistics");
        if (deptStats.size() > 1) {
            int topDeptCount = (Integer) deptStats.get(0).get("appointmentCount");
            int totalDeptAppointments = (Integer) departmentStats.get("totalAppointments");
            
            if (topDeptCount > totalDeptAppointments * 0.4) {
                insights.add("部分科室预约集中度较高，建议评估是否需要增加医生资源");
            }
        }
        
        return insights;
    }
} 