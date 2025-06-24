-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE student_system;

-- 用户表（包含学生、教师和管理员）
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0-女，1-男',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `user_type` tinyint(1) NOT NULL COMMENT '用户类型：0-学生，1-教师，2-管理员',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 学生表
CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL COMMENT '学生ID，关联user表id',
  `student_no` varchar(20) NOT NULL COMMENT '学号',
  `major` varchar(50) NOT NULL COMMENT '专业',
  `class_name` varchar(50) NOT NULL COMMENT '班级',
  `grade` varchar(10) NOT NULL COMMENT '年级',
  `admission_date` date NOT NULL COMMENT '入学日期',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_no` (`student_no`),
  CONSTRAINT `fk_student_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 教师表
CREATE TABLE IF NOT EXISTS `teacher` (
  `id` bigint(20) NOT NULL COMMENT '教师ID，关联user表id',
  `teacher_no` varchar(20) NOT NULL COMMENT '工号',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `department` varchar(50) NOT NULL COMMENT '院系',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_teacher_no` (`teacher_no`),
  CONSTRAINT `fk_teacher_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- 课程表
CREATE TABLE IF NOT EXISTS `course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_name` varchar(100) NOT NULL COMMENT '课程名称',
  `course_code` varchar(20) NOT NULL COMMENT '课程代码',
  `credit` decimal(3,1) NOT NULL COMMENT '学分',
  `period` int(11) NOT NULL COMMENT '课时',
  `course_type` tinyint(1) NOT NULL COMMENT '课程类型：0-必修，1-选修',
  `introduction` text DEFAULT NULL COMMENT '课程简介',
  `department` varchar(50) NOT NULL COMMENT '开课院系',
  `teacher_id` bigint(20) NOT NULL COMMENT '授课教师ID',
  `max_student` int(11) NOT NULL COMMENT '最大学生数',
  `current_student` int(11) NOT NULL DEFAULT 0 COMMENT '当前选课人数',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-关闭，1-开放',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_code` (`course_code`),
  KEY `idx_teacher_id` (`teacher_id`),
  CONSTRAINT `fk_course_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 先修课表
CREATE TABLE IF NOT EXISTS `prerequisite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `prerequisite_course_id` bigint(20) NOT NULL COMMENT '先修课程ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_course_prerequisite` (`course_id`,`prerequisite_course_id`),
  KEY `idx_prerequisite_course_id` (`prerequisite_course_id`),
  CONSTRAINT `fk_prerequisite_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_prerequisite_prerequisite` FOREIGN KEY (`prerequisite_course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='先修课表';

-- 教室表
CREATE TABLE IF NOT EXISTS `classroom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '教室ID',
  `classroom_name` varchar(50) NOT NULL COMMENT '教室名称',
  `building` varchar(50) NOT NULL COMMENT '教学楼',
  `capacity` int(11) NOT NULL COMMENT '容量',
  `classroom_type` tinyint(1) NOT NULL COMMENT '教室类型：0-普通教室，1-实验室，2-多媒体教室',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_classroom_name` (`classroom_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教室表';

-- 学期表
CREATE TABLE IF NOT EXISTS `term` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '学期ID',
  `term_name` varchar(50) NOT NULL COMMENT '学期名称',
  `start_date` date NOT NULL COMMENT '开始日期',
  `end_date` date NOT NULL COMMENT '结束日期',
  `selection_start_time` datetime DEFAULT NULL COMMENT '选课开始时间',
  `selection_end_time` datetime DEFAULT NULL COMMENT '选课结束时间',
  `withdraw_end_time` datetime DEFAULT NULL COMMENT '退课截止时间',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态：0-未激活，1-激活',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_term_name` (`term_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学期表';

-- 课程时间表
CREATE TABLE IF NOT EXISTS `course_schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `classroom_id` bigint(20) NOT NULL COMMENT '教室ID',
  `term_id` bigint(20) NOT NULL COMMENT '学期ID',
  `day_of_week` tinyint(1) NOT NULL COMMENT '星期几：1-7表示周一至周日',
  `start_time` time NOT NULL COMMENT '开始时间',
  `end_time` time NOT NULL COMMENT '结束时间',
  `week_range` varchar(100) NOT NULL COMMENT '周次范围，如1-16',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_classroom_id` (`classroom_id`),
  KEY `idx_term_id` (`term_id`),
  CONSTRAINT `fk_schedule_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_schedule_classroom` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_schedule_term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程时间表';

-- 班级表
CREATE TABLE IF NOT EXISTS `class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `class_name` varchar(50) NOT NULL COMMENT '班级名称',
  `teacher_id` bigint(20) NOT NULL COMMENT '教师ID',
  `classroom_id` bigint(20) NOT NULL COMMENT '教室ID',
  `term_id` bigint(20) NOT NULL COMMENT '学期ID',
  `student_count` int(11) NOT NULL DEFAULT 0 COMMENT '学生数量',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_classroom_id` (`classroom_id`),
  KEY `idx_term_id` (`term_id`),
  CONSTRAINT `fk_class_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_class_teacher` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_class_classroom` FOREIGN KEY (`classroom_id`) REFERENCES `classroom` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_class_term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- 学生选课表
CREATE TABLE IF NOT EXISTS `student_course` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '选课记录ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `class_id` bigint(20) NOT NULL COMMENT '班级ID',
  `term_id` bigint(20) NOT NULL COMMENT '学期ID',
  `status` tinyint(1) NOT NULL COMMENT '状态：0-待提交，1-已选，2-退课',
  `score` decimal(5,2) DEFAULT NULL COMMENT '成绩',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_course_term` (`student_id`,`course_id`,`term_id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_class_id` (`class_id`),
  KEY `idx_term_id` (`term_id`),
  CONSTRAINT `fk_student_course_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_course_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_course_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_course_term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生选课表';

-- 补退课申请表
CREATE TABLE IF NOT EXISTS `apply_form` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `term_id` bigint(20) NOT NULL COMMENT '学期ID',
  `type` tinyint(1) NOT NULL COMMENT '申请类型：0-补选，1-退课',
  `reason` text NOT NULL COMMENT '申请理由',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态：0-待审核，1-通过，2-驳回',
  `reviewer_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `review_time` datetime DEFAULT NULL COMMENT '审核时间',
  `review_reason` text DEFAULT NULL COMMENT '审核意见',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_term_id` (`term_id`),
  KEY `idx_reviewer_id` (`reviewer_id`),
  CONSTRAINT `fk_apply_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_apply_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_apply_term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_apply_reviewer` FOREIGN KEY (`reviewer_id`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='补退课申请表';

-- 申请附件表
CREATE TABLE IF NOT EXISTS `apply_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `apply_id` bigint(20) NOT NULL COMMENT '申请ID',
  `file_name` varchar(200) NOT NULL COMMENT '文件名',
  `file_path` varchar(500) NOT NULL COMMENT '文件路径',
  `file_size` bigint(20) NOT NULL COMMENT '文件大小(字节)',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_apply_id` (`apply_id`),
  CONSTRAINT `fk_attachment_apply` FOREIGN KEY (`apply_id`) REFERENCES `apply_form` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='申请附件表';

-- 学生班级表
CREATE TABLE IF NOT EXISTS `student_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  `class_id` bigint(20) NOT NULL COMMENT '班级ID',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `term_id` bigint(20) NOT NULL COMMENT '学期ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除，1-已删除',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_student_class_term` (`student_id`,`class_id`,`term_id`),
  KEY `idx_class_id` (`class_id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_term_id` (`term_id`),
  CONSTRAINT `fk_student_class_student` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_class_class` FOREIGN KEY (`class_id`) REFERENCES `class` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_class_course` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_student_class_term` FOREIGN KEY (`term_id`) REFERENCES `term` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生班级表'; 