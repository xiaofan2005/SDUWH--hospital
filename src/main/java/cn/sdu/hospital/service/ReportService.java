package cn.sdu.hospital.service;

import java.time.LocalDate;
import java.util.Map;

/**
 * 报表业务层接口
 * @author Administrator
 */
public interface ReportService {
    
    /**
     * 生成预约统计报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return 报表数据
     */
    Map<String, Object> generateAppointmentStatisticsReport(LocalDate startDate, LocalDate endDate, Integer departmentId);
    
    /**
     * 生成医生工作量报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return 报表数据
     */
    Map<String, Object> generateDoctorWorkloadReport(LocalDate startDate, LocalDate endDate, Integer departmentId);
    
    /**
     * 生成科室预约统计报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 报表数据
     */
    Map<String, Object> generateDepartmentStatisticsReport(LocalDate startDate, LocalDate endDate);
    
    /**
     * 导出预约统计报表为CSV
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return CSV数据
     */
    byte[] exportAppointmentStatisticsCSV(LocalDate startDate, LocalDate endDate, Integer departmentId);
    
    /**
     * 导出医生工作量报表为CSV
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return CSV数据
     */
    byte[] exportDoctorWorkloadCSV(LocalDate startDate, LocalDate endDate, Integer departmentId);
    
    /**
     * 生成综合分析报告
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分析报告数据
     */
    Map<String, Object> generateComprehensiveAnalysisReport(LocalDate startDate, LocalDate endDate);
} 