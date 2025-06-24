/**
 * 学生课程管理系统主JavaScript文件
 */

// 当DOM完全加载后执行
document.addEventListener('DOMContentLoaded', function() {
    // 初始化登录模态框
    initLoginModal();
    
    // 初始化搜索功能
    initSearchFunction();
    
    // 从本地存储中恢复用户登录状态
    restoreUserSession();
});

/**
 * 初始化登录模态框
 */
function initLoginModal() {
    const loginBtn = document.getElementById('login-btn');
    const logoutBtn = document.getElementById('logout-btn');
    const modal = document.getElementById('login-modal');
    const closeBtn = modal.querySelector('.close');
    const loginSubmit = document.getElementById('login-submit');
    
    // 点击登录按钮打开模态框
    if (loginBtn) {
        loginBtn.addEventListener('click', function() {
            modal.style.display = 'block';
        });
    }
    
    // 点击关闭按钮关闭模态框
    if (closeBtn) {
        closeBtn.addEventListener('click', function() {
            modal.style.display = 'none';
        });
    }
    
    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    // 点击登录提交按钮
    if (loginSubmit) {
        loginSubmit.addEventListener('click', function() {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const userType = document.querySelector('input[name="userType"]:checked').value;
            
            if (validateLogin(username, password, userType)) {
                performLogin(username, password, userType);
            }
        });
    }
    
    // 点击退出按钮
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            logout();
        });
    }
}

/**
 * 验证登录信息
 */
function validateLogin(username, password, userType) {
    if (!username || !password) {
        showMessage('用户名和密码不能为空', 'error');
        return false;
    }
    return true;
}

/**
 * 执行登录操作
 */
function performLogin(username, password, userType) {
    let user = null;
    
    // 根据用户类型查找用户
    if (userType === 'student') {
        user = Data.students.find(student => student.id === username && student.password === password);
    } else if (userType === 'teacher') {
        user = Data.teachers.find(teacher => teacher.id === username && teacher.password === password);
    } else if (userType === 'admin') {
        user = ExtraData.admins.find(admin => admin.id === username && admin.password === password);
    }
    
    if (user) {
        // 登录成功
        user.userType = userType;
        currentUser = user;
        
        // 保存到本地存储
        localStorage.setItem('currentUser', JSON.stringify(currentUser));
        
        // 更新UI
        updateUserUI();
        
        // 关闭模态框
        document.getElementById('login-modal').style.display = 'none';
        
        showMessage(`欢迎回来，${user.name}！`, 'success');
    } else {
        // 登录失败
        showMessage('用户名或密码错误', 'error');
    }
}

/**
 * 退出登录
 */
function logout() {
    currentUser = null;
    localStorage.removeItem('currentUser');
    updateUserUI();
    showMessage('您已成功退出登录', 'success');
}

/**
 * 更新用户界面
 */
function updateUserUI() {
    const currentUserSpan = document.getElementById('current-user');
    const loginBtn = document.getElementById('login-btn');
    const logoutBtn = document.getElementById('logout-btn');
    
    if (currentUser) {
        currentUserSpan.textContent = `当前用户: ${currentUser.name} (${getUserTypeText(currentUser.userType)})`;
        loginBtn.style.display = 'none';
        logoutBtn.style.display = 'inline-block';
    } else {
        currentUserSpan.textContent = '未登录';
        loginBtn.style.display = 'inline-block';
        logoutBtn.style.display = 'none';
    }
}

/**
 * 获取用户类型的中文描述
 */
function getUserTypeText(userType) {
    switch (userType) {
        case 'student':
            return '学生';
        case 'teacher':
            return '教师';
        case 'admin':
            return '管理员';
        default:
            return '未知';
    }
}

/**
 * 恢复用户会话
 */
function restoreUserSession() {
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
        currentUser = JSON.parse(savedUser);
        updateUserUI();
    }
}

/**
 * 初始化搜索功能
 */
function initSearchFunction() {
    const searchBtn = document.getElementById('search-btn');
    const globalSearch = document.getElementById('global-search');
    
    if (searchBtn && globalSearch) {
        searchBtn.addEventListener('click', function() {
            const query = globalSearch.value.trim();
            
            if (query) {
                // 保存搜索词到localStorage
                localStorage.setItem('lastSearchQuery', query);
                
                // 跳转到课程目录页面
                window.location.href = 'pages/course-catalog.html';
            } else {
                showMessage('请输入搜索内容', 'warning');
            }
        });
        
        // 回车键触发搜索
        globalSearch.addEventListener('keyup', function(event) {
            if (event.key === 'Enter') {
                searchBtn.click();
            }
        });
    }
}

/**
 * 显示消息提示
 */
function showMessage(message, type = 'info') {
    // 创建消息元素
    const messageDiv = document.createElement('div');
    messageDiv.className = `message-toast ${type}`;
    messageDiv.textContent = message;
    
    // 添加到页面
    document.body.appendChild(messageDiv);
    
    // 显示消息
    setTimeout(() => {
        messageDiv.classList.add('show');
    }, 10);
    
    // 3秒后自动关闭
    setTimeout(() => {
        messageDiv.classList.remove('show');
        setTimeout(() => {
            document.body.removeChild(messageDiv);
        }, 300);
    }, 3000);
}

/**
 * 格式化日期
 */
function formatDate(dateStr) {
    if (!dateStr) return '';
    
    const date = new Date(dateStr.replace(' ', 'T'));
    return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(date.getDate())} ${padZero(date.getHours())}:${padZero(date.getMinutes())}`;
}

/**
 * 在数字前补零
 */
function padZero(num) {
    return num < 10 ? '0' + num : num;
}

/**
 * 获取URL参数
 */
function getUrlParam(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

/**
 * 获取课程信息
 */
function getCourseById(courseId) {
    return Data.courses.find(course => course.id === courseId);
}

/**
 * 获取教师信息
 */
function getTeacherById(teacherId) {
    return Data.teachers.find(teacher => teacher.id === teacherId);
}

/**
 * 获取学生信息
 */
function getStudentById(studentId) {
    return Data.students.find(student => student.id === studentId);
}

/**
 * 获取教室信息
 */
function getClassroomById(classroomId) {
    return Data.classrooms.find(classroom => classroom.id === classroomId);
}

/**
 * 检查用户是否已登录
 */
function checkLogin() {
    if (!currentUser) {
        showMessage('请先登录', 'error');
        setTimeout(() => {
            window.location.href = '../index.html';
        }, 1500);
        return false;
    }
    return true;
}

/**
 * 检查用户类型
 */
function checkUserType(allowedTypes) {
    if (!currentUser) return false;
    
    if (!Array.isArray(allowedTypes)) {
        allowedTypes = [allowedTypes];
    }
    
    return allowedTypes.includes(currentUser.userType);
} 