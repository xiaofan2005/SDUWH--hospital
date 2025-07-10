package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.DoctorMapper;
import cn.sdu.hospital.pojo.Doctor;
import cn.sdu.hospital.service.DoctorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 医生业务层实现类
 * @author Administrator
 */
@Service
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Override
    public Doctor login(String username, String password) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        return doctorMapper.selectOne(queryWrapper);
    }
    
    @Override
    public List<Doctor> selectAll() {
        return doctorMapper.selectList(null);
    }
    
    @Override
    public Doctor selectById(Integer id) {
        return doctorMapper.selectById(id);
    }
    
    @Override
    public List<Doctor> selectByDepartmentId(Integer departmentId) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id", departmentId);
        return doctorMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean insert(Doctor doctor) {
        return doctorMapper.insert(doctor) > 0;
    }
    
    @Override
    public boolean update(Doctor doctor) {
        return doctorMapper.updateById(doctor) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return doctorMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean changePassword(Integer id, String oldPassword, String newPassword) {
        Doctor doctor = doctorMapper.selectById(id);
        if (doctor != null && doctor.getPassword().equals(oldPassword)) {
            doctor.setPassword(newPassword);
            return doctorMapper.updateById(doctor) > 0;
        }
        return false;
    }
    
    @Override
    public List<Doctor> selectByNameLike(String name) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return doctorMapper.selectList(queryWrapper);
    }
} 