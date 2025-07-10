package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 科室的持久层接口
 * @author Administrator
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
} 