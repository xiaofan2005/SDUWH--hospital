package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Schedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医生排班的持久层接口
 * @author Administrator
 */
@Mapper
public interface ScheduleMapper extends BaseMapper<Schedule> {
} 