/**
 * 选课管理页面的JavaScript代码
 */

// 当前选中的待选课程ID
let selectedCourseIds = [];

// 当前准备退课的课程记录ID
let withdrawCourseId = null;

// 模拟选课单数据（在实际应用中这应该保存在后端）
// 这里简单模拟用户的选课单
const selectionCart = [];

// 当DOM完全加载后执行
document.addEventListener('DOMContentLoaded', function() {
    // 检查用户是否已登录
    if (!checkLogin()) return;
    
    // 检查用户是否为学生
    if (currentUser.userType !== 'student') {
        showMessage('只有学生可以使用选课功能', 'error');
        setTimeout(() => {
            window.location.href = '../index.html';
        }, 1500);
        return;
    }
    
    // 初始化标签页切换
    initTabs();
    
    // 初始化退课确认模态框
    initWithdrawModal();
    
    // 初始化按钮事件
    initButtons();
    
    // 加载选课单数据
    loadSelectionCartData();
    
    // 加载已选课程数据
    loadSelectedCoursesData();
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
 * 初始化退课确认模态框
 */
function initWithdrawModal() {
    const modal = document.getElementById('withdraw-confirm-modal');
    const closeBtn = modal.querySelector('.close');
    const cancelBtn = document.getElementById('withdraw-cancel-btn');
    const confirmBtn = document.getElementById('withdraw-confirm-btn');
    
    // 点击关闭按钮关闭模态框
    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击取消按钮关闭模态框
    cancelBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });
    
    // 点击确认按钮执行退课
    confirmBtn.addEventListener('click', function() {
        if (withdrawCourseId) {
            withdrawCourse(withdrawCourseId);
            modal.style.display = 'none';
        }
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
    // 全选按钮
    const selectAllCheckbox = document.getElementById('select-all');
    selectAllCheckbox.addEventListener('change', function() {
        const checkboxes = document.querySelectorAll('#selection-cart-body input[type="checkbox"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = this.checked;
        });
        
        // 更新选中的课程ID列表
        updateSelectedCourseIds();
    });
    
    // 提交选课按钮
    const submitSelectionBtn = document.getElementById('submit-selection-btn');
    submitSelectionBtn.addEventListener('click', function() {
        submitSelection();
    });
    
    // 前往课程目录按钮
    const goToCatalogBtn = document.getElementById('go-to-catalog-btn');
    goToCatalogBtn.addEventListener('click', function() {
        window.location.href = 'course-catalog.html';
    });
}

/**
 * 加载选课单数据
 */
function loadSelectionCartData() {
    // 在实际应用中，这里会从后端API获取数据
    // 这里简单模拟
    populateSelectionCart();
}

/**
 * 加载已选课程数据
 */
function loadSelectedCoursesData() {
    // 获取当前学生已选课程记录
    const studentCourses = Data.studentCourses.filter(record => 
        record.studentId === currentUser.id && 
        record.status === 1 // 状态为已选
    );
    
    const tableBody = document.getElementById('selected-courses-body');
    tableBody.innerHTML = '';
    
    if (studentCourses.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="7" class="no-data">暂无已选课程</td></tr>';
        return;
    }
    
    studentCourses.forEach(record => {
        const course = getCourseById(record.courseId);
        if (!course) return;
        
        const teacher = getTeacherById(course.teacherId);
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${course.id}</td>
            <td>${course.courseName}</td>
            <td>${course.credit}</td>
            <td>${teacher ? teacher.name : '未分配'}</td>
            <td>${course.timeSlot}</td>
            <td>${formatDate(record.applyTime)}</td>
            <td>
                <button class="btn btn-danger btn-sm" onclick="showWithdrawConfirm('${record.id}')">退课</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 填充选课单
 */
function populateSelectionCart() {
    const tableBody = document.getElementById('selection-cart-body');
    tableBody.innerHTML = '';
    
    // 这里简单模拟，实际应用中会从后端获取选课单数据
    // 这里使用前5门课程作为示例
    const sampleCourses = Data.courses.slice(0, 5);
    
    if (sampleCourses.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="8" class="no-data">选课单中没有课程</td></tr>';
        return;
    }
    
    sampleCourses.forEach(course => {
        const teacher = getTeacherById(course.teacherId);
        const remainingSeats = course.maxStudent - course.currentStudent;
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td><input type="checkbox" class="course-checkbox" data-course-id="${course.id}"></td>
            <td>${course.id}</td>
            <td>${course.courseName}</td>
            <td>${course.credit}</td>
            <td>${teacher ? teacher.name : '未分配'}</td>
            <td>${course.timeSlot}</td>
            <td class="${remainingSeats <= 5 ? 'status-error' : ''}">${remainingSeats}/${course.maxStudent}</td>
            <td>
                <button class="btn btn-sm" onclick="showCourseDetail('${course.id}')">详情</button>
                <button class="btn btn-danger btn-sm" onclick="removeFromCart('${course.id}')">移除</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
    
    // 添加复选框事件
    const checkboxes = document.querySelectorAll('.course-checkbox');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            updateSelectedCourseIds();
        });
    });
}

/**
 * 更新选中的课程ID列表
 */
function updateSelectedCourseIds() {
    const checkboxes = document.querySelectorAll('.course-checkbox:checked');
    selectedCourseIds = Array.from(checkboxes).map(checkbox => checkbox.getAttribute('data-course-id'));
}

/**
 * 从选课单中移除课程
 */
function removeFromCart(courseId) {
    // 在实际应用中，这里会调用API从选课单中移除课程
    // 这里简单模拟
    showMessage(`已从选课单中移除课程`, 'success');
    // 重新加载选课单数据
    setTimeout(() => {
        loadSelectionCartData();
    }, 500);
}

/**
 * 提交选课
 */
function submitSelection() {
    if (selectedCourseIds.length === 0) {
        showMessage('请先选择要提交的课程', 'warning');
        return;
    }
    
    // 在实际应用中，这里会调用API提交选课
    // 这里简单模拟
    showMessage(`已成功选择 ${selectedCourseIds.length} 门课程`, 'success');
    
    // 重新加载数据
    setTimeout(() => {
        loadSelectionCartData();
        loadSelectedCoursesData();
        
        // 切换到已选课程标签页
        document.querySelector('.nav-tab[data-tab="selected-courses"]').click();
    }, 1000);
}

/**
 * 显示退课确认对话框
 */
function showWithdrawConfirm(recordId) {
    // 查找选课记录
    const record = Data.studentCourses.find(r => r.id === recordId);
    if (!record) return;
    
    // 查找课程信息
    const course = getCourseById(record.courseId);
    if (!course) return;
    
    withdrawCourseId = recordId;
    
    // 显示课程信息
    const courseInfoDiv = document.getElementById('withdraw-course-info');
    courseInfoDiv.innerHTML = `
        <div class="course-info-item">
            <strong>课程名称:</strong> ${course.courseName}
        </div>
        <div class="course-info-item">
            <strong>课程编号:</strong> ${course.id}
        </div>
        <div class="course-info-item">
            <strong>学分:</strong> ${course.credit}
        </div>
        <div class="course-info-item">
            <strong>上课时间:</strong> ${course.timeSlot}
        </div>
        <p class="warning-text" style="color: var(--danger-color); margin-top: 10px;">
            注意：退课后需要重新选课才能再次加入该课程。
        </p>
    `;
    
    // 显示模态框
    document.getElementById('withdraw-confirm-modal').style.display = 'block';
}

/**
 * 退课操作
 */
function withdrawCourse(recordId) {
    // 在实际应用中，这里会调用API执行退课操作
    // 这里简单模拟
    showMessage('退课成功', 'success');
    
    // 重新加载已选课程数据
    setTimeout(() => {
        loadSelectedCoursesData();
    }, 500);
}

/**
 * 显示课程详情
 */
function showCourseDetail(courseId) {
    // 重用课程目录页面的函数，这里简单显示一个消息
    showMessage('查看课程详情功能已启用', 'info');
    
    // 在实际应用中，可以打开一个模态框显示课程详情
    // 或者跳转到课程详情页面
    window.open(`course-catalog.html?courseId=${courseId}`, '_blank');
} 