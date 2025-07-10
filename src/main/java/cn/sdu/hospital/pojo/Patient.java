package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 患者实体类
 * 作用：封装患者信息 对应数据库表patient
 * @author Administrator
 */
@Data
@TableName("patient")
public class Patient {
    /**
     * 患者ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
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
     * 身份证号
     */
    @TableField("id_card")
    private String idCard;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 病史
     */
    @TableField("medical_history")
    private String medicalHistory;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 