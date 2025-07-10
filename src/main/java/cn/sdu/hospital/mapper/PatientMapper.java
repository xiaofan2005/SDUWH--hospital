package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Patient;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 患者的持久层接口
 * @author Administrator
 */
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
} 