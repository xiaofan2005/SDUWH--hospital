package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 医生排班实体类
 * 作用：封装医生排班信息 对应数据库表schedule
 * @author Administrator
 */
@Data
@TableName("schedule")
public class Schedule {
    /**
     * 排班ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 医生ID
     */
    @TableField("doctor_id")
    private Integer doctorId;
    
    /**
     * 排班日期
     */
    @TableField("schedule_date")
    private LocalDate scheduleDate;
    
    /**
     * 时段ID
     */
    @TableField("time_slot_id")
    private Integer timeSlotId;
    
    /**
     * 总号源数
     */
    @TableField("total_count")
    private Integer totalCount;
    
    /**
     * 已预约数
     */
    @TableField("booked_count")
    private Integer bookedCount;
    
    /**
     * 排班状态
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