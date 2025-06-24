-- 使用数据库
USE student_system;

-- 插入用户数据
-- 密码均为123456的BCrypt加密
INSERT INTO `user` (`id`, `username`, `password`, `name`, `gender`, `email`, `phone`, `user_type`, `status`) VALUES
(1, 'admin', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '管理员', 1, 'admin@school.edu', '13800000000', 2, 1),
(2, 'teacher1', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '张教授', 1, 'zhang@school.edu', '13911111111', 1, 1),
(3, 'teacher2', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '李讲师', 0, 'li@school.edu', '13922222222', 1, 1),
(4, 'teacher3', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '王副教授', 1, 'wang@school.edu', '13933333333', 1, 1),
(5, 'student1', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '赵同学', 0, 'zhao@school.edu', '13944444444', 0, 1),
(6, 'student2', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '钱同学', 1, 'qian@school.edu', '13955555555', 0, 1),
(7, 'student3', '$2a$10$kV7UrzbLSA0XZ3MzYsEb7OtOlhZKq2QF5CDg8oKQQKrRmYP1gCLyG', '孙同学', 0, 'sun@school.edu', '13966666666', 0, 1);

-- 插入教师数据
INSERT INTO `teacher` (`id`, `teacher_no`, `title`, `department`) VALUES
(2, 'T20010001', '教授', '计算机科学与技术学院'),
(3, 'T20150002', '讲师', '软件工程学院'),
(4, 'T20080003', '副教授', '人工智能学院');

-- 插入学生数据
INSERT INTO `student` (`id`, `student_no`, `major`, `class_name`, `grade`, `admission_date`) VALUES
(5, '2021010001', '计算机科学与技术', '计算机2101班', '2021', '2021-09-01'),
(6, '2021010002', '软件工程', '软件2101班', '2021', '2021-09-01'),
(7, '2021020001', '人工智能', '人工智能2102班', '2021', '2021-09-01');

-- 插入学期数据
INSERT INTO `term` (`id`, `term_name`, `start_date`, `end_date`, `selection_start_time`, `selection_end_time`, `withdraw_end_time`, `status`) VALUES
(1, '2023-2024学年第一学期', '2023-09-01', '2024-01-31', '2023-08-20 00:00:00', '2023-08-31 23:59:59', '2023-09-15 23:59:59', 0),
(2, '2023-2024学年第二学期', '2024-02-01', '2024-06-30', '2024-01-15 00:00:00', '2024-01-31 23:59:59', '2024-02-15 23:59:59', 1);

-- 插入教室数据
INSERT INTO `classroom` (`id`, `classroom_name`, `building`, `capacity`, `classroom_type`, `status`) VALUES
(1, '博学楼101', '博学楼', 120, 2, 1),
(2, '博学楼102', '博学楼', 60, 0, 1),
(3, '博学楼201', '博学楼', 120, 2, 1),
(4, '致知楼301', '致知楼', 40, 1, 1),
(5, '致知楼302', '致知楼', 40, 1, 1);

-- 插入课程数据
INSERT INTO `course` (`id`, `course_name`, `course_code`, `credit`, `period`, `course_type`, `introduction`, `department`, `teacher_id`, `max_student`, `current_student`, `status`) VALUES
(1, '数据结构', 'CS2001', 4.0, 64, 0, '本课程是计算机科学与技术专业的核心课程，主要讲授各种数据结构及其算法。', '计算机科学与技术学院', 2, 60, 0, 1),
(2, '计算机网络', 'CS3001', 3.0, 48, 0, '本课程讲授计算机网络的基本概念、体系结构和协议。', '计算机科学与技术学院', 2, 60, 0, 1),
(3, '软件工程', 'SE2001', 3.0, 48, 0, '本课程介绍软件开发的过程、方法和工具。', '软件工程学院', 3, 40, 0, 1),
(4, '人工智能导论', 'AI1001', 3.0, 48, 1, '本课程介绍人工智能的基础理论与应用。', '人工智能学院', 4, 40, 0, 1),
(5, '操作系统', 'CS3002', 4.0, 64, 0, '本课程介绍操作系统的基本概念与原理。', '计算机科学与技术学院', 4, 60, 0, 1);

-- 插入先修课关系
INSERT INTO `prerequisite` (`id`, `course_id`, `prerequisite_course_id`) VALUES
(1, 5, 1);  -- 操作系统的先修课是数据结构

-- 插入课程时间表
INSERT INTO `course_schedule` (`id`, `course_id`, `classroom_id`, `term_id`, `day_of_week`, `start_time`, `end_time`, `week_range`) VALUES
(1, 1, 1, 2, 1, '08:00:00', '09:40:00', '1-16'),  -- 数据结构，周一上午8-9:40
(2, 1, 1, 2, 3, '08:00:00', '09:40:00', '1-16'),  -- 数据结构，周三上午8-9:40
(3, 2, 3, 2, 2, '10:00:00', '11:40:00', '1-16'),  -- 计算机网络，周二上午10-11:40
(4, 2, 3, 2, 4, '10:00:00', '11:40:00', '1-16'),  -- 计算机网络，周四上午10-11:40
(5, 3, 2, 2, 1, '14:00:00', '15:40:00', '1-16'),  -- 软件工程，周一下午14-15:40
(6, 3, 2, 2, 3, '14:00:00', '15:40:00', '1-16'),  -- 软件工程，周三下午14-15:40
(7, 4, 4, 2, 2, '14:00:00', '15:40:00', '1-16'),  -- 人工智能导论，周二下午14-15:40
(8, 4, 4, 2, 5, '10:00:00', '11:40:00', '1-16'),  -- 人工智能导论，周五上午10-11:40
(9, 5, 3, 2, 2, '08:00:00', '09:40:00', '1-16'),  -- 操作系统，周二上午8-9:40
(10, 5, 3, 2, 4, '14:00:00', '15:40:00', '1-16'); -- 操作系统，周四下午14-15:40 