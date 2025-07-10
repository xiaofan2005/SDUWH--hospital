package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 医生的持久层接口
 * @author Administrator
 */
@Mapper
public interface DoctorMapper extends BaseMapper<Doctor> {
} 