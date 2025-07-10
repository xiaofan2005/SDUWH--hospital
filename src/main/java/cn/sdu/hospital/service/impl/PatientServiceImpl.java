package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.PatientMapper;
import cn.sdu.hospital.pojo.Patient;
import cn.sdu.hospital.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 患者业务层实现类
 * @author Administrator
 */
@Service
public class PatientServiceImpl implements PatientService {
    
    @Autowired
    private PatientMapper patientMapper;
    
    @Override
    public List<Patient> selectAll() {
        return patientMapper.selectList(null);
    }
    
    @Override
    public Patient selectById(Integer id) {
        return patientMapper.selectById(id);
    }
    
    @Override
    public boolean insert(Patient patient) {
        return patientMapper.insert(patient) > 0;
    }
    
    @Override
    public boolean update(Patient patient) {
        return patientMapper.updateById(patient) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return patientMapper.deleteById(id) > 0;
    }
    
    @Override
    public List<Patient> selectByNameLike(String name) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return patientMapper.selectList(queryWrapper);
    }
    
    @Override
    public Patient selectByPhone(String phone) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        return patientMapper.selectOne(queryWrapper);
    }
    
    @Override
    public Patient selectByIdCard(String idCard) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_card", idCard);
        return patientMapper.selectOne(queryWrapper);
    }
} 