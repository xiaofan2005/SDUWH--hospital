package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Admin;
import cn.sdu.hospital.service.AdminService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 管理员控制类
 * @author Administrator
 * @RestController 声明这是一个控制器类 同时该控制器所有的方法全部以JSON格式返回数据
 */
@RestController
@Tag(name = "管理员管理接口")
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "管理员登录")
    public ServerResult<Admin> login(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            return ServerResult.ok(admin);
        } else {
            return ServerResult.error(401, "用户名或密码错误");
        }
    }
    
    /**
     * 查询所有管理员
     * @return 管理员列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有管理员")
    public ServerResult<List<Admin>> selectAll() {
        List<Admin> list = adminService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 管理员信息
     */
    @GetMapping("/selectById")
    @Operation(summary = "根据ID查询管理员")
    public ServerResult<Admin> selectById(@RequestParam Integer id) {
        Admin admin = adminService.selectById(id);
        if (admin != null) {
            // 不返回密码信息
            admin.setPassword(null);
            return ServerResult.ok(admin);
        } else {
            return ServerResult.error(404, "管理员不存在");
        }
    }
    
    /**
     * 新增管理员
     * @param admin 管理员信息
     * @return 操作结果
     */
    @PostMapping("/insert")
    @Operation(summary = "新增管理员")
    public ServerResult<Void> insert(@RequestBody Admin admin) {
        boolean result = adminService.insert(admin);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "新增管理员失败");
        }
    }
    
    /**
     * 更新管理员信息
     * @param admin 管理员信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新管理员信息")
    public ServerResult<Void> update(@RequestBody Admin admin) {
        boolean result = adminService.update(admin);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "更新管理员信息失败");
        }
    }
    
    /**
     * 删除管理员
     * @param id 管理员ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除管理员")
    public ServerResult<Void> deleteById(@RequestParam Integer id) {
        boolean result = adminService.deleteById(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "删除管理员失败");
        }
    }
    
    /**
     * 修改密码
     * @param id 管理员ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    @PutMapping("/changePassword")
    @Operation(summary = "修改管理员密码")
    public ServerResult<Void> changePassword(@RequestParam Integer id, 
                                            @RequestParam String oldPassword, 
                                            @RequestParam String newPassword) {
        boolean result = adminService.changePassword(id, oldPassword, newPassword);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "修改密码失败，请检查原密码是否正确");
        }
    }
} 