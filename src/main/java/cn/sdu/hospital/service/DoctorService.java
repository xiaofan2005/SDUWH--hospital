package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Doctor;
import java.util.List;

/**
 * 医生业务层接口
 * @author Administrator
 */
public interface DoctorService {
    
    /**
     * 医生登录
     * @param username 用户名
     * @param password 密码
     * @return 医生信息
     */
    Doctor login(String username, String password);
    
    /**
     * 查询所有医生
     * @return 医生列表
     */
    List<Doctor> selectAll();
    
    /**
     * 根据ID查询医生
     * @param id 医生ID
     * @return 医生信息
     */
    Doctor selectById(Integer id);
    
    /**
     * 根据科室ID查询医生
     * @param departmentId 科室ID
     * @return 医生列表
     */
    List<Doctor> selectByDepartmentId(Integer departmentId);
    
    /**
     * 新增医生
     * @param doctor 医生信息
     * @return 操作结果
     */
    boolean insert(Doctor doctor);
    
    /**
     * 更新医生信息
     * @param doctor 医生信息
     * @return 操作结果
     */
    boolean update(Doctor doctor);
    
    /**
     * 删除医生
     * @param id 医生ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
    
    /**
     * 修改医生密码
     * @param id 医生ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    boolean changePassword(Integer id, String oldPassword, String newPassword);
    
    /**
     * 根据医生姓名模糊查询
     * @param name 医生姓名
     * @return 医生列表
     */
    List<Doctor> selectByNameLike(String name);
} 