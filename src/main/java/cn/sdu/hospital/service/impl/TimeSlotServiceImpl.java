package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.TimeSlotMapper;
import cn.sdu.hospital.pojo.TimeSlot;
import cn.sdu.hospital.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 预约时段业务层实现类
 * @author Administrator
 */
@Service
public class TimeSlotServiceImpl implements TimeSlotService {
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Override
    public List<TimeSlot> selectAll() {
        return timeSlotMapper.selectList(null);
    }
    
    @Override
    public TimeSlot selectById(Integer id) {
        return timeSlotMapper.selectById(id);
    }
    
    @Override
    public boolean insert(TimeSlot timeSlot) {
        return timeSlotMapper.insert(timeSlot) > 0;
    }
    
    @Override
    public boolean update(TimeSlot timeSlot) {
        return timeSlotMapper.updateById(timeSlot) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return timeSlotMapper.deleteById(id) > 0;
    }
} 