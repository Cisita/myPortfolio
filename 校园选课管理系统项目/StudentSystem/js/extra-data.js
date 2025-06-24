/**
 * 学生课程管理系统补充数据
 */

// 补退课申请数据
const applyFormData = [
    {
        id: "AF001",
        studentId: "2023001",
        courseId: "C006",
        type: 0, // 0-补选，1-退课
        reason: "因专业学习需要，希望补选该课程进一步学习软件需求分析知识。",
        status: 1, // 0-待审核，1-通过，2-驳回
        attachments: ["uploads/apply/2023001_C006_1.pdf"],
        applyTime: "2024-03-10 10:15:30",
        reviewTime: "2024-03-12 14:30:22",
        reviewer: "A001",
        reviewReason: "符合补选条件，审核通过。"
    },
    {
        id: "AF002",
        studentId: "2023002",
        courseId: "C005",
        type: 0,
        reason: "对软件测试方向很感兴趣，希望能补选该课程。",
        status: 1,
        attachments: ["uploads/apply/2023002_C005_1.pdf"],
        applyTime: "2024-03-10 11:25:40",
        reviewTime: "2024-03-12 14:33:15",
        reviewer: "A001",
        reviewReason: "符合补选条件，审核通过。"
    },
    {
        id: "AF003",
        studentId: "2023003",
        courseId: "C008",
        type: 0,
        reason: "对深度学习有浓厚兴趣，已经自学了相关基础知识，希望能补选该课程。",
        status: 2,
        attachments: ["uploads/apply/2023003_C008_1.pdf", "uploads/apply/2023003_C008_2.pdf"],
        applyTime: "2024-03-10 14:10:25",
        reviewTime: "2024-03-12 15:05:48",
        reviewer: "A001",
        reviewReason: "未完成先修课程 C007，不符合补选条件。"
    },
    {
        id: "AF004",
        studentId: "2023004",
        courseId: "C004",
        type: 1,
        reason: "因个人原因学习精力不足，希望能退掉该课程，下学期再选。",
        status: 1,
        attachments: ["uploads/apply/2023004_C004_1.pdf"],
        applyTime: "2024-03-11 09:30:15",
        reviewTime: "2024-03-12 15:15:33",
        reviewer: "A001",
        reviewReason: "理由充分，审核通过。"
    },
    {
        id: "AF005",
        studentId: "2023005",
        courseId: "C009",
        type: 1,
        reason: "与实习时间冲突，无法正常上课，申请退课。",
        status: 1,
        attachments: ["uploads/apply/2023005_C009_1.pdf", "uploads/apply/2023005_C009_2.pdf"],
        applyTime: "2024-03-11 10:45:22",
        reviewTime: "2024-03-12 15:28:10",
        reviewer: "A001",
        reviewReason: "已提供实习证明，审核通过。"
    },
    {
        id: "AF006",
        studentId: "2022001",
        courseId: "C012",
        type: 1,
        reason: "已经在工作实践中掌握了Web开发技术，希望退课以腾出时间学习其他课程。",
        status: 2,
        attachments: ["uploads/apply/2022001_C012_1.pdf"],
        applyTime: "2024-03-11 13:20:48",
        reviewTime: "2024-03-12 16:02:35",
        reviewer: "A001",
        reviewReason: "理由不充分，已过退课截止时间，不予批准。"
    },
    {
        id: "AF007",
        studentId: "2022002",
        courseId: "C014",
        type: 0,
        reason: "因转专业需要学习自然语言处理相关知识，希望能补选该课程。",
        status: 2,
        attachments: ["uploads/apply/2022002_C014_1.pdf", "uploads/apply/2022002_C014_2.pdf"],
        applyTime: "2024-03-11 14:33:27",
        reviewTime: "2024-03-12 16:15:42",
        reviewer: "A001",
        reviewReason: "未完成先修课程C008，不符合补选条件。"
    },
    {
        id: "AF008",
        studentId: "2022003",
        courseId: "C015",
        type: 0,
        reason: "对数据挖掘技术很感兴趣，已自学相关知识，希望能补选该课程。",
        status: 1,
        attachments: ["uploads/apply/2022003_C015_1.pdf"],
        applyTime: "2024-03-11 16:05:18",
        reviewTime: "2024-03-12 16:30:25",
        reviewer: "A001",
        reviewReason: "已完成先修课程，符合补选条件，审核通过。"
    },
    {
        id: "AF009",
        studentId: "2022004",
        courseId: "C001",
        type: 1,
        reason: "因身体原因需要休学一段时间，希望能退掉该课程。",
        status: 1,
        attachments: ["uploads/apply/2022004_C001_1.pdf", "uploads/apply/2022004_C001_2.pdf", "uploads/apply/2022004_C001_3.pdf"],
        applyTime: "2024-03-12 09:15:50",
        reviewTime: "2024-03-13 10:05:33",
        reviewer: "A001",
        reviewReason: "已提供医院证明，审核通过。"
    },
    {
        id: "AF010",
        studentId: "2022005",
        courseId: "C013",
        type: 0,
        reason: "希望学习移动应用开发技术，为毕业设计做准备。",
        status: 0,
        attachments: ["uploads/apply/2022005_C013_1.pdf"],
        applyTime: "2024-03-12 11:28:35",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    },
    {
        id: "AF011",
        studentId: "2021001",
        courseId: "C012",
        type: 0,
        reason: "希望学习Web开发技术，扩展就业技能。",
        status: 0,
        attachments: ["uploads/apply/2021001_C012_1.pdf"],
        applyTime: "2024-03-12 13:42:18",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    },
    {
        id: "AF012",
        studentId: "2021002",
        courseId: "C007",
        type: 1,
        reason: "因实验室科研任务繁重，无法投入足够时间学习，申请退课。",
        status: 0,
        attachments: ["uploads/apply/2021002_C007_1.pdf", "uploads/apply/2021002_C007_2.pdf"],
        applyTime: "2024-03-12 15:33:27",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    },
    {
        id: "AF013",
        studentId: "2021003",
        courseId: "C003",
        type: 0,
        reason: "因课程安排变更，需要补选该课程以满足毕业要求。",
        status: 0,
        attachments: ["uploads/apply/2021003_C003_1.pdf"],
        applyTime: "2024-03-12 16:20:45",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    },
    {
        id: "AF014",
        studentId: "2021004",
        courseId: "C002",
        type: 1,
        reason: "已在其他学校修读过同等课程并获得学分，申请退课。",
        status: 0,
        attachments: ["uploads/apply/2021004_C002_1.pdf", "uploads/apply/2021004_C002_2.pdf"],
        applyTime: "2024-03-13 09:10:33",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    },
    {
        id: "AF015",
        studentId: "2021005",
        courseId: "C010",
        type: 0,
        reason: "需要学习操作系统知识，为研究生考试做准备。",
        status: 0,
        attachments: ["uploads/apply/2021005_C010_1.pdf"],
        applyTime: "2024-03-13 10:25:18",
        reviewTime: null,
        reviewer: null,
        reviewReason: null
    }
];

// 班级数据
const classData = [
    {
        id: "CL001",
        className: "数据结构1班",
        courseId: "C001",
        teacherId: "T001",
        classroomId: "R101",
        studentList: ["2023001", "2023002", "2022004", "2022001", "2022002", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 10:00:00"
    },
    {
        id: "CL002",
        className: "数据结构2班",
        courseId: "C001",
        teacherId: "T001",
        classroomId: "R102",
        studentList: ["2023003", "2023004", "2023005", "2022003", "2022005"],
        createTime: "2024-02-25 10:15:30"
    },
    {
        id: "CL003",
        className: "数据库系统1班",
        courseId: "C002",
        teacherId: "T002",
        classroomId: "R102",
        studentList: ["2023001", "2023002", "2023003", "2023004", "2023005", "2022001", "2022002", "2022003", "2022004", "2022005"],
        createTime: "2024-02-25 10:30:15"
    },
    {
        id: "CL004",
        className: "计算机网络1班",
        courseId: "C003",
        teacherId: "T003",
        classroomId: "R103",
        studentList: ["2023001", "2023002", "2023003", "2023004", "2023005", "2022001", "2022002", "2022003", "2022004", "2022005"],
        createTime: "2024-02-25 10:45:22"
    },
    {
        id: "CL005",
        className: "软件工程1班",
        courseId: "C004",
        teacherId: "T004",
        classroomId: "R104",
        studentList: ["2023002", "2023003", "2023004", "2022002", "2022005"],
        createTime: "2024-02-25 11:00:10"
    },
    {
        id: "CL006",
        className: "软件测试1班",
        courseId: "C005",
        teacherId: "T005",
        classroomId: "R105",
        studentList: ["2023003", "2022002", "2022005"],
        createTime: "2024-02-25 11:15:35"
    },
    {
        id: "CL007",
        className: "需求分析与设计1班",
        courseId: "C006",
        teacherId: "T006",
        classroomId: "R106",
        studentList: ["2023003", "2022005"],
        createTime: "2024-02-25 11:30:18"
    },
    {
        id: "CL008",
        className: "机器学习1班",
        courseId: "C007",
        teacherId: "T007",
        classroomId: "R107",
        studentList: ["2023005", "2022003", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 11:45:42"
    },
    {
        id: "CL009",
        className: "深度学习1班",
        courseId: "C008",
        teacherId: "T008",
        classroomId: "R108",
        studentList: ["2023005", "2022003"],
        createTime: "2024-02-25 13:00:15"
    },
    {
        id: "CL010",
        className: "计算机视觉1班",
        courseId: "C009",
        teacherId: "T009",
        classroomId: "R109",
        studentList: ["2023005"],
        createTime: "2024-02-25 13:15:33"
    },
    {
        id: "CL011",
        className: "操作系统1班",
        courseId: "C010",
        teacherId: "T010",
        classroomId: "R110",
        studentList: ["2022001", "2022004", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 13:30:22"
    },
    {
        id: "CL012",
        className: "编译原理1班",
        courseId: "C011",
        teacherId: "T011",
        classroomId: "R111",
        studentList: ["2022001", "2022004", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 13:45:48"
    },
    {
        id: "CL013",
        className: "Web开发技术1班",
        courseId: "C012",
        teacherId: "T012",
        classroomId: "R112",
        studentList: ["2023002", "2023004", "2022001", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 14:00:25"
    },
    {
        id: "CL014",
        className: "移动应用开发1班",
        courseId: "C013",
        teacherId: "T013",
        classroomId: "R113",
        studentList: ["2023004", "2022002", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 14:15:10"
    },
    {
        id: "CL015",
        className: "自然语言处理1班",
        courseId: "C014",
        teacherId: "T014",
        classroomId: "R114",
        studentList: ["2022003", "2021001", "2021002", "2021003", "2021004", "2021005"],
        createTime: "2024-02-25 14:30:35"
    }
];

// 管理员数据
const adminData = [
    {
        id: "A001",
        name: "系统管理员",
        password: "admin123",
        role: "super_admin",
        department: "教务处",
        phone: "13912345678",
        email: "admin@example.com"
    },
    {
        id: "A002",
        name: "计算机学院管理员",
        password: "admin123",
        role: "department_admin",
        department: "计算机科学与技术学院",
        phone: "13912345679",
        email: "cs_admin@example.com"
    },
    {
        id: "A003",
        name: "软件学院管理员",
        password: "admin123",
        role: "department_admin",
        department: "软件工程学院",
        phone: "13912345680",
        email: "se_admin@example.com"
    }
];

// 导出数据以供其他JS文件使用
const ExtraData = {
    applyForms: applyFormData,
    classes: classData,
    admins: adminData
};

// 在控制台打印已加载的补充数据
console.log("补充数据已加载"); 