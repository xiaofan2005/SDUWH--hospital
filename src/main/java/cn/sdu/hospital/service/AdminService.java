package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Admin;
import java.util.List;

/**
 * 管理员业务层接口
 * @author Administrator
 */
public interface AdminService {
    
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 管理员信息
     */
    Admin login(String username, String password);
    
    /**
     * 查询所有管理员
     * @return 管理员列表
     */
    List<Admin> selectAll();
    
    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 管理员信息
     */
    Admin selectById(Integer id);
    
    /**
     * 新增管理员
     * @param admin 管理员信息
     * @return 操作结果
     */
    boolean insert(Admin admin);
    
    /**
     * 更新管理员信息
     * @param admin 管理员信息
     * @return 操作结果
     */
    boolean update(Admin admin);
    
    /**
     * 删除管理员
     * @param id 管理员ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
    
    /**
     * 修改密码
     * @param id 管理员ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    boolean changePassword(Integer id, String oldPassword, String newPassword);
} 