package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.TimeSlot;
import java.util.List;

/**
 * 预约时段业务层接口
 * @author Administrator
 */
public interface TimeSlotService {
    
    /**
     * 查询所有预约时段
     * @return 时段列表
     */
    List<TimeSlot> selectAll();
    
    /**
     * 根据ID查询预约时段
     * @param id 时段ID
     * @return 时段信息
     */
    TimeSlot selectById(Integer id);
    
    /**
     * 新增预约时段
     * @param timeSlot 时段信息
     * @return 操作结果
     */
    boolean insert(TimeSlot timeSlot);
    
    /**
     * 更新预约时段信息
     * @param timeSlot 时段信息
     * @return 操作结果
     */
    boolean update(TimeSlot timeSlot);
    
    /**
     * 删除预约时段
     * @param id 时段ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
} 