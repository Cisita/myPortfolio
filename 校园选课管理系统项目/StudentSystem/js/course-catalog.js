/**
 * 课程目录页面的JavaScript代码
 */

// 当DOM完全加载后执行
document.addEventListener('DOMContentLoaded', function() {
    // 初始化视图切换
    initViewSwitcher();
    
    // 初始化筛选功能
    initFilters();
    
    // 初始化课程详情模态框
    initCourseDetailModal();
    
    // 加载课程数据
    loadCourseData();
    
    // 检查是否有搜索词从首页传递过来
    checkSearchQuery();
});

/**
 * 初始化视图切换功能
 */
function initViewSwitcher() {
    const cardViewBtn = document.getElementById('card-view-btn');
    const tableViewBtn = document.getElementById('table-view-btn');
    const cardView = document.getElementById('card-view');
    const tableView = document.getElementById('table-view');
    
    cardViewBtn.addEventListener('click', function() {
        cardView.style.display = 'grid';
        tableView.style.display = 'none';
        cardViewBtn.classList.add('active');
        tableViewBtn.classList.remove('active');
    });
    
    tableViewBtn.addEventListener('click', function() {
        cardView.style.display = 'none';
        tableView.style.display = 'block';
        tableViewBtn.classList.add('active');
        cardViewBtn.classList.remove('active');
    });
}

/**
 * 初始化筛选功能
 */
function initFilters() {
    const applyFilterBtn = document.getElementById('apply-filter');
    const resetFilterBtn = document.getElementById('reset-filter');
    const searchInput = document.getElementById('search-course');
    
    // 应用筛选按钮点击事件
    applyFilterBtn.addEventListener('click', function() {
        filterCourses();
    });
    
    // 重置筛选按钮点击事件
    resetFilterBtn.addEventListener('click', function() {
        document.getElementById('search-course').value = '';
        document.getElementById('filter-category').value = '';
        document.getElementById('filter-credit').value = '';
        document.getElementById('filter-day').value = '';
        
        loadCourseData();
    });
    
    // 搜索框回车事件
    searchInput.addEventListener('keyup', function(event) {
        if (event.key === 'Enter') {
            filterCourses();
        }
    });
}

/**
 * 筛选课程
 */
function filterCourses() {
    const searchQuery = document.getElementById('search-course').value.toLowerCase();
    const categoryFilter = document.getElementById('filter-category').value;
    const creditFilter = document.getElementById('filter-credit').value;
    const dayFilter = document.getElementById('filter-day').value;
    
    let filteredCourses = Data.courses;
    
    // 应用搜索查询
    if (searchQuery) {
        filteredCourses = filteredCourses.filter(course => {
            const teacherName = getTeacherById(course.teacherId)?.name || '';
            return course.courseName.toLowerCase().includes(searchQuery) || 
                   teacherName.toLowerCase().includes(searchQuery);
        });
    }
    
    // 应用课程类型筛选
    if (categoryFilter) {
        filteredCourses = filteredCourses.filter(course => course.category === categoryFilter);
    }
    
    // 应用学分筛选
    if (creditFilter) {
        filteredCourses = filteredCourses.filter(course => course.credit === parseInt(creditFilter));
    }
    
    // 应用上课日筛选
    if (dayFilter) {
        filteredCourses = filteredCourses.filter(course => course.timeSlot.startsWith(dayFilter));
    }
    
    // 更新视图
    updateCourseView(filteredCourses);
}

/**
 * 初始化课程详情模态框
 */
function initCourseDetailModal() {
    const modal = document.getElementById('course-detail-modal');
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
 * 加载课程数据
 */
function loadCourseData() {
    updateCourseView(Data.courses);
}

/**
 * 更新课程视图
 */
function updateCourseView(courses) {
    updateCardView(courses);
    updateTableView(courses);
}

/**
 * 更新卡片视图
 */
function updateCardView(courses) {
    const cardView = document.getElementById('card-view');
    cardView.innerHTML = '';
    
    if (courses.length === 0) {
        cardView.innerHTML = '<div class="no-data">没有找到符合条件的课程</div>';
        return;
    }
    
    courses.forEach(course => {
        const teacher = getTeacherById(course.teacherId);
        const remainingSeats = course.maxStudent - course.currentStudent;
        const cardElement = document.createElement('div');
        cardElement.className = 'course-card';
        
        // 设置课程卡片HTML内容
        cardElement.innerHTML = `
            <h3>${course.courseName}</h3>
            <div class="course-info">
                <p>课程编号: <span>${course.id}</span></p>
                <p>学分: <span>${course.credit}</span></p>
                <p>课时: <span>${course.period}</span></p>
                <p>授课教师: <span>${teacher ? teacher.name : '未分配'}</span></p>
                <p>上课时间: <span>${course.timeSlot}</span></p>
                <p>课程类型: <span>${course.category}</span></p>
                <p>剩余名额: <span class="${remainingSeats <= 5 ? 'status-error' : ''}">${remainingSeats}</span>/${course.maxStudent}</p>
            </div>
            <div class="card-footer">
                <button class="btn btn-sm" data-course-id="${course.id}" onclick="showCourseDetail('${course.id}')">详情</button>
                ${currentUser && currentUser.userType === 'student' ? 
                   `<button class="btn btn-primary btn-sm" data-course-id="${course.id}" onclick="addToSelectionCart('${course.id}')">加入选课单</button>` : ''}
            </div>
        `;
        
        cardView.appendChild(cardElement);
    });
}

/**
 * 更新表格视图
 */
function updateTableView(courses) {
    const tableBody = document.getElementById('course-table-body');
    tableBody.innerHTML = '';
    
    if (courses.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = '<td colspan="9" class="no-data">没有找到符合条件的课程</td>';
        tableBody.appendChild(row);
        return;
    }
    
    courses.forEach(course => {
        const teacher = getTeacherById(course.teacherId);
        const remainingSeats = course.maxStudent - course.currentStudent;
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.courseName}</td>
            <td>${course.credit}</td>
            <td>${course.period}</td>
            <td>${teacher ? teacher.name : '未分配'}</td>
            <td>${course.timeSlot}</td>
            <td>${course.category}</td>
            <td class="${remainingSeats <= 5 ? 'status-error' : ''}">${remainingSeats}/${course.maxStudent}</td>
            <td>
                <button class="btn btn-sm" onclick="showCourseDetail('${course.id}')">详情</button>
                ${currentUser && currentUser.userType === 'student' ? 
                   `<button class="btn btn-primary btn-sm" onclick="addToSelectionCart('${course.id}')">选课</button>` : ''}
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 显示课程详情
 */
function showCourseDetail(courseId) {
    const course = getCourseById(courseId);
    if (!course) return;
    
    const teacher = getTeacherById(course.teacherId);
    const classroom = getClassroomById(course.classroomId);
    const remainingSeats = course.maxStudent - course.currentStudent;
    
    // 查找课程的先修课
    const prerequisiteCourses = course.prerequisite.map(preId => {
        const preCourse = getCourseById(preId);
        return preCourse ? preCourse.courseName : '未知';
    }).join(', ') || '无';
    
    const detailContent = document.getElementById('course-detail-content');
    
    // 设置详情模态框HTML内容
    detailContent.innerHTML = `
        <div class="detail-header">
            <h2>${course.courseName}</h2>
        </div>
        <div class="detail-section">
            <div class="detail-row">
                <div class="detail-label">课程编号</div>
                <div class="detail-value">${course.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学分</div>
                <div class="detail-value">${course.credit}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">课时</div>
                <div class="detail-value">${course.period}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">课程类型</div>
                <div class="detail-value">${course.category}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">先修课程</div>
                <div class="detail-value">${prerequisiteCourses}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">授课教师</div>
                <div class="detail-value">${teacher ? teacher.name : '未分配'}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">上课时间</div>
                <div class="detail-value">${course.timeSlot}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">上课地点</div>
                <div class="detail-value">${classroom ? `${classroom.building} ${classroom.roomNumber}` : '未分配'}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">剩余名额</div>
                <div class="detail-value ${remainingSeats <= 5 ? 'status-error' : ''}">${remainingSeats}/${course.maxStudent}</div>
            </div>
        </div>
        <div class="detail-section">
            <h3>课程简介</h3>
            <p>${course.description || '暂无简介'}</p>
        </div>
        <div class="detail-actions" style="text-align: right; margin-top: 20px;">
            ${currentUser && currentUser.userType === 'student' ? 
               `<button class="btn btn-primary" onclick="addToSelectionCart('${course.id}')">加入选课单</button>` : ''}
            <button class="btn" onclick="document.getElementById('course-detail-modal').style.display='none'">关闭</button>
        </div>
    `;
    
    // 显示详情模态框
    document.getElementById('course-detail-modal').style.display = 'block';
}

/**
 * 添加到选课单
 */
function addToSelectionCart(courseId) {
    if (!checkLogin()) return;
    
    if (currentUser.userType !== 'student') {
        showMessage('只有学生可以选课', 'error');
        return;
    }
    
    // 在实际应用中，这里会与后端交互
    // 这里只是模拟
    const course = getCourseById(courseId);
    const remainingSeats = course.maxStudent - course.currentStudent;
    
    if (remainingSeats <= 0) {
        showMessage('该课程已满员', 'error');
        return;
    }
    
    // 检查是否已经选过该课程
    const isAlreadySelected = Data.studentCourses.some(record => 
        record.studentId === currentUser.id && 
        record.courseId === courseId && 
        record.status === 1
    );
    
    if (isAlreadySelected) {
        showMessage('你已经选过该课程', 'warning');
        return;
    }
    
    // 检查先修课
    if (!checkPrerequisites(courseId)) {
        return;
    }
    
    // 模拟添加到选课单
    // 在真实应用中，这里会调用API进行选课操作
    showMessage('课程已添加到选课单', 'success');
    
    // 可以跳转到选课管理页面
    setTimeout(() => {
        window.location.href = 'course-selection.html';
    }, 1500);
}

/**
 * 检查先修课程要求
 */
function checkPrerequisites(courseId) {
    const course = getCourseById(courseId);
    
    if (!course.prerequisite || course.prerequisite.length === 0) {
        return true;
    }
    
    // 获取学生已选课程并已通过的课程ID列表
    // 在真实应用中，这里需要从后端获取学生的成绩信息
    // 这里简单模拟
    const passedCourses = [];
    Data.studentCourses.forEach(record => {
        if (record.studentId === currentUser.id && record.status === 1) {
            passedCourses.push(record.courseId);
        }
    });
    
    const missingPrerequisites = course.prerequisite.filter(preId => !passedCourses.includes(preId));
    
    if (missingPrerequisites.length > 0) {
        const missingCourseNames = missingPrerequisites.map(preId => {
            const preCourse = getCourseById(preId);
            return preCourse ? preCourse.courseName : preId;
        }).join(', ');
        
        showMessage(`选课失败，缺少先修课程: ${missingCourseNames}`, 'error');
        return false;
    }
    
    return true;
}

/**
 * 检查是否有搜索查询
 */
function checkSearchQuery() {
    const lastSearchQuery = localStorage.getItem('lastSearchQuery');
    
    if (lastSearchQuery) {
        // 填充搜索框
        document.getElementById('search-course').value = lastSearchQuery;
        
        // 执行搜索
        filterCourses();
        
        // 清除存储的搜索词
        localStorage.removeItem('lastSearchQuery');
    }
} 