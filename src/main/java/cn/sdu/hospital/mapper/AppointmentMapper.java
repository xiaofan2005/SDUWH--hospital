package cn.sdu.hospital.mapper;

import cn.sdu.hospital.pojo.Appointment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 预约的持久层接口
 * @author Administrator
 */
@Mapper
public interface AppointmentMapper extends BaseMapper<Appointment> {
    
    /**
     * 根据医生ID获取今日预约数量
     * @param doctorId 医生ID
     * @param today 今日日期 (YYYY-MM-DD)
     * @return 今日预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "WHERE s.doctor_id = #{doctorId} AND s.schedule_date = #{today} " +
            "AND a.status != '已取消'")
    int getTodayAppointmentCount(@Param("doctorId") Integer doctorId, @Param("today") String today);
    
    /**
     * 根据医生ID获取本周预约数量
     * @param doctorId 医生ID
     * @param weekStart 本周开始日期
     * @param weekEnd 本周结束日期
     * @return 本周预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "WHERE s.doctor_id = #{doctorId} " +
            "AND s.schedule_date >= #{weekStart} AND s.schedule_date <= #{weekEnd} " +
            "AND a.status != '已取消'")
    int getWeekAppointmentCount(@Param("doctorId") Integer doctorId, 
                               @Param("weekStart") String weekStart, 
                               @Param("weekEnd") String weekEnd);
    
    /**
     * 根据医生ID获取本月预约数量
     * @param doctorId 医生ID
     * @param monthStart 本月开始日期
     * @param monthEnd 本月结束日期
     * @return 本月预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "WHERE s.doctor_id = #{doctorId} " +
            "AND s.schedule_date >= #{monthStart} AND s.schedule_date <= #{monthEnd} " +
            "AND a.status != '已取消'")
    int getMonthAppointmentCount(@Param("doctorId") Integer doctorId, 
                                @Param("monthStart") String monthStart, 
                                @Param("monthEnd") String monthEnd);
    
    /**
     * 根据医生ID获取累计患者数量（去重）
     * @param doctorId 医生ID
     * @return 累计患者数量
     */
    @Select("SELECT COUNT(DISTINCT a.patient_id) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "WHERE s.doctor_id = #{doctorId} AND a.status != '已取消'")
    int getTotalPatientCount(@Param("doctorId") Integer doctorId);
    
    /**
     * 获取预约状态分布统计
     * @return 状态分布数据
     */
    @Select("SELECT status, COUNT(*) as count FROM appointment GROUP BY status")
    java.util.List<java.util.Map<String, Object>> getAppointmentStatusStatistics();
    
    /**
     * 根据科室获取预约统计
     * @param departmentId 科室ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "INNER JOIN doctor d ON s.doctor_id = d.id " +
            "WHERE d.department_id = #{departmentId} " +
            "AND s.schedule_date >= #{startDate} AND s.schedule_date <= #{endDate} " +
            "AND a.status != '已取消'")
    int getDepartmentAppointmentCount(@Param("departmentId") Integer departmentId, 
                                     @Param("startDate") String startDate, 
                                     @Param("endDate") String endDate);
    
    /**
     * 获取系统总预约数
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预约数量
     */
    @Select("SELECT COUNT(*) FROM appointment a " +
            "INNER JOIN schedule s ON a.schedule_id = s.id " +
            "WHERE s.schedule_date >= #{startDate} AND s.schedule_date <= #{endDate} " +
            "AND a.status != '已取消'")
    int getSystemAppointmentCount(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    /**
     * 获取系统总患者数（去重）
     * @return 患者数量
     */
    @Select("SELECT COUNT(DISTINCT patient_id) FROM appointment WHERE status != '已取消'")
    int getSystemTotalPatientCount();
} 