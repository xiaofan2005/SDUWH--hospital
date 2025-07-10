package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.AppointmentMapper;
import cn.sdu.hospital.mapper.ScheduleMapper;
import cn.sdu.hospital.mapper.DoctorMapper;
import cn.sdu.hospital.mapper.DepartmentMapper;
import cn.sdu.hospital.mapper.PatientMapper;
import cn.sdu.hospital.mapper.TimeSlotMapper;
import cn.sdu.hospital.pojo.Appointment;
import cn.sdu.hospital.pojo.AppointmentVO;
import cn.sdu.hospital.pojo.Schedule;
import cn.sdu.hospital.pojo.Doctor;
import cn.sdu.hospital.pojo.Department;
import cn.sdu.hospital.pojo.Patient;
import cn.sdu.hospital.pojo.TimeSlot;
import cn.sdu.hospital.service.AppointmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

/**
 * 预约业务层实现类
 * @author Administrator
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {
    
    @Autowired
    private AppointmentMapper appointmentMapper;
    
    @Autowired
    private ScheduleMapper scheduleMapper;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Autowired
    private PatientMapper patientMapper;
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Override
    public boolean makeAppointment(Appointment appointment) {
        // 检查排班是否可预约
        Schedule schedule = scheduleMapper.selectById(appointment.getScheduleId());
        if (schedule != null && schedule.getBookedCount() < schedule.getTotalCount()) {
            // 设置预约号码
            appointment.setAppointmentNumber(schedule.getBookedCount() + 1);
            // 设置预约创建时间
            appointment.setAppointmentTime(LocalDateTime.now());
            // 保存预约
            if (appointmentMapper.insert(appointment) > 0) {
                // 更新排班已预约数
                schedule.setBookedCount(schedule.getBookedCount() + 1);
                scheduleMapper.updateById(schedule);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean cancelAppointment(Integer id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment != null) {
            appointment.setStatus("已取消");
            if (appointmentMapper.updateById(appointment) > 0) {
                // 更新排班已预约数
                Schedule schedule = scheduleMapper.selectById(appointment.getScheduleId());
                if (schedule != null) {
                    schedule.setBookedCount(schedule.getBookedCount() - 1);
                    scheduleMapper.updateById(schedule);
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean updateAppointment(Appointment appointment) {
        return appointmentMapper.updateById(appointment) > 0;
    }
    
    @Override
    public List<Appointment> selectByPatientId(Integer patientId) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id", patientId);
        return appointmentMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<AppointmentVO> selectAppointmentVOByPatientId(Integer patientId) {
        List<Appointment> appointments = selectByPatientId(patientId);
        List<AppointmentVO> voList = new ArrayList<>();
        
        for (Appointment appointment : appointments) {
            AppointmentVO vo = convertToVO(appointment);
            voList.add(vo);
        }
        
        return voList;
    }
    
    @Override
    public List<Appointment> selectByScheduleId(Integer scheduleId) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_id", scheduleId).eq("status", "已预约");
        return appointmentMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<AppointmentVO> selectAppointmentVOByDoctorId(Integer doctorId) {
        // 先查询该医生的所有排班
        QueryWrapper<Schedule> scheduleQuery = new QueryWrapper<>();
        scheduleQuery.eq("doctor_id", doctorId);
        List<Schedule> schedules = scheduleMapper.selectList(scheduleQuery);
        
        List<AppointmentVO> voList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<Appointment> appointments = selectByScheduleId(schedule.getId());
            for (Appointment appointment : appointments) {
                AppointmentVO vo = convertToVO(appointment);
                voList.add(vo);
            }
        }
        
        return voList;
    }
    
    @Override
    public List<AppointmentVO> selectAppointmentVOByDoctorIdAndDate(Integer doctorId, String date) {
        // 先查询该医生在指定日期的排班
        QueryWrapper<Schedule> scheduleQuery = new QueryWrapper<>();
        scheduleQuery.eq("doctor_id", doctorId).eq("schedule_date", date);
        List<Schedule> schedules = scheduleMapper.selectList(scheduleQuery);
        
        List<AppointmentVO> voList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            List<Appointment> appointments = selectByScheduleId(schedule.getId());
            for (Appointment appointment : appointments) {
                AppointmentVO vo = convertToVO(appointment);
                voList.add(vo);
            }
        }
        
        return voList;
    }
    
    @Override
    public List<Appointment> selectAll() {
        return appointmentMapper.selectList(null);
    }
    
    @Override
    public List<AppointmentVO> selectAllAppointmentVO() {
        List<Appointment> appointments = selectAll();
        List<AppointmentVO> voList = new ArrayList<>();
        
        for (Appointment appointment : appointments) {
            AppointmentVO vo = convertToVO(appointment);
            voList.add(vo);
        }
        
        return voList;
    }
    
    @Override
    public AppointmentVO selectAppointmentVOById(Integer id) {
        Appointment appointment = selectById(id);
        if (appointment != null) {
            return convertToVO(appointment);
        }
        return null;
    }
    
    @Override
    public boolean modifyAppointmentSchedule(Integer appointmentId, Integer newScheduleId, String symptoms) {
        // 获取原预约信息
        Appointment appointment = appointmentMapper.selectById(appointmentId);
        if (appointment == null) {
            return false;
        }
        
        Integer oldScheduleId = appointment.getScheduleId();
        
        // 如果排班没有变化，只更新症状
        if (oldScheduleId.equals(newScheduleId)) {
            appointment.setSymptoms(symptoms);
            return appointmentMapper.updateById(appointment) > 0;
        }
        
        // 检查新排班是否可预约
        Schedule newSchedule = scheduleMapper.selectById(newScheduleId);
        if (newSchedule == null || newSchedule.getBookedCount() >= newSchedule.getTotalCount()) {
            return false;
        }
        
        // 减少原排班的已预约数
        Schedule oldSchedule = scheduleMapper.selectById(oldScheduleId);
        if (oldSchedule != null) {
            oldSchedule.setBookedCount(oldSchedule.getBookedCount() - 1);
            scheduleMapper.updateById(oldSchedule);
        }
        
        // 增加新排班的已预约数，并分配新的预约号码
        newSchedule.setBookedCount(newSchedule.getBookedCount() + 1);
        scheduleMapper.updateById(newSchedule);
        
        // 更新预约信息
        appointment.setScheduleId(newScheduleId);
        appointment.setAppointmentNumber(newSchedule.getBookedCount());
        appointment.setSymptoms(symptoms);
        
        return appointmentMapper.updateById(appointment) > 0;
    }
    
    @Override
    public int getTodayAppointmentCount(Integer doctorId) {
        String today = LocalDate.now().toString();
        return appointmentMapper.getTodayAppointmentCount(doctorId, today);
    }
    
    @Override
    public int getWeekAppointmentCount(Integer doctorId) {
        LocalDate now = LocalDate.now();
        LocalDate weekStart = now.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = now.with(DayOfWeek.SUNDAY);
        return appointmentMapper.getWeekAppointmentCount(doctorId, weekStart.toString(), weekEnd.toString());
    }
    
    @Override
    public int getMonthAppointmentCount(Integer doctorId) {
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());
        return appointmentMapper.getMonthAppointmentCount(doctorId, monthStart.toString(), monthEnd.toString());
    }
    
    @Override
    public int getTotalPatientCount(Integer doctorId) {
        return appointmentMapper.getTotalPatientCount(doctorId);
    }
    
    @Override
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        LocalDate now = LocalDate.now();
        String today = now.toString();
        LocalDate weekStart = now.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = now.with(DayOfWeek.SUNDAY);
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());
        
        // 今日预约数
        int todayCount = appointmentMapper.getSystemAppointmentCount(today, today);
        statistics.put("todayAppointments", todayCount);
        
        // 本周预约数
        int weekCount = appointmentMapper.getSystemAppointmentCount(weekStart.toString(), weekEnd.toString());
        statistics.put("weekAppointments", weekCount);
        
        // 本月预约数
        int monthCount = appointmentMapper.getSystemAppointmentCount(monthStart.toString(), monthEnd.toString());
        statistics.put("monthAppointments", monthCount);
        
        // 总患者数
        int totalPatients = appointmentMapper.getSystemTotalPatientCount();
        statistics.put("totalPatients", totalPatients);
        
        // 总预约数
        int totalAppointments = appointmentMapper.selectCount(null);
        statistics.put("totalAppointments", totalAppointments);
        
        return statistics;
    }
    
    @Override
    public Map<String, Object> getDepartmentStatistics(Integer departmentId) {
        Map<String, Object> statistics = new HashMap<>();
        
        LocalDate now = LocalDate.now();
        LocalDate monthStart = now.withDayOfMonth(1);
        LocalDate monthEnd = now.withDayOfMonth(now.lengthOfMonth());
        
        // 本月科室预约数
        int monthCount = appointmentMapper.getDepartmentAppointmentCount(departmentId, monthStart.toString(), monthEnd.toString());
        statistics.put("monthAppointments", monthCount);
        
        // 今年科室预约数
        LocalDate yearStart = now.withDayOfYear(1);
        LocalDate yearEnd = now.withDayOfYear(now.lengthOfYear());
        int yearCount = appointmentMapper.getDepartmentAppointmentCount(departmentId, yearStart.toString(), yearEnd.toString());
        statistics.put("yearAppointments", yearCount);
        
        return statistics;
    }
    
    @Override
    public Map<String, Integer> getAppointmentStatusStatistics() {
        List<Map<String, Object>> results = appointmentMapper.getAppointmentStatusStatistics();
        Map<String, Integer> statistics = new HashMap<>();
        
        for (Map<String, Object> result : results) {
            String status = (String) result.get("status");
            Object countObj = result.get("count");
            Integer count = 0;
            
            if (countObj instanceof Long) {
                count = ((Long) countObj).intValue();
            } else if (countObj instanceof Integer) {
                count = (Integer) countObj;
            }
            
            statistics.put(status, count);
        }
        
        return statistics;
    }
    
    @Override
    public Appointment selectById(Integer id) {
        return appointmentMapper.selectById(id);
    }
    
    @Override
    public boolean completeAppointment(Integer id) {
        Appointment appointment = appointmentMapper.selectById(id);
        if (appointment != null) {
            appointment.setStatus("已完成");
            return appointmentMapper.updateById(appointment) > 0;
        }
        return false;
    }
    
    /**
     * 将Appointment转换为AppointmentVO
     */
    private AppointmentVO convertToVO(Appointment appointment) {
        AppointmentVO vo = new AppointmentVO();
        
        // 复制基础字段
        vo.setId(appointment.getId());
        vo.setPatientId(appointment.getPatientId());
        vo.setScheduleId(appointment.getScheduleId());
        vo.setAppointmentNumber(appointment.getAppointmentNumber());
        vo.setStatus(appointment.getStatus());
        vo.setSymptoms(appointment.getSymptoms());
        vo.setAppointmentTime(appointment.getAppointmentTime());
        vo.setUpdateTime(appointment.getUpdateTime());
        
        // 查询患者信息
        if (appointment.getPatientId() != null) {
            Patient patient = patientMapper.selectById(appointment.getPatientId());
            if (patient != null) {
                vo.setPatientName(patient.getName());
                vo.setPatientPhone(patient.getPhone());
            }
        }
        
        // 查询排班信息
        if (appointment.getScheduleId() != null) {
            Schedule schedule = scheduleMapper.selectById(appointment.getScheduleId());
            if (schedule != null) {
                vo.setAppointmentDate(schedule.getScheduleDate().toString());
                vo.setDoctorId(schedule.getDoctorId());
                
                // 查询时间段信息
                if (schedule.getTimeSlotId() != null) {
                    TimeSlot timeSlot = timeSlotMapper.selectById(schedule.getTimeSlotId());
                    if (timeSlot != null) {
                        vo.setTimeSlotName(timeSlot.getName());
                    }
                }
                
                // 查询医生信息
                if (schedule.getDoctorId() != null) {
                    Doctor doctor = doctorMapper.selectById(schedule.getDoctorId());
                    if (doctor != null) {
                        vo.setDoctorName(doctor.getName());
                        vo.setDepartmentId(doctor.getDepartmentId());
                        
                        // 查询科室信息
                        if (doctor.getDepartmentId() != null) {
                            Department department = departmentMapper.selectById(doctor.getDepartmentId());
                            if (department != null) {
                                vo.setDepartmentName(department.getName());
                            }
                        }
                    }
                }
            }
        }
        
        return vo;
    }
} 