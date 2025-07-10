package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.TimeSlot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约时段的持久层接口
 * @author Administrator
 */
@Mapper
public interface TimeSlotMapper extends BaseMapper<TimeSlot> {
} 