package cn.sdu.hospital.controller;

import cn.sdu.hospital.pojo.Department;
import cn.sdu.hospital.service.DepartmentService;
import cn.sdu.hospital.util.ServerResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 科室控制类
 * @author Administrator
 */
@RestController
@Tag(name = "科室管理接口")
@RequestMapping("/department")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    /**
     * 查询所有科室
     * @return 科室列表
     */
    @GetMapping("/selectAll")
    @Operation(summary = "查询所有科室")
    public ServerResult<List<Department>> selectAll() {
        List<Department> list = departmentService.selectAll();
        return ServerResult.ok(list);
    }
    
    /**
     * 根据ID查询科室
     * @param id 科室ID
     * @return 科室信息
     */
    @GetMapping("/selectById")
    @Operation(summary = "根据ID查询科室")
    public ServerResult<Department> selectById(@RequestParam Integer id) {
        Department department = departmentService.selectById(id);
        if (department != null) {
            return ServerResult.ok(department);
        } else {
            return ServerResult.error(404, "科室不存在");
        }
    }
    
    /**
     * 新增科室
     * @param department 科室信息
     * @return 操作结果
     */
    @PostMapping("/insert")
    @Operation(summary = "新增科室")
    public ServerResult<Void> insert(@RequestBody Department department) {
        boolean result = departmentService.insert(department);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "新增科室失败");
        }
    }
    
    /**
     * 更新科室信息
     * @param department 科室信息
     * @return 操作结果
     */
    @PutMapping("/update")
    @Operation(summary = "更新科室信息")
    public ServerResult<Void> update(@RequestBody Department department) {
        boolean result = departmentService.update(department);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "更新科室信息失败");
        }
    }
    
    /**
     * 删除科室
     * @param id 科室ID
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除科室")
    public ServerResult<Void> deleteById(@RequestParam Integer id) {
        boolean result = departmentService.deleteById(id);
        if (result) {
            return ServerResult.ok();
        } else {
            return ServerResult.error(500, "删除科室失败");
        }
    }
    
    /**
     * 根据科室名称模糊查询
     * @param name 科室名称
     * @return 科室列表
     */
    @GetMapping("/selectByNameLike")
    @Operation(summary = "根据科室名称模糊查询")
    public ServerResult<List<Department>> selectByNameLike(@RequestParam String name) {
        List<Department> list = departmentService.selectByNameLike(name);
        return ServerResult.ok(list);
    }
    
    /**
     * 查询所有科室（RESTful版本）
     * @return 科室列表
     */
    @GetMapping
    @Operation(summary = "查询所有科室RESTful版本")
    public ServerResult<List<Department>> getAll() {
        List<Department> list = departmentService.selectAll();
        return ServerResult.ok(list);
    }
} 