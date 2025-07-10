# 🏥 医院挂号预约系统

基于Spring Boot + MyBatis-Plus + Vue.js开发的医院挂号预约管理系统

## 📋 项目概述

医院挂号预约系统是一个功能完整的医疗服务管理平台，支持管理员、医生、患者三种角色，提供科室管理、医生管理、排班管理、预约管理等全面功能。

### 🎯 `主要功能`

#### 管理员功能

- 🔐 登录功能：系统管理员身份验证
- 👩‍⚕️ 医生信息管理：医生基本信息的增删改查
- 🧑‍🤝‍🧑 患者信息管理：患者档案管理
- 🏢 科室信息管理：医院科室信息维护
- 🏠 诊室信息管理：诊室资源管理
- 💼 号源池管理：医生可预约号源维护
- 📅 医生排班管理：排班信息统一管理
- ⏰ 预约时段管理：时段配置管理
- 🔍 查询功能：各类信息的综合查询
- 🔑 密码管理：修改个人密码

#### 医生功能

- 🔐 登录功能：医生身份验证
- 📊 基本信息查询：个人信息、号源、排班查询
- 🔄 调班管理：请假申请、停诊处理
- 📝 患者队列查询：预约患者队列管理

#### 患者预约功能

- 🔍 信息查询：多种方式查询医生、科室号源
- 📞 挂号预约：在线预约挂号服务
- ❌ 取消预约：预约取消功能
- ✏️ 修改预约：预约信息调整

#### 查询统计功能

- 📈 权限查询：基于角色的信息查询
- 📊 统计分析：预约情况统计分析
- 📋 报表生成：统计结果报表输出

## 🛠️ 技术栈

### 后端技术

- **Java 17** - 开发语言
- **Spring Boot 2.6.13** - 基础框架
- **Spring MVC** - Web框架
- **MyBatis-Plus 3.3.1** - 持久层框架
- **MySQL 8.0** - 数据库
- **Swagger** - API文档

### 前端技术

- **Vue.js** - 前端框架
- **Axios** - HTTP客户端
- **HTML5/CSS3** - 页面结构和样式
- **JavaScript ES6+** - 前端逻辑

## 📦 项目结构

```
hospital/
├── src/main/java/cn/sdu/hospital/
│   ├── HospitalApplication.java          # 主启动类
│   ├── config/
│   │   └── SwaggerConfiguration.java     # Swagger配置
│   ├── controller/                       # 控制层
│   │   ├── AdminController.java
│   │   ├── DepartmentController.java
│   │   ├── DoctorController.java
│   │   ├── PatientController.java
│   │   ├── ScheduleController.java
│   │   └── AppointmentController.java
│   ├── service/                          # 业务层
│   │   ├── AdminService.java
│   │   ├── DepartmentService.java
│   │   ├── DoctorService.java
│   │   ├── PatientService.java
│   │   ├── ScheduleService.java
│   │   ├── AppointmentService.java
│   │   ├── ClinicRoomService.java
│   │   ├── TimeSlotService.java
│   │   └── impl/                         # 业务层实现
│   ├── mapper/                           # 持久层
│   │   ├── AdminMapper.java
│   │   ├── DepartmentMapper.java
│   │   ├── DoctorMapper.java
│   │   ├── PatientMapper.java
│   │   ├── ScheduleMapper.java
│   │   ├── AppointmentMapper.java
│   │   ├── ClinicRoomMapper.java
│   │   └── TimeSlotMapper.java
│   ├── pojo/                             # 实体类
│   │   ├── Admin.java
│   │   ├── Department.java
│   │   ├── Doctor.java
│   │   ├── Patient.java
│   │   ├── Schedule.java
│   │   ├── Appointment.java
│   │   ├── ClinicRoom.java
│   │   └── TimeSlot.java
│   └── util/
│       └── ServerResult.java             # 统一响应结果
├── src/main/resources/
│   ├── application.yml                   # 配置文件
│   └── static/                           # 静态资源
│       ├── index.html                    # 首页
│       ├── admin-login.html              # 管理员登录
│       ├── admin-dashboard.html          # 管理员控制台
│       ├── doctor-login.html             # 医生登录
│       ├── doctor-dashboard.html         # 医生管理界面
│       ├── patient-login.html            # 患者登录
│       ├── patient-dashboard.html        # 患者预约界面
│       └── js/                           # JavaScript文件
├── hospital.sql                          # 数据库脚本
├── pom.xml                              # Maven配置
└── README.md                            # 项目说明
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.6+
- IntelliJ IDEA 2021+（推荐）

### 1. 克隆项目

```bash
git clone [repository-url]
cd hospital
```

### 2. 数据库配置

1. 创建MySQL数据库：

```sql
CREATE DATABASE hospital CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入数据库脚本：

```bash
mysql -u root -p hospital < hospital.sql
```

### 3. 配置数据库连接

修改 `src/main/resources/application.yml` 文件中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hospital?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
```

### 4. 下载前端依赖文件

请下载以下JavaScript文件并放置到 `src/main/resources/static/js/` 目录下：

#### 📥 下载链接

- **Vue.js** (开发版): https://unpkg.com/vue@2.6.14/dist/vue.js
- **Axios**: https://unpkg.com/axios@0.27.2/dist/axios.min.js

#### 💡 下载方法

```bash
# 创建js目录
mkdir -p src/main/resources/static/js

# 使用wget下载（Linux/Mac）
wget https://unpkg.com/vue@2.6.14/dist/vue.js -O src/main/resources/static/js/vue.js
wget https://unpkg.com/axios@0.27.2/dist/axios.min.js -O src/main/resources/static/js/axios.min.js

# 或使用curl下载
curl -o src/main/resources/static/js/vue.js https://unpkg.com/vue@2.6.14/dist/vue.js
curl -o src/main/resources/static/js/axios.min.js https://unpkg.com/axios@0.27.2/dist/axios.min.js
```

### 5. 启动项目

```bash
mvn spring-boot:run
```

### 6. 访问系统

- 系统首页: http://localhost:8091
- 管理员登录: http://localhost:8091/admin-login.html
- 医生登录: http://localhost:8091/doctor-login.html
- 患者登录: http://localhost:8091/patient-login.html
- API文档: http://localhost:8091/swagger-ui/index.html

## 🔐 默认账号

### 管理员账号

- 用户名: `admin`
- 密码: `admin123`

### 医生账号

- 用户名: `doc001`
- 密码: `doc123`

### 患者账号

- 手机号: `13900139001`
- 密码: `123456`

📝 **注意：** 患者可以通过登录页面的注册功能自行注册新账号

## 📚 API文档

启动项目后，访问 http://localhost:8091/swagger-ui/index.html 查看完整的API文档。

### 主要接口

#### 管理员接口

- `POST /admin/login` - 管理员登录
- `GET /admin/selectAll` - 查询所有管理员
- `POST /admin/insert` - 新增管理员
- `PUT /admin/update` - 更新管理员信息
- `DELETE /admin/delete` - 删除管理员

#### 科室管理接口

- `GET /department/selectAll` - 查询所有科室
- `POST /department/insert` - 新增科室
- `PUT /department/update` - 更新科室信息
- `DELETE /department/delete` - 删除科室

#### 医生管理接口

- `POST /doctor/login` - 医生登录
- `GET /doctor/{id}` - 查询医生信息
- `GET /doctor/department/{departmentId}` - 按科室查询医生
- `GET /doctor/selectAll` - 查询所有医生
- `POST /doctor/insert` - 新增医生
- `PUT /doctor/update` - 更新医生信息

#### 患者管理接口

- `POST /patient/login` - 患者登录
- `POST /patient` - 患者注册
- `GET /patient/{id}` - 查询患者信息
- `PUT /patient/update` - 更新患者信息

#### 预约管理接口

- `POST /appointment` - 创建预约
- `DELETE /appointment/{id}` - 取消预约
- `PUT /appointment/{id}/status` - 更新预约状态
- `GET /appointment/patient/{patientId}/current` - 患者当前预约
- `GET /appointment/patient/{patientId}/history` - 患者历史预约
- `GET /appointment/doctor/{doctorId}/date/{date}` - 医生指定日期队列
- `GET /appointment/doctor/{doctorId}/statistics` - 医生预约统计

#### 排班管理接口

- `GET /schedule/doctor/{doctorId}` - 查询医生排班
- `GET /schedule/doctor/{doctorId}/available` - 查询医生可用排班
- `GET /schedule/selectAvailable` - 查询所有可用排班
- `POST /schedule/insert` - 新增排班
- `PUT /schedule/update` - 更新排班

## 🗄️ 数据库设计

### 主要数据表

#### admin - 管理员表

- `id` - 管理员ID（主键）
- `username` - 用户名
- `password` - 密码
- `name` - 姓名
- `phone` - 电话
- `email` - 邮箱

#### department - 科室表

- `id` - 科室ID（主键）
- `name` - 科室名称
- `description` - 科室描述
- `location` - 科室位置
- `phone` - 科室电话

#### doctor - 医生表

- `id` - 医生ID（主键）
- `username` - 用户名
- `password` - 密码
- `name` - 姓名
- `department_id` - 所属科室ID（外键）
- `clinic_room_id` - 所属诊室ID（外键）
- `registration_fee` - 挂号费
- `status` - 状态

#### schedule - 排班表

- `id` - 排班ID（主键）
- `doctor_id` - 医生ID（外键）
- `schedule_date` - 排班日期
- `time_slot_id` - 时段ID（外键）
- `total_count` - 总号源数
- `booked_count` - 已预约数

#### appointment - 预约表

- `id` - 预约ID（主键）
- `patient_id` - 患者ID（外键）
- `schedule_id` - 排班ID（外键）
- `appointment_number` - 预约号码
- `status` - 预约状态

