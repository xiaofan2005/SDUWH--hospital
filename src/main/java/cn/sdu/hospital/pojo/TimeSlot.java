package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 预约时段实体类
 * 作用：封装预约时段信息 对应数据库表time_slot
 * @author Administrator
 */
@Data
@TableName("time_slot")
public class TimeSlot {
    /**
     * 时段ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 时段名称
     */
    private String name;
    
    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalTime startTime;
    
    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalTime endTime;
    
    /**
     * 时段描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
} 