package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Appointment;
import cn.sdu.hospital.pojo.AppointmentVO;
import java.util.List;

/**
 * 预约业务层接口
 * @author Administrator
 */
public interface AppointmentService {
    /**
     * 挂号预约
     * @param appointment 预约信息
     * @return 预约结果
     */
    boolean makeAppointment(Appointment appointment);
    
    /**
     * 取消预约
     * @param id 预约ID
     * @return 取消结果
     */
    boolean cancelAppointment(Integer id);
    
    /**
     * 修改预约
     * @param appointment 预约信息
     * @return 修改结果
     */
    boolean updateAppointment(Appointment appointment);
    
    /**
     * 根据患者ID查询预约
     * @param patientId 患者ID
     * @return 预约列表
     */
    List<Appointment> selectByPatientId(Integer patientId);
    
    /**
     * 根据患者ID查询预约详情（包含关联信息）
     * @param patientId 患者ID
     * @return 预约详情列表
     */
    List<AppointmentVO> selectAppointmentVOByPatientId(Integer patientId);
    
    /**
     * 根据排班ID查询预约队列
     * @param scheduleId 排班ID
     * @return 预约列表
     */
    List<Appointment> selectByScheduleId(Integer scheduleId);
    
    /**
     * 根据医生ID查询预约详情列表
     * @param doctorId 医生ID
     * @return 预约详情列表
     */
    List<AppointmentVO> selectAppointmentVOByDoctorId(Integer doctorId);
    
    /**
     * 根据医生ID和日期查询预约详情
     * @param doctorId 医生ID
     * @param date 日期
     * @return 预约详情列表
     */
    List<AppointmentVO> selectAppointmentVOByDoctorIdAndDate(Integer doctorId, String date);
    
    /**
     * 查询所有预约
     * @return 预约列表
     */
    List<Appointment> selectAll();
    
    /**
     * 查询所有预约详情
     * @return 预约详情列表
     */
    List<AppointmentVO> selectAllAppointmentVO();
    
    /**
     * 根据ID查询预约VO
     * @param id 预约ID
     * @return 预约详情
     */
    AppointmentVO selectAppointmentVOById(Integer id);
    
    /**
     * 修改预约排班
     * @param appointmentId 预约ID
     * @param newScheduleId 新排班ID
     * @param symptoms 症状描述
     * @return 是否成功
     */
    boolean modifyAppointmentSchedule(Integer appointmentId, Integer newScheduleId, String symptoms);
    
    /**
     * 根据医生ID获取今日预约数量
     * @param doctorId 医生ID
     * @return 今日预约数量
     */
    int getTodayAppointmentCount(Integer doctorId);
    
    /**
     * 根据医生ID获取本周预约数量
     * @param doctorId 医生ID
     * @return 本周预约数量
     */
    int getWeekAppointmentCount(Integer doctorId);
    
    /**
     * 根据医生ID获取本月预约数量
     * @param doctorId 医生ID
     * @return 本月预约数量
     */
    int getMonthAppointmentCount(Integer doctorId);
    
    /**
     * 根据医生ID获取累计患者数量（去重）
     * @param doctorId 医生ID
     * @return 累计患者数量
     */
    int getTotalPatientCount(Integer doctorId);
    
    /**
     * 获取系统总体统计数据
     * @return 统计数据Map
     */
    java.util.Map<String, Object> getSystemStatistics();
    
    /**
     * 根据科室获取预约统计
     * @param departmentId 科室ID
     * @return 统计数据
     */
    java.util.Map<String, Object> getDepartmentStatistics(Integer departmentId);
    
    /**
     * 获取预约状态分布统计
     * @return 状态分布数据
     */
    java.util.Map<String, Integer> getAppointmentStatusStatistics();
    
    /**
     * 根据ID查询预约
     * @param id 预约ID
     * @return 预约信息
     */
    Appointment selectById(Integer id);
    
    /**
     * 完成预约
     * @param id 预约ID
     * @return 操作结果
     */
    boolean completeAppointment(Integer id);
} 