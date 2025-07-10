package cn.sdu.hospital.service;

import cn.sdu.hospital.pojo.ClinicRoom;
import java.util.List;

/**
 * 诊室业务层接口
 * @author Administrator
 */
public interface ClinicRoomService {
    
    /**
     * 查询所有诊室
     * @return 诊室列表
     */
    List<ClinicRoom> selectAll();
    
    /**
     * 根据ID查询诊室
     * @param id 诊室ID
     * @return 诊室信息
     */
    ClinicRoom selectById(Integer id);
    
    /**
     * 根据科室ID查询诊室
     * @param departmentId 科室ID
     * @return 诊室列表
     */
    List<ClinicRoom> selectByDepartmentId(Integer departmentId);
    
    /**
     * 新增诊室
     * @param clinicRoom 诊室信息
     * @return 操作结果
     */
    boolean insert(ClinicRoom clinicRoom);
    
    /**
     * 更新诊室信息
     * @param clinicRoom 诊室信息
     * @return 操作结果
     */
    boolean update(ClinicRoom clinicRoom);
    
    /**
     * 删除诊室
     * @param id 诊室ID
     * @return 操作结果
     */
    boolean deleteById(Integer id);
} 