package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Doctor;
import cn.sdu.hospital.service.DoctorService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 医生控制类
 * @author Administrator
 */
@RestController
@Tag(name = "医生管理接口")
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;
    
    /**
     * 查询所有医生
     * @return 医生列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有医生")
    public ServerResult<List<Doctor>> selectAll() {
        List<Doctor> list = doctorService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 新增医生
     * @param doctor 医生信息
     * @return 操作结果
     */
    @PostMapping("/insert")
    @Operation(summary = "新增医生")
    public ServerResult<Void> insert(@RequestBody Doctor doctor) {
        boolean result = doctorService.insert(doctor);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "新增医生失败");
        }
    }
    
    /**
     * 更新医生信息
     * @param doctor 医生信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新医生信息")
    public ServerResult<Void> update(@RequestBody Doctor doctor) {
        boolean result = doctorService.update(doctor);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "更新医生信息失败");
        }
    }
    
    /**
     * 删除医生
     * @param id 医生ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除医生")
    public ServerResult<Void> deleteById(@RequestParam Integer id) {
        boolean result = doctorService.deleteById(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "删除医生失败");
        }
    }
    
    /**
     * 修改医生密码
     * @param id 医生ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 操作结果
     */
    @PutMapping("/changePassword")
    @Operation(summary = "修改医生密码")
    public ServerResult<Void> changePassword(@RequestParam Integer id, 
                                            @RequestParam String oldPassword, 
                                            @RequestParam String newPassword) {
        boolean result = doctorService.changePassword(id, oldPassword, newPassword);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "修改密码失败，请检查原密码是否正确");
        }
    }
    
    /**
     * 根据医生姓名模糊查询
     * @param name 医生姓名
     * @return 医生列表
     */
    @GetMapping("/selectByNameLike")
    @Operation(summary = "根据医生姓名模糊查询")
    public ServerResult<List<Doctor>> selectByNameLike(@RequestParam String name) {
        List<Doctor> list = doctorService.selectByNameLike(name);
        return ServerResult.ok(list);
    }
    
    /**
     * 医生登录（RESTful版本）
     * @param loginData 登录数据
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "医生登录RESTful版本")
    public ServerResult<Doctor> loginRestful(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        Doctor doctor = doctorService.login(username, password);
        if (doctor != null) {
            // 不返回密码信息
            doctor.setPassword(null);
            return ServerResult.ok(doctor);
        } else {
            return ServerResult.error(401, "用户名或密码错误");
        }
    }
    
    /**
     * 根据ID查询医生（RESTful版本）
     * @param id 医生ID
     * @return 医生信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询医生RESTful版本")
    public ServerResult<Doctor> getById(@PathVariable Integer id) {
        Doctor doctor = doctorService.selectById(id);
        if (doctor != null) {
            // 不返回密码信息
            doctor.setPassword(null);
            return ServerResult.ok(doctor);
        } else {
            return ServerResult.error(404, "医生不存在");
        }
    }
    
    /**
     * 根据科室ID查询医生（RESTful版本）
     * @param departmentId 科室ID
     * @return 医生列表
     */
    @GetMapping("/department/{departmentId}")
    @Operation(summary = "根据科室ID查询医生RESTful版本")
    public ServerResult<List<Doctor>> getByDepartmentId(@PathVariable Integer departmentId) {
        List<Doctor> list = doctorService.selectByDepartmentId(departmentId);
        // 不返回密码信息
        list.forEach(doctor -> doctor.setPassword(null));
        return ServerResult.ok(list);
    }
} 