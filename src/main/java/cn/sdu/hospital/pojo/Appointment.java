package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 预约实体类
 * 作用：封装预约信息 对应数据库表appointment
 * @author Administrator
 */
@Data
@TableName("appointment")
public class Appointment {
    /**
     * 预约ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 患者ID
     */
    @TableField("patient_id")
    private Integer patientId;
    
    /**
     * 排班ID
     */
    @TableField("schedule_id")
    private Integer scheduleId;
    
    /**
     * 预约号码
     */
    @TableField("appointment_number")
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
    @TableField("appointment_time")
    private LocalDateTime appointmentTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 