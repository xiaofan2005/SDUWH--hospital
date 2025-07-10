package cn.sdu.hospital.controller;

import cn.sdu.hospital.service.ReportService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

/**
 * 报表控制层
 * @author Administrator
 */
@RestController
@RequestMapping("/report")
@Tag(name = "报表管理接口")
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 生成预约统计报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return 报表数据
     */
    @GetMapping("/appointment-statistics")
    @Operation(summary = "生成预约统计报表数据")
    public ServerResult<Map<String, Object>> generateAppointmentStatisticsReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer departmentId) {
        
        Map<String, Object> reportData = reportService.generateAppointmentStatisticsReport(
                startDate, endDate, departmentId);
        return ServerResult.ok(reportData);
    }

    /**
     * 生成医生工作量报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return 报表数据
     */
    @GetMapping("/doctor-workload")
    @Operation(summary = "生成医生工作量报表数据")
    public ServerResult<Map<String, Object>> generateDoctorWorkloadReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer departmentId) {
        
        Map<String, Object> reportData = reportService.generateDoctorWorkloadReport(
                startDate, endDate, departmentId);
        return ServerResult.ok(reportData);
    }

    /**
     * 生成科室预约统计报表数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 报表数据
     */
    @GetMapping("/department-statistics")
    @Operation(summary = "生成科室预约统计报表数据")
    public ServerResult<Map<String, Object>> generateDepartmentStatisticsReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Map<String, Object> reportData = reportService.generateDepartmentStatisticsReport(startDate, endDate);
        return ServerResult.ok(reportData);
    }

    /**
     * 导出预约统计报表为CSV
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return CSV文件
     */
    @GetMapping("/export/appointment-statistics/csv")
    @Operation(summary = "导出预约统计报表为CSV")
    public ResponseEntity<byte[]> exportAppointmentStatisticsCSV(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer departmentId) {
        
        byte[] csvData = reportService.exportAppointmentStatisticsCSV(startDate, endDate, departmentId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", 
                "appointment_statistics_" + startDate + "_to_" + endDate + ".csv");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    /**
     * 导出医生工作量报表为CSV
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param departmentId 科室ID（可选）
     * @return CSV文件
     */
    @GetMapping("/export/doctor-workload/csv")
    @Operation(summary = "导出医生工作量报表为CSV")
    public ResponseEntity<byte[]> exportDoctorWorkloadCSV(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Integer departmentId) {
        
        byte[] csvData = reportService.exportDoctorWorkloadCSV(startDate, endDate, departmentId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", 
                "doctor_workload_" + startDate + "_to_" + endDate + ".csv");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    /**
     * 生成综合分析报告
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 分析报告数据
     */
    @GetMapping("/comprehensive-analysis")
    @Operation(summary = "生成综合分析报告")
    public ServerResult<Map<String, Object>> generateComprehensiveAnalysisReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        Map<String, Object> reportData = reportService.generateComprehensiveAnalysisReport(startDate, endDate);
        return ServerResult.ok(reportData);
    }
} 