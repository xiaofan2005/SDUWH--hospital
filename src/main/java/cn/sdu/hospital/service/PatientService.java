package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.Patient;
import java.util.List;

/**
 * 患者业务层接口
 * @author Administrator
 */
public interface PatientService {
    
    /**
     * 查询所有患者
     * @return 患者列表
     */
    List<Patient> selectAll();
    
    /**
     * 根据ID查询患者
     * @param id 患者ID
     * @return 患者信息
     */
    Patient selectById(Integer id);
    
    /**
     * 新增患者
     * @param patient 患者信息
     * @return 操作结果
     */
    boolean insert(Patient patient);
    
    /**
     * 更新患者信息
     * @param patient 患者信息
     * @return 操作结果
     */
    boolean update(Patient patient);
    
    /**
     * 删除患者
     * @param id 患者ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
    
    /**
     * 根据姓名模糊查询患者
     * @param name 患者姓名
     * @return 患者列表
     */
    List<Patient> selectByNameLike(String name);
    
    /**
     * 根据电话查询患者
     * @param phone 电话号码
     * @return 患者信息
     */
    Patient selectByPhone(String phone);
    
    /**
     * 根据身份证号查询患者
     * @param idCard 身份证号
     * @return 患者信息
     */
    Patient selectByIdCard(String idCard);
} 