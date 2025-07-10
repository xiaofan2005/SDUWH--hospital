package cn.sdu.hospital.service.impl;

import cn.sdu.hospital.mapper.AdminMapper;
import cn.sdu.hospital.pojo.Admin;
import cn.sdu.hospital.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 管理员业务层实现类
 * @author Administrator
 * @Service 声明为业务层组件
 */
@Service
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Override
    public Admin login(String username, String password) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);
        return adminMapper.selectOne(queryWrapper);
    }
    
    @Override
    public List<Admin> selectAll() {
        return adminMapper.selectList(null);
    }
    
    @Override
    public Admin selectById(Integer id) {
        return adminMapper.selectById(id);
    }
    
    @Override
    public boolean insert(Admin admin) {
        return adminMapper.insert(admin) > 0;
    }
    
    @Override
    public boolean update(Admin admin) {
        return adminMapper.updateById(admin) > 0;
    }
    
    @Override
    public boolean deleteById(Integer id) {
        return adminMapper.deleteById(id) > 0;
    }
    
    @Override
    public boolean changePassword(Integer id, String oldPassword, String newPassword) {
        Admin admin = adminMapper.selectById(id);
        if (admin != null && admin.getPassword().equals(oldPassword)) {
            admin.setPassword(newPassword);
            return adminMapper.updateById(admin) > 0;
        }
        return false;
    }
} 