package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.ClinicRoomMapper;
import cn.sdu.hospital.pojo.ClinicRoom;
import cn.sdu.hospital.service.ClinicRoomService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 诊室业务层实现类
 * @author Administrator
 */
@Service
public class ClinicRoomServiceImpl implements ClinicRoomService {
    
    @Autowired
    private ClinicRoomMapper clinicRoomMapper;
    
    @Override
    public List<ClinicRoom> selectAll() {
        return clinicRoomMapper.selectList(null);
    }
    
    @Override
    public ClinicRoom selectById(Integer id) {
        return clinicRoomMapper.selectById(id);
    }
    
    @Override
    public List<ClinicRoom> selectByDepartmentId(Integer departmentId) {
        QueryWrapper<ClinicRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", departmentId);
        return clinicRoomMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean insert(ClinicRoom clinicRoom) {
        return clinicRoomMapper.insert(clinicRoom) > 0;
    }
    
    @Override
    public boolean update(ClinicRoom clinicRoom) {
        return clinicRoomMapper.updateById(clinicRoom) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return clinicRoomMapper.deleteById(id) > 0;
    }
} 