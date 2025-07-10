package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 医生实体类
 * 作用：封装医生信息 对应数据库表doctor
 * @author Administrator
 */
@Data
@TableName("doctor")
public class Doctor {
    /**
     * 医生ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 所属科室ID
     */
    @TableField("department_id")
    private Integer departmentId;
    
    /**
     * 所属诊室ID
     */
    @TableField("clinic_room_id")
    private Integer clinicRoomId;
    
    /**
     * 职称
     */
    private String title;
    
    /**
     * 专长
     */
    private String specialty;
    
    /**
     * 简介
     */
    private String introduction;
    
    /**
     * 挂号费
     */
    @TableField("registration_fee")
    private BigDecimal registrationFee;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 