package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Patient;
import cn.sdu.hospital.service.PatientService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 患者控制类
 * @author Administrator
 */
@RestController
@Tag(name = "患者管理接口")
@RequestMapping("/patient")
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    /**
     * 患者登录
     * @param loginData 登录数据（包含phone和password）
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "患者登录")
    public ServerResult<Patient> login(@RequestBody Map<String, String> loginData) {
        String phone = loginData.get("phone");
        String password = loginData.get("password");
        
        if (phone == null || password == null) {
            return ServerResult.error(400, "手机号和密码不能为空");
        }
        
        Patient patient = patientService.selectByPhone(phone);
        if (patient == null) {
            return ServerResult.error(401, "用户不存在");
        }
        
        if (!password.equals(patient.getPassword())) {
            return ServerResult.error(401, "密码错误");
        }
        
        // 不返回密码信息
        patient.setPassword(null);
        return ServerResult.ok(patient);
    }
    
    /**
     * 患者注册
     * @param patient 患者信息
     * @return 注册结果
     */
    @PostMapping
    @Operation(summary = "患者注册")
    public ServerResult<Void> register(@RequestBody Patient patient) {
        // 检查手机号是否已存在
        Patient existingPatient = patientService.selectByPhone(patient.getPhone());
        if (existingPatient != null) {
            return ServerResult.error(400, "该手机号已被注册");
        }
        
        // 检查身份证号是否已存在
        existingPatient = patientService.selectByIdCard(patient.getIdCard());
        if (existingPatient != null) {
            return ServerResult.error(400, "该身份证号已被注册");
        }
        
        boolean result = patientService.insert(patient);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "注册失败");
        }
    }
    
    /**
     * 根据ID查询患者（用于前端获取个人信息）
     * @param id 患者ID
     * @return 患者信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询患者")
    public ServerResult<Patient> getById(@PathVariable Integer id) {
        Patient patient = patientService.selectById(id);
        if (patient != null) {
            // 不返回密码信息
            patient.setPassword(null);
            return ServerResult.ok(patient);
        } else {
            return ServerResult.error(404, "患者不存在");
        }
    }
    
    /**
     * 查询所有患者
     * @return 患者列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有患者")
    public ServerResult<List<Patient>> selectAll() {
        List<Patient> list = patientService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 更新患者信息
     * @param patient 患者信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新患者信息")
    public ServerResult<Void> update(@RequestBody Patient patient) {
        boolean result = patientService.update(patient);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "更新患者信息失败");
        }
    }
    
    /**
     * 删除患者
     * @param id 患者ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除患者")
    public ServerResult<Void> deleteById(@RequestParam Integer id) {
        boolean result = patientService.deleteById(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "删除患者失败");
        }
    }
    
    /**
     * 根据姓名模糊查询患者
     * @param name 患者姓名
     * @return 患者列表
     */
    @GetMapping("/selectByNameLike")
    @Operation(summary = "根据姓名模糊查询患者")
    public ServerResult<List<Patient>> selectByNameLike(@RequestParam String name) {
        List<Patient> list = patientService.selectByNameLike(name);
        return ServerResult.ok(list);
    }
    
    /**
     * 根据电话查询患者
     * @param phone 电话号码
     * @return 患者信息
     */
    @GetMapping("/selectByPhone")
    @Operation(summary = "根据电话查询患者")
    public ServerResult<Patient> selectByPhone(@RequestParam String phone) {
        Patient patient = patientService.selectByPhone(phone);
        if (patient != null) {
            // 不返回密码信息
            patient.setPassword(null);
            return ServerResult.ok(patient);
        } else {
            return ServerResult.error(404, "患者不存在");
        }
    }
    
    /**
     * 根据身份证号查询患者
     * @param idCard 身份证号
     * @return 患者信息
     */
    @GetMapping("/selectByIdCard")
    @Operation(summary = "根据身份证号查询患者")
    public ServerResult<Patient> selectByIdCard(@RequestParam String idCard) {
        Patient patient = patientService.selectByIdCard(idCard);
        if (patient != null) {
            // 不返回密码信息
            patient.setPassword(null);
            return ServerResult.ok(patient);
        } else {
            return ServerResult.error(404, "患者不存在");
        }
    }
} 