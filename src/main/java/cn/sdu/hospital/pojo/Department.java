package cn.sdu.hospital.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 科室实体类
 * 作用：封装科室信息 对应数据库表department
 * @author Administrator
 */
@Data
@TableName("department")
public class Department {
    /**
     * 科室ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 科室名称
     */
    private String name;
    
    /**
     * 科室描述
     */
    private String description;
    
    /**
     * 科室位置
     */
    private String location;
    
    /**
     * 科室电话
     */
    private String phone;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 