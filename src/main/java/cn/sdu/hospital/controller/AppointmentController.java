package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Appointment;
import cn.sdu.hospital.pojo.AppointmentVO;
import cn.sdu.hospital.service.AppointmentService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 预约控制类
 * @author Administrator
 */
@RestController
@Tag(name = "预约管理接口")
@RequestMapping("/appointment")
public class AppointmentController {
    
    @Autowired
    private AppointmentService appointmentService;
    
    /**
     * 挂号预约
     * @param appointment 预约信息
     * @return 操作结果
     */
    @PostMapping("/makeAppointment")
    @Operation(summary = "挂号预约")
    public ServerResult<Void> makeAppointment(@RequestBody Appointment appointment) {
        boolean result = appointmentService.makeAppointment(appointment);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "预约失败，可能号源已满或排班不存在");
        }
    }
    
    /**
     * 取消预约
     * @param id 预约ID
     * @return 操作结果
     */
    @PutMapping("/cancel")
    @Operation(summary = "取消预约")
    public ServerResult<Void> cancelAppointment(@RequestParam Integer id) {
        boolean result = appointmentService.cancelAppointment(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "取消预约失败");
        }
    }
    
    /**
     * 修改预约
     * @param appointment 预约信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "修改预约")
    public ServerResult<Void> updateAppointment(@RequestBody Appointment appointment) {
        boolean result = appointmentService.updateAppointment(appointment);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "修改预约失败");
        }
    }
    
    /**
     * 修改预约排班（患者专用）
     * @param appointmentData 预约修改数据
     * @return 操作结果
     */
    @PutMapping("/modifySchedule")
    @Operation(summary = "修改预约排班")
    public ServerResult<Void> modifyAppointmentSchedule(@RequestBody Map<String, Object> appointmentData) {
        try {
            Integer appointmentId = null;
            Integer scheduleId = null;
            String symptoms = null;
            
            // 安全地转换appointmentId
            Object appointmentIdObj = appointmentData.get("id");
            if (appointmentIdObj instanceof Integer) {
                appointmentId = (Integer) appointmentIdObj;
            } else if (appointmentIdObj instanceof String) {
                appointmentId = Integer.parseInt((String) appointmentIdObj);
            }
            
            // 安全地转换scheduleId
            Object scheduleIdObj = appointmentData.get("scheduleId");
            if (scheduleIdObj instanceof Integer) {
                scheduleId = (Integer) scheduleIdObj;
            } else if (scheduleIdObj instanceof String) {
                scheduleId = Integer.parseInt((String) scheduleIdObj);
            }
            
            // 获取症状描述
            Object symptomsObj = appointmentData.get("symptoms");
            if (symptomsObj instanceof String) {
                symptoms = (String) symptomsObj;
            }
            
            if (appointmentId == null || scheduleId == null) {
                return ServerResult.error(400, "预约ID和排班ID不能为空");
            }
            
            boolean result = appointmentService.modifyAppointmentSchedule(appointmentId, scheduleId, symptoms);
            if (result) {
                return ServerResult.ok();
            } else {
                return ServerResult.error(500, "修改预约失败，可能号源已满或预约不存在");
            }
        } catch (NumberFormatException e) {
            return ServerResult.error(400, "参数格式错误");
        } catch (Exception e) {
            return ServerResult.error(500, "修改预约失败：" + e.getMessage());
        }
    }
    
    /**
     * 根据患者ID查询预约
     * @param patientId 患者ID
     * @return 预约列表
     */
    @GetMapping("/selectByPatientId")
    @Operation(summary = "根据患者ID查询预约")
    public ServerResult<List<Appointment>> selectByPatientId(@RequestParam Integer patientId) {
        List<Appointment> list = appointmentService.selectByPatientId(patientId);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据排班ID查询预约队列
     * @param scheduleId 排班ID
     * @return 预约列表
     */
    @GetMapping("/selectByScheduleId")
    @Operation(summary = "根据排班ID查询预约队列")
    public ServerResult<List<Appointment>> selectByScheduleId(@RequestParam Integer scheduleId) {
        List<Appointment> list = appointmentService.selectByScheduleId(scheduleId);
        return ServerResult.ok(list);
    }
    
    /**
     * 查询所有预约
     * @return 预约列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有预约")
    public ServerResult<List<Appointment>> selectAll() {
        List<Appointment> list = appointmentService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 查询所有预约详情（管理员专用）
     * @return 预约详情列表
     */
    @GetMapping("/admin/selectAll")
    @Operation(summary = "查询所有预约详情")
    public ServerResult<List<AppointmentVO>> selectAllForAdmin() {
        List<AppointmentVO> list = appointmentService.selectAllAppointmentVO();
        return ServerResult.ok(list);
    }
    
    /**
     * 根据ID查询预约
     * @param id 预约ID
     * @return 预约信息
     */
    @GetMapping("/selectById")
    @Operation(summary = "根据ID查询预约")
    public ServerResult<Appointment> selectById(@RequestParam Integer id) {
        Appointment appointment = appointmentService.selectById(id);
        if (appointment != null) {
            return ServerResult.ok(appointment);
        } else {
            return ServerResult.error(404, "预约不存在");
        }
    }
    
    /**
     * 根据ID查询预约详情
     * @param id 预约ID
     * @return 预约详情
     */
    @GetMapping("/vo/selectById")
    @Operation(summary = "根据ID查询预约详情")
    public ServerResult<AppointmentVO> selectAppointmentVOById(@RequestParam Integer id) {
        AppointmentVO appointmentVO = appointmentService.selectAppointmentVOById(id);
        if (appointmentVO != null) {
            return ServerResult.ok(appointmentVO);
        } else {
            return ServerResult.error(404, "预约不存在");
        }
    }
    
    /**
     * 完成预约（就诊完成）
     * @param id 预约ID
     * @return 操作结果
     */
    @PutMapping("/complete")
    @Operation(summary = "完成预约")
    public ServerResult<Void> completeAppointment(@RequestParam Integer id) {
        boolean result = appointmentService.completeAppointment(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "完成预约失败");
        }
    }
    
    /**
     * 创建预约（简化版）
     * @param appointmentData 预约数据
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "创建预约")
    public ServerResult<Void> createAppointment(@RequestBody Map<String, Object> appointmentData) {
        try {
            Appointment appointment = new Appointment();
            
            // 安全地转换patientId
            Object patientIdObj = appointmentData.get("patientId");
            Integer patientId = null;
            if (patientIdObj instanceof Integer) {
                patientId = (Integer) patientIdObj;
            } else if (patientIdObj instanceof String) {
                patientId = Integer.parseInt((String) patientIdObj);
            }
            
            // 安全地转换scheduleId
            Object scheduleIdObj = appointmentData.get("scheduleId");
            Integer scheduleId = null;
            if (scheduleIdObj instanceof Integer) {
                scheduleId = (Integer) scheduleIdObj;
            } else if (scheduleIdObj instanceof String) {
                scheduleId = Integer.parseInt((String) scheduleIdObj);
            }
            
            if (patientId == null || scheduleId == null) {
                return ServerResult.error(400, "患者ID和排班ID不能为空");
            }
            
            appointment.setPatientId(patientId);
            appointment.setScheduleId(scheduleId);
            appointment.setStatus("已预约");
            
            // 设置症状描述（如果有）
            Object symptomsObj = appointmentData.get("symptoms");
            if (symptomsObj instanceof String) {
                appointment.setSymptoms((String) symptomsObj);
            }
            
            boolean result = appointmentService.makeAppointment(appointment);
            if (result) {
                return ServerResult.ok();
            } else {
                return ServerResult.error(500, "预约失败，可能号源已满或排班不存在");
            }
        } catch (NumberFormatException e) {
            return ServerResult.error(400, "参数格式错误");
        } catch (Exception e) {
            return ServerResult.error(500, "预约失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除预约
     * @param id 预约ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除预约")
    public ServerResult<Void> deleteAppointment(@PathVariable Integer id) {
        boolean result = appointmentService.cancelAppointment(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "取消预约失败");
        }
    }
    
    /**
     * 更新预约状态
     * @param id 预约ID
     * @param statusData 状态数据
     * @return 操作结果
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新预约状态")
    public ServerResult<Void> updateAppointmentStatus(@PathVariable Integer id, @RequestBody Map<String, String> statusData) {
        String status = statusData.get("status");
        Appointment appointment = appointmentService.selectById(id);
        if (appointment == null) {
            return ServerResult.error(404, "预约不存在");
        }
        
        appointment.setStatus(status);
        boolean result = appointmentService.updateAppointment(appointment);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "更新预约状态失败");
        }
    }
    
    /**
     * 获取患者当前预约
     * @param patientId 患者ID
     * @return 当前预约列表
     */
    @GetMapping("/patient/{patientId}/current")
    @Operation(summary = "获取患者当前预约")
    public ServerResult<List<AppointmentVO>> getPatientCurrentAppointments(@PathVariable Integer patientId) {
        List<AppointmentVO> list = appointmentService.selectAppointmentVOByPatientId(patientId);
        // 过滤出未完成的预约
        list = list.stream()
                .filter(appointment -> !"已完成".equals(appointment.getStatus()) && !"已取消".equals(appointment.getStatus()))
                .collect(java.util.stream.Collectors.toList());
        return ServerResult.ok(list);
    }
    
    /**
     * 获取患者历史预约
     * @param patientId 患者ID
     * @return 历史预约列表
     */
    @GetMapping("/patient/{patientId}/history")
    @Operation(summary = "获取患者历史预约")
    public ServerResult<List<AppointmentVO>> getPatientAppointmentHistory(@PathVariable Integer patientId) {
        List<AppointmentVO> list = appointmentService.selectAppointmentVOByPatientId(patientId);
        return ServerResult.ok(list);
    }
    
    /**
     * 获取医生指定日期的患者队列
     * @param doctorId 医生ID
     * @param date 日期
     * @return 患者队列
     */
    @GetMapping("/doctor/{doctorId}/date/{date}")
    @Operation(summary = "获取医生指定日期的患者队列")
    public ServerResult<List<AppointmentVO>> getDoctorAppointmentsByDate(@PathVariable Integer doctorId, @PathVariable String date) {
        List<AppointmentVO> list = appointmentService.selectAppointmentVOByDoctorIdAndDate(doctorId, date);
        return ServerResult.ok(list);
    }
    
    /**
     * 获取医生历史预约
     * @param doctorId 医生ID
     * @return 历史预约列表
     */
    @GetMapping("/doctor/{doctorId}/history")
    @Operation(summary = "获取医生历史预约")
    public ServerResult<List<AppointmentVO>> getDoctorAppointmentHistory(@PathVariable Integer doctorId) {
        List<AppointmentVO> list = appointmentService.selectAppointmentVOByDoctorId(doctorId);
        return ServerResult.ok(list);
    }
    
    /**
     * 获取医生预约统计
     * @param doctorId 医生ID
     * @return 统计数据
     */
    @GetMapping("/doctor/{doctorId}/statistics")
    @Operation(summary = "获取医生预约统计")
    public ServerResult<Map<String, Object>> getDoctorAppointmentStatistics(@PathVariable Integer doctorId) {
        Map<String, Object> stats = new HashMap<>();
        
        // 获取真实的统计数据
        stats.put("today", appointmentService.getTodayAppointmentCount(doctorId));
        stats.put("week", appointmentService.getWeekAppointmentCount(doctorId));
        stats.put("month", appointmentService.getMonthAppointmentCount(doctorId));
        stats.put("total", appointmentService.getTotalPatientCount(doctorId));
        
        return ServerResult.ok(stats);
    }
    
    /**
     * 获取系统总体统计数据
     * @return 统计数据
     */
    @GetMapping("/statistics/system")
    @Operation(summary = "获取系统总体统计数据")
    public ServerResult<Map<String, Object>> getSystemStatistics() {
        Map<String, Object> stats = appointmentService.getSystemStatistics();
        return ServerResult.ok(stats);
    }
    
    /**
     * 获取预约状态分布统计
     * @return 状态分布数据
     */
    @GetMapping("/statistics/status")
    @Operation(summary = "获取预约状态分布统计")
    public ServerResult<Map<String, Integer>> getAppointmentStatusStatistics() {
        Map<String, Integer> stats = appointmentService.getAppointmentStatusStatistics();
        return ServerResult.ok(stats);
    }
    
    /**
     * 根据科室获取预约统计
     * @param departmentId 科室ID
     * @return 统计数据
     */
    @GetMapping("/statistics/department/{departmentId}")
    @Operation(summary = "根据科室获取预约统计")
    public ServerResult<Map<String, Object>> getDepartmentStatistics(@PathVariable Integer departmentId) {
        Map<String, Object> stats = appointmentService.getDepartmentStatistics(departmentId);
        return ServerResult.ok(stats);
    }
} 