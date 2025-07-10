package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 诊室实体类
 * 作用：封装诊室信息 对应数据库表clinic_room
 * @author Administrator
 */
@Data
@TableName("clinic_room")
public class ClinicRoom {
    /**
     * 诊室ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 诊室名称
     */
    private String name;
    
    /**
     * 所属科室ID
     */
    @TableField("department_id")
    private Integer departmentId;
    
    /**
     * 诊室位置
     */
    private String location;
    
    /**
     * 容纳人数
     */
    private Integer capacity;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 