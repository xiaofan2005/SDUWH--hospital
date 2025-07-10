package cn.sdu.hospital.pojo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预约视图对象
 * 包含预约的详细信息，用于前端展示
 * @author Administrator
 */
@Data
public class AppointmentVO {
    /**
     * 预约ID
     */
    private Integer id;
    
    /**
     * 患者ID
     */
    private Integer patientId;
    
    /**
     * 患者姓名
     */
    private String patientName;
    
    /**
     * 患者电话
     */
    private String patientPhone;
    
    /**
     * 排班ID
     */
    private Integer scheduleId;
    
    /**
     * 预约号码
     */
    private Integer appointmentNumber;
    
    /**
     * 预约状态
     */
    private String status;
    
    /**
     * 症状描述
     */
    private String symptoms;
    
    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 预约日期（来自排班）
     */
    private String appointmentDate;
    
    /**
     * 时间段名称
     */
    private String timeSlotName;
    
    /**
     * 医生ID
     */
    private Integer doctorId;
    
    /**
     * 医生姓名
     */
    private String doctorName;
    
    /**
     * 科室ID
     */
    private Integer departmentId;
    
    /**
     * 科室名称
     */
    private String departmentName;
    
    /**
     * 诊室名称
     */
    private String clinicRoomName;
} 