package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Schedule;
import cn.sdu.hospital.pojo.ScheduleVO;
import java.time.LocalDate;
import java.util.List;

/**
 * 排班业务层接口
 * @author Administrator
 */
public interface ScheduleService {
    /**
     * 查询所有排班
     * @return 排班列表
     */
    List<Schedule> selectAll();
    
    /**
     * 根据医生ID查询排班
     * @param doctorId 医生ID
     * @return 排班列表
     */
    List<Schedule> selectByDoctorId(Integer doctorId);
    
    /**
     * 根据医生ID查询排班详情
     * @param doctorId 医生ID
     * @return 排班详情列表
     */
    List<ScheduleVO> selectScheduleVOByDoctorId(Integer doctorId);
    
    /**
     * 根据日期查询排班
     * @param date 日期
     * @return 排班列表
     */
    List<Schedule> selectByDate(LocalDate date);
    
    /**
     * 根据医生ID和日期查询排班
     * @param doctorId 医生ID
     * @param date 日期
     * @return 排班列表
     */
    List<Schedule> selectByDoctorAndDate(Integer doctorId, LocalDate date);
    
    /**
     * 新增排班
     * @param schedule 排班信息
     * @return 新增结果
     */
    boolean insert(Schedule schedule);
    
    /**
     * 修改排班
     * @param schedule 排班信息
     * @return 修改结果
     */
    boolean update(Schedule schedule);
    
    /**
     * 根据ID删除排班
     * @param id 排班ID
     * @return 删除结果
     */
    boolean deleteById(Integer id);
    
    /**
     * 停诊
     * @param id 排班ID
     * @return 操作结果
     */
    boolean stopSchedule(Integer id);
    
    /**
     * 查询可用排班
     * @return 可用排班列表
     */
    List<Schedule> selectAvailableSchedules();
    
    /**
     * 根据医生ID查询可用排班详情
     * @param doctorId 医生ID
     * @return 可用排班详情列表
     */
    List<ScheduleVO> selectAvailableScheduleVOByDoctorId(Integer doctorId);
    
    /**
     * 根据科室ID查询排班
     * @param departmentId 科室ID
     * @return 排班列表
     */
    List<Schedule> selectByDepartmentId(Integer departmentId);
    
    /**
     * 查询所有排班详情
     * @return 排班详情列表
     */
    List<ScheduleVO> selectAllScheduleVO();
    
    /**
     * 根据ID查询排班
     * @param id 排班ID
     * @return 排班信息
     */
    Schedule selectById(Integer id);
} 