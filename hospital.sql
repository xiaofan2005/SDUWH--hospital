-- 医院挂号预约系统数据库脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS hospital CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hospital;

-- 1. 管理员表
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '管理员ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '管理员表';

-- 2. 科室表
DROP TABLE IF EXISTS department;
CREATE TABLE department (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '科室ID',
    name VARCHAR(50) NOT NULL COMMENT '科室名称',
    description TEXT COMMENT '科室描述',
    location VARCHAR(100) COMMENT '科室位置',
    phone VARCHAR(20) COMMENT '科室电话',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '科室表';

-- 3. 诊室表
DROP TABLE IF EXISTS clinic_room;
CREATE TABLE clinic_room (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '诊室ID',
    name VARCHAR(50) NOT NULL COMMENT '诊室名称',
    department_id INT NOT NULL COMMENT '所属科室ID',
    location VARCHAR(100) COMMENT '诊室位置',
    capacity INT DEFAULT 1 COMMENT '容纳人数',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (department_id) REFERENCES department(id)
) COMMENT '诊室表';

-- 4. 医生表
DROP TABLE IF EXISTS doctor;
CREATE TABLE doctor (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '医生ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    age INT COMMENT '年龄',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(100) COMMENT '邮箱',
    department_id INT NOT NULL COMMENT '所属科室ID',
    clinic_room_id INT COMMENT '所属诊室ID',
    title VARCHAR(50) COMMENT '职称',
    specialty TEXT COMMENT '专长',
    introduction TEXT COMMENT '简介',
    registration_fee DECIMAL(10,2) DEFAULT 0.00 COMMENT '挂号费',
    status ENUM('在职', '停诊', '休假') DEFAULT '在职' COMMENT '状态',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (clinic_room_id) REFERENCES clinic_room(id)
) COMMENT '医生表';

-- 5. 患者表
DROP TABLE IF EXISTS patient;
CREATE TABLE patient (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '患者ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    age INT COMMENT '年龄',
    phone VARCHAR(20) NOT NULL COMMENT '电话',
    id_card VARCHAR(18) COMMENT '身份证号',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    address VARCHAR(200) COMMENT '地址',
    medical_history TEXT COMMENT '病史',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '患者表';

-- 6. 预约时段表
DROP TABLE IF EXISTS time_slot;
CREATE TABLE time_slot (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '时段ID',
    name VARCHAR(50) NOT NULL COMMENT '时段名称',
    start_time TIME NOT NULL COMMENT '开始时间',
    end_time TIME NOT NULL COMMENT '结束时间',
    description VARCHAR(100) COMMENT '时段描述',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '预约时段表';

-- 7. 医生排班表
DROP TABLE IF EXISTS schedule;
CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '排班ID',
    doctor_id INT NOT NULL COMMENT '医生ID',
    schedule_date DATE NOT NULL COMMENT '排班日期',
    time_slot_id INT NOT NULL COMMENT '时段ID',
    total_count INT DEFAULT 30 COMMENT '总号源数',
    booked_count INT DEFAULT 0 COMMENT '已预约数',
    status ENUM('正常', '停诊', '已满') DEFAULT '正常' COMMENT '排班状态',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (time_slot_id) REFERENCES time_slot(id),
    UNIQUE KEY uk_doctor_date_slot (doctor_id, schedule_date, time_slot_id)
) COMMENT '医生排班表';

-- 8. 预约表
DROP TABLE IF EXISTS appointment;
CREATE TABLE appointment (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID',
    patient_id INT NOT NULL COMMENT '患者ID',
    schedule_id INT NOT NULL COMMENT '排班ID',
    appointment_number INT NOT NULL COMMENT '预约号码',
    status ENUM('已预约', '已取消', '已完成', '过期') DEFAULT '已预约' COMMENT '预约状态',
    symptoms TEXT COMMENT '症状描述',
    appointment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '预约时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
) COMMENT '预约表';

-- 插入测试数据

-- 插入管理员数据
INSERT INTO admin (username, password, name, phone, email) VALUES
('admin', 'admin123', '系统管理员', '13800138000', 'admin@hospital.com'),
('manager', 'manager123', '医务科长', '13800138001', 'manager@hospital.com');

-- 插入科室数据
INSERT INTO department (name, description, location, phone) VALUES
('内科', '内科疾病诊治', '门诊楼1楼', '010-12345678'),
('外科', '外科手术治疗', '门诊楼2楼', '010-12345679'),
('儿科', '儿童疾病诊治', '门诊楼3楼', '010-12345680'),
('妇产科', '妇科疾病和产科服务', '门诊楼4楼', '010-12345681'),
('骨科', '骨科疾病治疗', '门诊楼5楼', '010-12345682');

-- 插入诊室数据
INSERT INTO clinic_room (name, department_id, location, capacity) VALUES
('内科诊室1', 1, '门诊楼1楼101', 1),
('内科诊室2', 1, '门诊楼1楼102', 1),
('外科诊室1', 2, '门诊楼2楼201', 1),
('外科诊室2', 2, '门诊楼2楼202', 1),
('儿科诊室1', 3, '门诊楼3楼301', 1),
('妇产科诊室1', 4, '门诊楼4楼401', 1),
('骨科诊室1', 5, '门诊楼5楼501', 1);

-- 插入医生数据
INSERT INTO doctor (username, password, name, gender, age, phone, email, department_id, clinic_room_id, title, specialty, introduction, registration_fee, status) VALUES
('doc001', 'doc123', '张医生', '男', 45, '13801234567', 'zhang@hospital.com', 1, 1, '主任医师', '心血管疾病', '从事内科临床工作20年', 50.00, '在职'),
('doc002', 'doc123', '李医生', '女', 38, '13801234568', 'li@hospital.com', 1, 2, '副主任医师', '消化系统疾病', '消化内科专家', 30.00, '在职'),
('doc003', 'doc123', '王医生', '男', 42, '13801234569', 'wang@hospital.com', 2, 3, '主任医师', '外科手术', '外科手术专家', 60.00, '在职'),
('doc004', 'doc123', '赵医生', '女', 35, '13801234570', 'zhao@hospital.com', 3, 5, '主治医师', '儿童疾病', '儿科专家', 40.00, '在职'),
('doc005', 'doc123', '陈医生', '女', 40, '13801234571', 'chen@hospital.com', 4, 6, '副主任医师', '妇科疾病', '妇产科专家', 45.00, '在职');

-- 插入患者数据
INSERT INTO patient (name, gender, age, phone, id_card, password, address, medical_history) VALUES
('张三', '男', 35, '13900139001', '110101198800101234', '123456', '北京市朝阳区', '高血压病史'),
('李四', '女', 28, '13900139002', '110101199200202345', '123456', '北京市海淀区', '无特殊病史'),
('王五', '男', 42, '13900139003', '110101198000303456', '123456', '北京市西城区', '糖尿病史'),
('赵六', '女', 31, '13900139004', '110101199100404567', '123456', '北京市东城区', '过敏性鼻炎'),
('钱七', '男', 8, '13900139005', '110101201500505678', '123456', '北京市丰台区', '无特殊病史');

-- 插入预约时段数据
INSERT INTO time_slot (name, start_time, end_time, description) VALUES
('上午时段1', '08:00:00', '09:00:00', '上午第一时段'),
('上午时段2', '09:00:00', '10:00:00', '上午第二时段'),
('上午时段3', '10:00:00', '11:00:00', '上午第三时段'),
('上午时段4', '11:00:00', '12:00:00', '上午第四时段'),
('下午时段1', '14:00:00', '15:00:00', '下午第一时段'),
('下午时段2', '15:00:00', '16:00:00', '下午第二时段'),
('下午时段3', '16:00:00', '17:00:00', '下午第三时段'),
('下午时段4', '17:00:00', '18:00:00', '下午第四时段');

-- 插入排班数据（未来一周的排班）
INSERT INTO schedule (doctor_id, schedule_date, time_slot_id, total_count, booked_count, status) VALUES
-- 张医生排班
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 1, 30, 5, '正常'),
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 2, 30, 8, '正常'),
(1, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 5, 30, 3, '正常'),
(1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 1, 30, 2, '正常'),
(1, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 6, 30, 1, '正常'),
-- 李医生排班
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 3, 25, 4, '正常'),
(2, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 7, 25, 6, '正常'),
(2, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 2, 25, 0, '正常'),
-- 王医生排班
(3, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 1, 20, 8, '正常'),
(3, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 5, 20, 2, '正常'),
-- 赵医生排班
(4, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 4, 35, 10, '正常'),
(4, DATE_ADD(CURDATE(), INTERVAL 2 DAY), 8, 35, 5, '正常'),
-- 陈医生排班
(5, DATE_ADD(CURDATE(), INTERVAL 1 DAY), 6, 30, 7, '正常'),
(5, DATE_ADD(CURDATE(), INTERVAL 3 DAY), 3, 30, 3, '正常');

-- 插入预约数据
INSERT INTO appointment (patient_id, schedule_id, appointment_number, status, symptoms) VALUES
(1, 1, 1, '已预约', '胸闷心慌'),
(2, 1, 2, '已预约', '头痛头晕'),
(3, 6, 1, '已预约', '消化不良'),
(4, 9, 1, '已预约', '腰痛'),
(5, 11, 1, '已预约', '发热咳嗽'); 