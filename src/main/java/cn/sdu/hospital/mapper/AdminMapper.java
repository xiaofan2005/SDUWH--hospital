package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员的持久层接口
 * @author Administrator
 * @Mapper 声明这是一个持久层
 * BaseMapper<Admin> -- MyBatis plus提供的一个接口，负责操作数据库
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
} 