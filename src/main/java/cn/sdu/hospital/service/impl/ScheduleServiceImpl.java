package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.ScheduleMapper;
import cn.sdu.hospital.mapper.DoctorMapper;
import cn.sdu.hospital.mapper.DepartmentMapper;
import cn.sdu.hospital.mapper.TimeSlotMapper;
import cn.sdu.hospital.mapper.ClinicRoomMapper;
import cn.sdu.hospital.pojo.Schedule;
import cn.sdu.hospital.pojo.ScheduleVO;
import cn.sdu.hospital.pojo.Doctor;
import cn.sdu.hospital.pojo.Department;
import cn.sdu.hospital.pojo.TimeSlot;
import cn.sdu.hospital.pojo.ClinicRoom;
import cn.sdu.hospital.service.ScheduleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * 排班业务层实现类
 * @author Administrator
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    
    @Autowired
    private ScheduleMapper scheduleMapper;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Autowired
    private ClinicRoomMapper clinicRoomMapper;
    
    @Override
    public List<Schedule> selectAll() {
        return scheduleMapper.selectList(null);
    }
    
    @Override
    public List<Schedule> selectByDoctorId(Integer doctorId) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", doctorId);
        return scheduleMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<ScheduleVO> selectScheduleVOByDoctorId(Integer doctorId) {
        List<Schedule> schedules = selectByDoctorId(doctorId);
        List<ScheduleVO> voList = new ArrayList<>();
        
        for (Schedule schedule : schedules) {
            ScheduleVO vo = convertToVO(schedule);
            voList.add(vo);
        }
        
        return voList;
    }
    
    @Override
    public List<Schedule> selectByDate(LocalDate date) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("schedule_date", date);
        return scheduleMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<Schedule> selectByDoctorAndDate(Integer doctorId, LocalDate date) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", doctorId).eq("schedule_date", date);
        return scheduleMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean insert(Schedule schedule) {
        return scheduleMapper.insert(schedule) > 0;
    }
    
    @Override
    public boolean update(Schedule schedule) {
        return scheduleMapper.updateById(schedule) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return scheduleMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean stopSchedule(Integer id) {
        Schedule schedule = scheduleMapper.selectById(id);
        if (schedule != null) {
            schedule.setStatus("停诊");
            return scheduleMapper.updateById(schedule) > 0;
        }
        return false;
    }
    
    @Override
    public List<Schedule> selectAvailableSchedules() {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "正常")
                   .ge("schedule_date", LocalDate.now())
                   .apply("booked_count < total_count");
        return scheduleMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<ScheduleVO> selectAvailableScheduleVOByDoctorId(Integer doctorId) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", doctorId)
                   .eq("status", "正常")
                   .ge("schedule_date", LocalDate.now())
                   .apply("booked_count < total_count");
        List<Schedule> schedules = scheduleMapper.selectList(queryWrapper);
        
        List<ScheduleVO> voList = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleVO vo = convertToVO(schedule);
            voList.add(vo);
        }
        
        return voList;
    }
    
    @Override
    public List<Schedule> selectByDepartmentId(Integer departmentId) {
        QueryWrapper<Schedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("doctor_id", "SELECT id FROM doctor WHERE department_id = " + departmentId);
        return scheduleMapper.selectList(queryWrapper);
    }
    
    @Override
    public List<ScheduleVO> selectAllScheduleVO() {
        List<Schedule> schedules = selectAll();
        List<ScheduleVO> voList = new ArrayList<>();
        
        for (Schedule schedule : schedules) {
            ScheduleVO vo = convertToVO(schedule);
            voList.add(vo);
        }
        
        return voList;
    }
    
    @Override
    public Schedule selectById(Integer id) {
        return scheduleMapper.selectById(id);
    }
    
    /**
     * 将Schedule转换为ScheduleVO
     */
    private ScheduleVO convertToVO(Schedule schedule) {
        ScheduleVO vo = new ScheduleVO();
        
        // 复制基础字段
        vo.setId(schedule.getId());
        vo.setDoctorId(schedule.getDoctorId());
        vo.setScheduleDate(schedule.getScheduleDate());
        vo.setTimeSlotId(schedule.getTimeSlotId());
        vo.setTotalCount(schedule.getTotalCount());
        vo.setBookedCount(schedule.getBookedCount());
        vo.setStatus(schedule.getStatus());
        vo.setCreateTime(schedule.getCreateTime());
        vo.setUpdateTime(schedule.getUpdateTime());
        
        // 计算剩余号源和是否可用
        vo.setAvailableCount(schedule.getTotalCount() - schedule.getBookedCount());
        vo.setAvailable(vo.getAvailableCount() > 0 && "正常".equals(schedule.getStatus()));
        
        // 查询医生信息
        if (schedule.getDoctorId() != null) {
            Doctor doctor = doctorMapper.selectById(schedule.getDoctorId());
            if (doctor != null) {
                vo.setDoctorName(doctor.getName());
                vo.setDepartmentId(doctor.getDepartmentId());
                vo.setClinicRoomId(doctor.getClinicRoomId());
                
                // 查询科室信息
                if (doctor.getDepartmentId() != null) {
                    Department department = departmentMapper.selectById(doctor.getDepartmentId());
                    if (department != null) {
                        vo.setDepartmentName(department.getName());
                    }
                }
                
                // 查询诊室信息
                if (doctor.getClinicRoomId() != null) {
                    ClinicRoom clinicRoom = clinicRoomMapper.selectById(doctor.getClinicRoomId());
                    if (clinicRoom != null) {
                        vo.setClinicRoomName(clinicRoom.getName());
                    }
                }
            }
        }
        
        // 查询时间段信息
        if (schedule.getTimeSlotId() != null) {
            TimeSlot timeSlot = timeSlotMapper.selectById(schedule.getTimeSlotId());
            if (timeSlot != null) {
                vo.setTimeSlotName(timeSlot.getName());
            }
        }
        
        return vo;
    }
} 