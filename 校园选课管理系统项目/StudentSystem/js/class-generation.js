/**
 * 生成教学班级页面的JavaScript代码
 */

// 当DOM完全加载后执行
document.addEventListener('DOMContentLoaded', function() {
    // 检查用户是否已登录
    if (!checkLogin()) return;
    
    // 检查用户是否为管理员
    if (currentUser.userType !== 'admin') {
        showMessage('只有管理员可以使用班级生成功能', 'error');
        setTimeout(() => {
            window.location.href = '../index.html';
        }, 1500);
        return;
    }
    
    // 初始化标签页切换
    initTabs();
    
    // 初始化模态框
    initModals();
    
    // 初始化按钮事件
    initButtons();
    
    // 初始化筛选器
    initFilters();
    
    // 加载班级列表数据
    loadClassListData();
    
    // 加载教室列表数据
    loadClassroomListData();
    
    // 填充课程和教师筛选器选项
    populateFilterOptions();
});

/**
 * 初始化标签页切换
 */
function initTabs() {
    const tabs = document.querySelectorAll('.nav-tab');
    const tabContents = document.querySelectorAll('.tab-content');
    
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            // 移除所有标签的active类
            tabs.forEach(t => t.classList.remove('active'));
            
            // 隐藏所有内容
            tabContents.forEach(content => {
                content.style.display = 'none';
            });
            
            // 添加active类到当前标签
            this.classList.add('active');
            
            // 显示对应的内容
            const tabId = this.getAttribute('data-tab');
            document.getElementById(`${tabId}-tab`).style.display = 'block';
        });
    });
}

/**
 * 初始化模态框
 */
function initModals() {
    // 初始化班级详情模态框
    initClassDetailModal();
    
    // 初始化生成班级模态框
    initGenerateClassModal();
    
    // 初始化添加班级模态框
    initAddClassModal();
}

/**
 * 初始化班级详情模态框
 */
function initClassDetailModal() {
    const modal = document.getElementById('class-detail-modal');
    const closeBtn = modal.querySelector('.close');
    
    // 点击关闭按钮关闭模态框
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

/**
 * 初始化生成班级模态框
 */
function initGenerateClassModal() {
    const modal = document.getElementById('generate-class-modal');
    const closeBtn = modal.querySelector('.close');
    const cancelBtn = document.getElementById('cancel-generate-btn');
    const submitBtn = document.getElementById('submit-generate-btn');
    
    // 填充课程选择项
    populateCourseCheckboxes();
    
    // 点击关闭按钮关闭模态框
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击取消按钮关闭模态框
    cancelBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击提交按钮
    submitBtn.addEventListener('click', function() {
        generateClasses();
    });
    
    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

/**
 * 初始化添加班级模态框
 */
function initAddClassModal() {
    const modal = document.getElementById('add-class-modal');
    const closeBtn = modal.querySelector('.close');
    const cancelBtn = document.getElementById('cancel-add-class-btn');
    const submitBtn = document.getElementById('submit-add-class-btn');
    
    // 填充课程、教师和教室选项
    populateClassOptions();
    
    // 点击关闭按钮关闭模态框
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击取消按钮关闭模态框
    cancelBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击提交按钮
    submitBtn.addEventListener('click', function() {
        addClass();
    });
    
    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

/**
 * 初始化按钮事件
 */
function initButtons() {
    // 一键生成班级按钮
    const generateClassBtn = document.getElementById('generate-class-btn');
    generateClassBtn.addEventListener('click', function() {
        document.getElementById('generate-class-modal').style.display = 'block';
    });
    
    // 手动添加班级按钮
    const addClassBtn = document.getElementById('add-class-btn');
    addClassBtn.addEventListener('click', function() {
        document.getElementById('add-class-modal').style.display = 'block';
    });
}

/**
 * 初始化筛选器
 */
function initFilters() {
    // 班级列表筛选器
    const applyClassFilterBtn = document.getElementById('apply-class-filter');
    const resetClassFilterBtn = document.getElementById('reset-class-filter');
    
    applyClassFilterBtn.addEventListener('click', function() {
        filterClasses();
    });
    
    resetClassFilterBtn.addEventListener('click', function() {
        document.getElementById('filter-course').value = '';
        document.getElementById('filter-teacher').value = '';
        loadClassListData();
    });
    
    // 教室列表筛选器
    const applyClassroomFilterBtn = document.getElementById('apply-classroom-filter');
    const resetClassroomFilterBtn = document.getElementById('reset-classroom-filter');
    
    applyClassroomFilterBtn.addEventListener('click', function() {
        filterClassrooms();
    });
    
    resetClassroomFilterBtn.addEventListener('click', function() {
        document.getElementById('filter-building').value = '';
        document.getElementById('filter-capacity').value = '';
        loadClassroomListData();
    });
}

/**
 * 加载班级列表数据
 */
function loadClassListData() {
    const classes = ExtraData.classes;
    const tableBody = document.getElementById('class-list-body');
    tableBody.innerHTML = '';
    
    if (classes.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="8" class="no-data">暂无班级数据</td></tr>';
        return;
    }
    
    classes.forEach(cls => {
        const course = getCourseById(cls.courseId);
        const teacher = getTeacherById(cls.teacherId);
        const classroom = getClassroomById(cls.classroomId);
        
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${cls.id}</td>
            <td>${cls.className}</td>
            <td>${course ? course.courseName : '未知课程'}</td>
            <td>${teacher ? teacher.name : '未分配'}</td>
            <td>${classroom ? `${classroom.building} ${classroom.roomNumber}` : '未分配'}</td>
            <td>${cls.studentList.length}</td>
            <td>${formatDate(cls.createTime)}</td>
            <td>
                <button class="btn btn-sm" onclick="showClassDetail('${cls.id}')">详情</button>
                <button class="btn btn-danger btn-sm" onclick="deleteClass('${cls.id}')">删除</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 加载教室列表数据
 */
function loadClassroomListData() {
    const classrooms = Data.classrooms;
    const tableBody = document.getElementById('classroom-list-body');
    tableBody.innerHTML = '';
    
    if (classrooms.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="no-data">暂无教室数据</td></tr>';
        return;
    }
    
    classrooms.forEach(room => {
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${room.id}</td>
            <td>${room.building}</td>
            <td>${room.roomNumber}</td>
            <td>${room.capacity}</td>
            <td>${room.equipments}</td>
            <td>
                <button class="btn btn-sm" onclick="editClassroom('${room.id}')">编辑</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 填充筛选器选项
 */
function populateFilterOptions() {
    // 课程筛选器
    const courseFilter = document.getElementById('filter-course');
    const courses = Data.courses;
    
    courses.forEach(course => {
        const option = document.createElement('option');
        option.value = course.id;
        option.textContent = course.courseName;
        courseFilter.appendChild(option);
    });
    
    // 教师筛选器
    const teacherFilter = document.getElementById('filter-teacher');
    const teachers = Data.teachers;
    
    teachers.forEach(teacher => {
        const option = document.createElement('option');
        option.value = teacher.id;
        option.textContent = teacher.name;
        teacherFilter.appendChild(option);
    });
}

/**
 * 填充课程选择项
 */
function populateCourseCheckboxes() {
    const courseSelection = document.getElementById('course-selection');
    courseSelection.innerHTML = '';
    
    const courses = Data.courses;
    
    courses.forEach(course => {
        const div = document.createElement('div');
        div.className = 'checkbox-item';
        
        div.innerHTML = `
            <input type="checkbox" id="course-${course.id}" value="${course.id}">
            <label for="course-${course.id}">${course.courseName} (${course.id})</label>
        `;
        
        courseSelection.appendChild(div);
    });
}

/**
 * 填充添加班级模态框的选项
 */
function populateClassOptions() {
    // 课程选项
    const courseSelect = document.getElementById('class-course');
    courseSelect.innerHTML = '<option value="">-- 请选择课程 --</option>';
    
    Data.courses.forEach(course => {
        const option = document.createElement('option');
        option.value = course.id;
        option.textContent = `${course.courseName} (${course.id})`;
        courseSelect.appendChild(option);
    });
    
    // 教师选项
    const teacherSelect = document.getElementById('class-teacher');
    teacherSelect.innerHTML = '<option value="">-- 请选择教师 --</option>';
    
    Data.teachers.forEach(teacher => {
        const option = document.createElement('option');
        option.value = teacher.id;
        option.textContent = `${teacher.name} (${teacher.title})`;
        teacherSelect.appendChild(option);
    });
    
    // 教室选项
    const classroomSelect = document.getElementById('class-classroom');
    classroomSelect.innerHTML = '<option value="">-- 请选择教室 --</option>';
    
    Data.classrooms.forEach(room => {
        const option = document.createElement('option');
        option.value = room.id;
        option.textContent = `${room.building} ${room.roomNumber} (容量:${room.capacity})`;
        classroomSelect.appendChild(option);
    });
    
    // 当选择课程时，自动选择对应的教师
    courseSelect.addEventListener('change', function() {
        const courseId = this.value;
        if (!courseId) return;
        
        const course = getCourseById(courseId);
        if (course && course.teacherId) {
            teacherSelect.value = course.teacherId;
        }
    });
}

/**
 * 筛选班级
 */
function filterClasses() {
    const courseId = document.getElementById('filter-course').value;
    const teacherId = document.getElementById('filter-teacher').value;
    
    let filteredClasses = ExtraData.classes;
    
    if (courseId) {
        filteredClasses = filteredClasses.filter(cls => cls.courseId === courseId);
    }
    
    if (teacherId) {
        filteredClasses = filteredClasses.filter(cls => cls.teacherId === teacherId);
    }
    
    const tableBody = document.getElementById('class-list-body');
    tableBody.innerHTML = '';
    
    if (filteredClasses.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="8" class="no-data">没有找到符合条件的班级</td></tr>';
        return;
    }
    
    filteredClasses.forEach(cls => {
        const course = getCourseById(cls.courseId);
        const teacher = getTeacherById(cls.teacherId);
        const classroom = getClassroomById(cls.classroomId);
        
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${cls.id}</td>
            <td>${cls.className}</td>
            <td>${course ? course.courseName : '未知课程'}</td>
            <td>${teacher ? teacher.name : '未分配'}</td>
            <td>${classroom ? `${classroom.building} ${classroom.roomNumber}` : '未分配'}</td>
            <td>${cls.studentList.length}</td>
            <td>${formatDate(cls.createTime)}</td>
            <td>
                <button class="btn btn-sm" onclick="showClassDetail('${cls.id}')">详情</button>
                <button class="btn btn-danger btn-sm" onclick="deleteClass('${cls.id}')">删除</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 筛选教室
 */
function filterClassrooms() {
    const building = document.getElementById('filter-building').value;
    const capacity = document.getElementById('filter-capacity').value;
    
    let filteredClassrooms = Data.classrooms;
    
    if (building) {
        filteredClassrooms = filteredClassrooms.filter(room => room.building === building);
    }
    
    if (capacity) {
        filteredClassrooms = filteredClassrooms.filter(room => room.capacity >= parseInt(capacity));
    }
    
    const tableBody = document.getElementById('classroom-list-body');
    tableBody.innerHTML = '';
    
    if (filteredClassrooms.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="no-data">没有找到符合条件的教室</td></tr>';
        return;
    }
    
    filteredClassrooms.forEach(room => {
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${room.id}</td>
            <td>${room.building}</td>
            <td>${room.roomNumber}</td>
            <td>${room.capacity}</td>
            <td>${room.equipments}</td>
            <td>
                <button class="btn btn-sm" onclick="editClassroom('${room.id}')">编辑</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 显示班级详情
 */
function showClassDetail(classId) {
    const cls = ExtraData.classes.find(c => c.id === classId);
    if (!cls) return;
    
    const course = getCourseById(cls.courseId);
    const teacher = getTeacherById(cls.teacherId);
    const classroom = getClassroomById(cls.classroomId);
    
    const detailContent = document.getElementById('class-detail-content');
    
    // 获取学生列表
    const students = cls.studentList.map(studentId => {
        const student = getStudentById(studentId);
        return student ? student : { id: studentId, name: '未知学生' };
    });
    
    // 设置详情模态框HTML内容
    detailContent.innerHTML = `
        <div class="detail-header">
            <h2>${cls.className}</h2>
        </div>
        <div class="detail-section">
            <h3>基本信息</h3>
            <div class="detail-row">
                <div class="detail-label">班级编号</div>
                <div class="detail-value">${cls.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">所属课程</div>
                <div class="detail-value">${course ? course.courseName : '未知课程'} (${cls.courseId})</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">班主任</div>
                <div class="detail-value">${teacher ? teacher.name : '未分配'} (${cls.teacherId})</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">教室</div>
                <div class="detail-value">${classroom ? `${classroom.building} ${classroom.roomNumber}` : '未分配'} (${cls.classroomId})</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学生人数</div>
                <div class="detail-value">${cls.studentList.length}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">创建时间</div>
                <div class="detail-value">${formatDate(cls.createTime)}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>学生名单</h3>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>专业</th>
                        <th>班级</th>
                    </tr>
                </thead>
                <tbody>
                    ${students.map(student => `
                        <tr>
                            <td>${student.id}</td>
                            <td>${student.name}</td>
                            <td>${student.major || '-'}</td>
                            <td>${student.class || '-'}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        </div>
        
        <div class="detail-actions" style="text-align: right; margin-top: 20px;">
            <button class="btn btn-danger" onclick="deleteClass('${cls.id}')">删除班级</button>
            <button class="btn" onclick="document.getElementById('class-detail-modal').style.display='none'">关闭</button>
        </div>
    `;
    
    // 显示详情模态框
    document.getElementById('class-detail-modal').style.display = 'block';
}

/**
 * 生成班级
 */
function generateClasses() {
    // 获取选中的课程
    const selectedCourses = [];
    document.querySelectorAll('#course-selection input:checked').forEach(checkbox => {
        selectedCourses.push(checkbox.value);
    });
    
    if (selectedCourses.length === 0) {
        showMessage('请至少选择一门课程', 'error');
        return;
    }
    
    const maxClassSize = parseInt(document.getElementById('max-class-size').value);
    if (isNaN(maxClassSize) || maxClassSize < 20 || maxClassSize > 100) {
        showMessage('班级最大人数应在20-100之间', 'error');
        return;
    }
    
    const groupRule = document.querySelector('input[name="group-rule"]:checked').value;
    
    // 在实际应用中，这里会调用API生成班级
    // 这里简单模拟
    showMessage(`已为${selectedCourses.length}门课程生成班级`, 'success');
    
    // 关闭模态框
    document.getElementById('generate-class-modal').style.display = 'none';
    
    // 重新加载班级列表
    setTimeout(() => {
        loadClassListData();
    }, 500);
}

/**
 * 添加班级
 */
function addClass() {
    const className = document.getElementById('class-name').value.trim();
    const courseId = document.getElementById('class-course').value;
    const teacherId = document.getElementById('class-teacher').value;
    const classroomId = document.getElementById('class-classroom').value;
    
    // 验证输入
    if (!className) {
        showMessage('请输入班级名称', 'error');
        return;
    }
    
    if (!courseId) {
        showMessage('请选择课程', 'error');
        return;
    }
    
    if (!teacherId) {
        showMessage('请选择班主任', 'error');
        return;
    }
    
    if (!classroomId) {
        showMessage('请选择教室', 'error');
        return;
    }
    
    // 在实际应用中，这里会调用API添加班级
    // 这里简单模拟
    showMessage('班级添加成功', 'success');
    
    // 关闭模态框
    document.getElementById('add-class-modal').style.display = 'none';
    
    // 重置表单
    document.getElementById('class-name').value = '';
    document.getElementById('class-course').value = '';
    document.getElementById('class-teacher').value = '';
    document.getElementById('class-classroom').value = '';
    
    // 重新加载班级列表
    setTimeout(() => {
        loadClassListData();
    }, 500);
}

/**
 * 删除班级
 */
function deleteClass(classId) {
    if (!confirm('确定要删除这个班级吗？此操作不可撤销。')) {
        return;
    }
    
    // 在实际应用中，这里会调用API删除班级
    // 这里简单模拟
    showMessage('班级已删除', 'success');
    
    // 关闭详情模态框（如果打开的话）
    document.getElementById('class-detail-modal').style.display = 'none';
    
    // 重新加载班级列表
    setTimeout(() => {
        loadClassListData();
    }, 500);
}

/**
 * 编辑教室
 */
function editClassroom(roomId) {
    // 在实际应用中，这里会打开编辑教室的模态框
    // 这里简单模拟
    showMessage('教室编辑功能正在开发中', 'info');
} 