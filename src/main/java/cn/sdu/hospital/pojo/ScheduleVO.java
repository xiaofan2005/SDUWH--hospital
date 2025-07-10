package cn.sdu.hospital.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 排班视图对象
 * 包含排班的详细信息，用于前端展示
 * @author Administrator
 */
@Data
public class ScheduleVO {
    /**
     * 排班ID
     */
    private Integer id;
    
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
     * 诊室ID
     */
    private Integer clinicRoomId;
    
    /**
     * 诊室名称
     */
    private String clinicRoomName;
    
    /**
     * 排班日期
     */
    private LocalDate scheduleDate;
    
    /**
     * 时段ID
     */
    private Integer timeSlotId;
    
    /**
     * 时段名称
     */
    private String timeSlotName;
    
    /**
     * 总号源数
     */
    private Integer totalCount;
    
    /**
     * 已预约数
     */
    private Integer bookedCount;
    
    /**
     * 剩余号源数
     */
    private Integer availableCount;
    
    /**
     * 排班状态
     */
    private String status;
    
    /**
     * 是否可用
     */
    private Boolean available;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 