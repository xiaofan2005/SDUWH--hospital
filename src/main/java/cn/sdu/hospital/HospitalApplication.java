package cn.sdu.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 医院挂号预约系统启动类
 * @author Administrator
 * @SpringBootApplication 声明这是一个启动类
 * @MapperScan("cn.sdu.hospital.mapper") 声明到哪个包下Mapper接口
 */
@SpringBootApplication
@MapperScan("cn.sdu.hospital.mapper")
public class HospitalApplication {
    public static void main(String[] args) {
        // 启动Spring Boot项目
        SpringApplication.run(HospitalApplication.class, args);
    }
} 