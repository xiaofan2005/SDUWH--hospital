package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.DepartmentMapper;
import cn.sdu.hospital.pojo.Department;
import cn.sdu.hospital.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 科室业务层实现类
 * @author Administrator
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    @Autowired
    private DepartmentMapper departmentMapper;
    
    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectList(null);
    }
    
    @Override
    public Department selectById(Integer id) {
        return departmentMapper.selectById(id);
    }
    
    @Override
    public boolean insert(Department department) {
        return departmentMapper.insert(department) > 0;
    }
    
    @Override
    public boolean update(Department department) {
        return departmentMapper.updateById(department) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return departmentMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<Department> selectByNameLike(String name) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return departmentMapper.selectList(queryWrapper);
    }
} 