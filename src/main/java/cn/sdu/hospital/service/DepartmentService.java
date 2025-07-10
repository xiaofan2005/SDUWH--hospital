package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Department;
import java.util.List;

/**
 * 科室业务层接口
 * @author Administrator
 */
public interface DepartmentService {
    
    /**
     * 查询所有科室
     * @return 科室列表
     */
    List<Department> selectAll();
    
    /**
     * 根据ID查询科室
     * @param id 科室ID
     * @return 科室信息
     */
    Department selectById(Integer id);
    
    /**
     * 新增科室
     * @param department 科室信息
     * @return 操作结果
     */
    boolean insert(Department department);
    
    /**
     * 更新科室信息
     * @param department 科室信息
     * @return 操作结果
     */
    boolean update(Department department);
    
    /**
     * 删除科室
     * @param id 科室ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
    
    /**
     * 根据科室名称模糊查询
     * @param name 科室名称
     * @return 科室列表
     */
    List<Department> selectByNameLike(String name);
} 