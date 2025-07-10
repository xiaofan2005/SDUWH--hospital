package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Schedule;
import cn.sdu.hospital.pojo.ScheduleVO;
import cn.sdu.hospital.service.ScheduleService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 排班控制层
 * @author Administrator
 */
@RestController
@Tag(name = "排班管理接口")
@RequestMapping("/schedule")
public class ScheduleController {
    
    @Autowired
    private ScheduleService scheduleService;
    
    /**
     * 新增排班
     * @param schedule 排班信息
     * @return 操作结果
     */
    @PostMapping("/insert")
    @Operation(summary = "新增排班")
    public ServerResult<Void> insert(@RequestBody Schedule schedule) {
        boolean result = scheduleService.insert(schedule);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "新增排班失败");
        }
    }
    
    /**
     * 修改排班
     * @param schedule 排班信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "修改排班")
    public ServerResult<Void> update(@RequestBody Schedule schedule) {
        boolean result = scheduleService.update(schedule);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "修改排班失败");
        }
    }
    
    /**
     * 根据ID删除排班
     * @param id 排班ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除排班")
    public ServerResult<Void> deleteById(@RequestParam Integer id) {
        boolean result = scheduleService.deleteById(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "删除排班失败");
        }
    }
    
    /**
     * 根据医生ID查询排班
     * @param doctorId 医生ID
     * @return 排班列表
     */
    @GetMapping("/selectByDoctorId")
    @Operation(summary = "根据医生ID查询排班")
    public ServerResult<List<Schedule>> selectByDoctorId(@RequestParam Integer doctorId) {
        List<Schedule> list = scheduleService.selectByDoctorId(doctorId);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据医生ID查询排班详情
     * @param doctorId 医生ID
     * @return 排班详情列表
     */
    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "根据医生ID查询排班详情")
    public ServerResult<List<ScheduleVO>> getSchedulesByDoctorId(@PathVariable Integer doctorId) {
        List<ScheduleVO> list = scheduleService.selectScheduleVOByDoctorId(doctorId);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据日期查询排班
     * @param date 日期
     * @return 排班列表
     */
    @GetMapping("/selectByDate")
    @Operation(summary = "根据日期查询排班")
    public ServerResult<List<Schedule>> selectByDate(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Schedule> list = scheduleService.selectByDate(localDate);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据医生ID和日期查询排班
     * @param doctorId 医生ID
     * @param date 日期
     * @return 排班列表
     */
    @GetMapping("/selectByDoctorAndDate")
    @Operation(summary = "根据医生ID和日期查询排班")
    public ServerResult<List<Schedule>> selectByDoctorAndDate(@RequestParam Integer doctorId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Schedule> list = scheduleService.selectByDoctorAndDate(doctorId, localDate);
        return ServerResult.ok(list);
    }
    
    /**
     * 查询所有排班
     * @return 排班列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有排班")
    public ServerResult<List<Schedule>> selectAll() {
        List<Schedule> list = scheduleService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 查询所有排班详情（管理员专用）
     * @return 排班详情列表
     */
    @GetMapping("/admin/selectAll")
    @Operation(summary = "查询所有排班详情")
    public ServerResult<List<ScheduleVO>> selectAllForAdmin() {
        List<ScheduleVO> list = scheduleService.selectAllScheduleVO();
        return ServerResult.ok(list);
    }
    
    /**
     * 根据ID查询排班
     * @param id 排班ID
     * @return 排班信息
     */
    @GetMapping("/selectById")
    @Operation(summary = "根据ID查询排班")
    public ServerResult<Schedule> selectById(@RequestParam Integer id) {
        Schedule schedule = scheduleService.selectById(id);
        if (schedule != null) {
            return ServerResult.ok(schedule);
        } else {
            return ServerResult.error(404, "排班不存在");
        }
    }
    
    /**
     * 查询可用排班
     * @return 可用排班列表
     */
    @GetMapping("/selectAvailable")
    @Operation(summary = "查询可用排班")
    public ServerResult<List<Schedule>> selectAvailableSchedules() {
        List<Schedule> list = scheduleService.selectAvailableSchedules();
        return ServerResult.ok(list);
    }
    
    /**
     * 停诊处理
     * @param id 排班ID
     * @return 操作结果
     */
    @PutMapping("/stop")
    @Operation(summary = "停诊处理")
    public ServerResult<Void> stopSchedule(@RequestParam Integer id) {
        boolean result = scheduleService.stopSchedule(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "停诊处理失败");
        }
    }
    
    /**
     * 根据医生ID查询可用排班（前端调用）
     * @param doctorId 医生ID
     * @return 可用排班详情列表
     */
    @GetMapping("/doctor/{doctorId}/available")
    @Operation(summary = "根据医生ID查询可用排班")
    public ServerResult<List<ScheduleVO>> getAvailableByDoctorId(@PathVariable Integer doctorId) {
        List<ScheduleVO> list = scheduleService.selectAvailableScheduleVOByDoctorId(doctorId);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据科室ID查询排班
     * @param departmentId 科室ID
     * @return 排班列表
     */
    @GetMapping("/selectByDepartmentId")
    @Operation(summary = "根据科室ID查询排班")
    public ServerResult<List<Schedule>> selectByDepartmentId(@RequestParam Integer departmentId) {
        List<Schedule> list = scheduleService.selectByDepartmentId(departmentId);
        return ServerResult.ok(list);
    }
} 