/**
 * 补课退课管理页面的JavaScript代码
 */

// 当前审核的申请ID
let currentReviewApplyId = null;

// 当DOM完全加载后执行
document.addEventListener('DOMContentLoaded', function() {
    // 检查用户是否已登录
    if (!checkLogin()) return;
    
    // 初始化标签页切换
    initTabs();
    
    // 初始化模态框
    initModals();
    
    // 初始化按钮事件
    initButtons();
    
    // 加载申请记录数据
    loadApplyRecordsData();
    
    // 如果用户是管理员，加载待审核申请数据
    if (currentUser.userType === 'admin') {
        loadReviewApplicationsData();
    } else {
        // 隐藏申请审核标签
        document.getElementById('review-tab').style.display = 'none';
    }
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
    // 初始化申请详情模态框
    initApplyDetailModal();
    
    // 初始化新建申请模态框
    initNewApplyModal();
    
    // 初始化审核申请模态框
    initReviewApplyModal();
}

/**
 * 初始化申请详情模态框
 */
function initApplyDetailModal() {
    const modal = document.getElementById('apply-detail-modal');
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
 * 初始化新建申请模态框
 */
function initNewApplyModal() {
    const modal = document.getElementById('new-apply-modal');
    const closeBtn = modal.querySelector('.close');
    const cancelBtn = document.getElementById('cancel-apply-btn');
    const submitBtn = document.getElementById('submit-apply-btn');
    const courseSelect = document.getElementById('course-select');
    
    // 填充课程选项
    populateCourseOptions();
    
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
        submitNewApply();
    });
    
    // 申请类型变更时更新课程选项
    const applyTypeRadios = document.querySelectorAll('input[name="apply-type"]');
    applyTypeRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            populateCourseOptions();
        });
    });
    
    // 点击模态框外部关闭模态框
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

/**
 * 初始化审核申请模态框
 */
function initReviewApplyModal() {
    const modal = document.getElementById('review-apply-modal');
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
 * 初始化按钮事件
 */
function initButtons() {
    // 新建申请按钮
    const newApplyBtn = document.getElementById('new-apply-btn');
    newApplyBtn.addEventListener('click', function() {
        document.getElementById('new-apply-modal').style.display = 'block';
    });
}

/**
 * 加载申请记录数据
 */
function loadApplyRecordsData() {
    // 获取当前用户的申请记录
    let applyRecords = [];
    
    if (currentUser.userType === 'student') {
        // 学生查看自己的申请记录
        applyRecords = ExtraData.applyForms.filter(record => record.studentId === currentUser.id);
    } else if (currentUser.userType === 'admin') {
        // 管理员查看所有申请记录
        applyRecords = ExtraData.applyForms;
    }
    
    const tableBody = document.getElementById('apply-records-body');
    tableBody.innerHTML = '';
    
    if (applyRecords.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="7" class="no-data">暂无申请记录</td></tr>';
        return;
    }
    
    applyRecords.forEach(record => {
        const course = getCourseById(record.courseId);
        if (!course) return;
        
        const row = document.createElement('tr');
        
        // 设置状态样式
        let statusClass = '';
        let statusText = '';
        switch (record.status) {
            case 0:
                statusClass = 'status-pending';
                statusText = '待审核';
                break;
            case 1:
                statusClass = 'status-success';
                statusText = '已通过';
                break;
            case 2:
                statusClass = 'status-error';
                statusText = '已驳回';
                break;
        }
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${record.id}</td>
            <td>${course.courseName}</td>
            <td>${record.type === 0 ? '补选' : '退选'}</td>
            <td>${formatDate(record.applyTime)}</td>
            <td><span class="status-tag ${statusClass}">${statusText}</span></td>
            <td>${record.reviewTime ? formatDate(record.reviewTime) : '-'}</td>
            <td>
                <button class="btn btn-sm" onclick="showApplyDetail('${record.id}')">详情</button>
                ${record.status === 0 ? `<button class="btn btn-danger btn-sm" onclick="cancelApply('${record.id}')">取消</button>` : ''}
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 加载待审核申请数据
 */
function loadReviewApplicationsData() {
    // 获取所有待审核的申请
    const pendingApplies = ExtraData.applyForms.filter(record => record.status === 0);
    
    const tableBody = document.getElementById('review-applications-body');
    tableBody.innerHTML = '';
    
    if (pendingApplies.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="no-data">暂无待审核的申请</td></tr>';
        return;
    }
    
    pendingApplies.forEach(record => {
        const student = getStudentById(record.studentId);
        const course = getCourseById(record.courseId);
        if (!student || !course) return;
        
        const row = document.createElement('tr');
        
        // 设置表格行HTML内容
        row.innerHTML = `
            <td>${record.id}</td>
            <td>${student.name}</td>
            <td>${course.courseName}</td>
            <td>${record.type === 0 ? '补选' : '退选'}</td>
            <td>${formatDate(record.applyTime)}</td>
            <td>
                <button class="btn btn-primary btn-sm" onclick="showReviewApply('${record.id}')">审核</button>
            </td>
        `;
        
        tableBody.appendChild(row);
    });
}

/**
 * 显示申请详情
 */
function showApplyDetail(applyId) {
    const record = ExtraData.applyForms.find(r => r.id === applyId);
    if (!record) return;
    
    const student = getStudentById(record.studentId);
    const course = getCourseById(record.courseId);
    if (!student || !course) return;
    
    const detailContent = document.getElementById('apply-detail-content');
    
    // 设置状态样式
    let statusClass = '';
    let statusText = '';
    switch (record.status) {
        case 0:
            statusClass = 'status-pending';
            statusText = '待审核';
            break;
        case 1:
            statusClass = 'status-success';
            statusText = '已通过';
            break;
        case 2:
            statusClass = 'status-error';
            statusText = '已驳回';
            break;
    }
    
    // 设置详情模态框HTML内容
    detailContent.innerHTML = `
        <div class="detail-header">
            <h2>${record.type === 0 ? '补选' : '退选'}申请详情</h2>
        </div>
        <div class="detail-section">
            <div class="detail-row">
                <div class="detail-label">申请编号</div>
                <div class="detail-value">${record.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">申请状态</div>
                <div class="detail-value"><span class="status-tag ${statusClass}">${statusText}</span></div>
            </div>
            <div class="detail-row">
                <div class="detail-label">申请时间</div>
                <div class="detail-value">${formatDate(record.applyTime)}</div>
            </div>
            ${record.reviewTime ? `
            <div class="detail-row">
                <div class="detail-label">审核时间</div>
                <div class="detail-value">${formatDate(record.reviewTime)}</div>
            </div>` : ''}
        </div>
        
        <div class="detail-section">
            <h3>申请人信息</h3>
            <div class="detail-row">
                <div class="detail-label">学生姓名</div>
                <div class="detail-value">${student.name}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学号</div>
                <div class="detail-value">${student.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">专业</div>
                <div class="detail-value">${student.major}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">班级</div>
                <div class="detail-value">${student.class}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>课程信息</h3>
            <div class="detail-row">
                <div class="detail-label">课程名称</div>
                <div class="detail-value">${course.courseName}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">课程编号</div>
                <div class="detail-value">${course.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学分</div>
                <div class="detail-value">${course.credit}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">上课时间</div>
                <div class="detail-value">${course.timeSlot}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>申请理由</h3>
            <p>${record.reason}</p>
        </div>
        
        <div class="detail-section">
            <h3>附件材料</h3>
            <div class="attachment-list">
                ${record.attachments && record.attachments.length > 0 ? 
                    record.attachments.map(att => `<div class="attachment-item"><i class="bi bi-file-earmark"></i> <a href="${att}" target="_blank">${att.split('/').pop()}</a></div>`).join('') :
                    '<p>无附件</p>'}
            </div>
        </div>
        
        ${record.status !== 0 ? `
        <div class="detail-section">
            <h3>审核结果</h3>
            <div class="detail-row">
                <div class="detail-label">审核人</div>
                <div class="detail-value">${record.reviewer || '-'}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">审核意见</div>
                <div class="detail-value">${record.reviewReason || '-'}</div>
            </div>
        </div>` : ''}
        
        <div class="detail-actions" style="text-align: right; margin-top: 20px;">
            <button class="btn" onclick="document.getElementById('apply-detail-modal').style.display='none'">关闭</button>
        </div>
    `;
    
    // 显示详情模态框
    document.getElementById('apply-detail-modal').style.display = 'block';
}

/**
 * 取消申请
 */
function cancelApply(applyId) {
    // 在实际应用中，这里会调用API取消申请
    // 这里简单模拟
    showMessage('申请已取消', 'success');
    
    // 重新加载申请记录数据
    setTimeout(() => {
        loadApplyRecordsData();
    }, 500);
}

/**
 * 填充课程选项
 */
function populateCourseOptions() {
    const applyType = document.querySelector('input[name="apply-type"]:checked').value;
    const courseSelect = document.getElementById('course-select');
    courseSelect.innerHTML = '<option value="">-- 请选择课程 --</option>';
    
    if (currentUser.userType !== 'student') return;
    
    let courses = [];
    
    if (applyType === '0') {
        // 补选课程 - 显示学生尚未选择的课程
        const selectedCourseIds = Data.studentCourses
            .filter(record => record.studentId === currentUser.id && record.status === 1)
            .map(record => record.courseId);
        
        courses = Data.courses.filter(course => !selectedCourseIds.includes(course.id));
    } else {
        // 退选课程 - 显示学生已选择的课程
        const selectedCourseIds = Data.studentCourses
            .filter(record => record.studentId === currentUser.id && record.status === 1)
            .map(record => record.courseId);
        
        courses = Data.courses.filter(course => selectedCourseIds.includes(course.id));
    }
    
    courses.forEach(course => {
        const option = document.createElement('option');
        option.value = course.id;
        option.textContent = `${course.courseName} (${course.id})`;
        courseSelect.appendChild(option);
    });
}

/**
 * 提交新申请
 */
function submitNewApply() {
    const applyType = document.querySelector('input[name="apply-type"]:checked').value;
    const courseId = document.getElementById('course-select').value;
    const reason = document.getElementById('reason-textarea').value.trim();
    const attachmentInput = document.getElementById('attachment-input');
    
    // 验证输入
    if (!courseId) {
        showMessage('请选择课程', 'error');
        return;
    }
    
    if (!reason) {
        showMessage('请填写申请理由', 'error');
        return;
    }
    
    // 在实际应用中，这里会上传附件并调用API提交申请
    // 这里简单模拟
    
    // 模拟上传附件的文件名
    let attachments = [];
    if (attachmentInput.files.length > 0) {
        for (let i = 0; i < Math.min(attachmentInput.files.length, 3); i++) {
            const file = attachmentInput.files[i];
            attachments.push(`uploads/apply/${currentUser.id}_${courseId}_${i+1}.${file.name.split('.').pop()}`);
        }
    }
    
    showMessage('申请已提交，等待审核', 'success');
    
    // 关闭模态框
    document.getElementById('new-apply-modal').style.display = 'none';
    
    // 重置表单
    document.getElementById('course-select').value = '';
    document.getElementById('reason-textarea').value = '';
    document.getElementById('attachment-input').value = '';
    
    // 重新加载申请记录数据
    setTimeout(() => {
        loadApplyRecordsData();
    }, 500);
}

/**
 * 显示审核申请界面
 */
function showReviewApply(applyId) {
    const record = ExtraData.applyForms.find(r => r.id === applyId);
    if (!record) return;
    
    currentReviewApplyId = applyId;
    
    const student = getStudentById(record.studentId);
    const course = getCourseById(record.courseId);
    if (!student || !course) return;
    
    const reviewContent = document.getElementById('review-apply-content');
    
    // 设置审核模态框HTML内容
    reviewContent.innerHTML = `
        <div class="detail-section">
            <h3>申请信息</h3>
            <div class="detail-row">
                <div class="detail-label">申请编号</div>
                <div class="detail-value">${record.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">申请类型</div>
                <div class="detail-value">${record.type === 0 ? '补选' : '退选'}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">申请时间</div>
                <div class="detail-value">${formatDate(record.applyTime)}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>学生信息</h3>
            <div class="detail-row">
                <div class="detail-label">学生姓名</div>
                <div class="detail-value">${student.name}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学号</div>
                <div class="detail-value">${student.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">专业</div>
                <div class="detail-value">${student.major}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">班级</div>
                <div class="detail-value">${student.class}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>课程信息</h3>
            <div class="detail-row">
                <div class="detail-label">课程名称</div>
                <div class="detail-value">${course.courseName}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">课程编号</div>
                <div class="detail-value">${course.id}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">学分</div>
                <div class="detail-value">${course.credit}</div>
            </div>
            <div class="detail-row">
                <div class="detail-label">上课时间</div>
                <div class="detail-value">${course.timeSlot}</div>
            </div>
        </div>
        
        <div class="detail-section">
            <h3>申请理由</h3>
            <p>${record.reason}</p>
        </div>
        
        <div class="detail-section">
            <h3>附件材料</h3>
            <div class="attachment-list">
                ${record.attachments && record.attachments.length > 0 ? 
                    record.attachments.map(att => `<div class="attachment-item"><i class="bi bi-file-earmark"></i> <a href="${att}" target="_blank">${att.split('/').pop()}</a></div>`).join('') :
                    '<p>无附件</p>'}
            </div>
        </div>
        
        <div class="detail-section">
            <h3>审核决定</h3>
            <div class="form-row">
                <label class="form-label">审核结果</label>
                <div class="radio-group">
                    <input type="radio" id="review-approve" name="review-result" value="1" checked>
                    <label for="review-approve">通过</label>
                    <input type="radio" id="review-reject" name="review-result" value="2">
                    <label for="review-reject">驳回</label>
                </div>
            </div>
            <div class="form-row">
                <label class="form-label" for="review-reason">审核意见</label>
                <textarea id="review-reason" class="form-textarea" placeholder="请填写审核意见..."></textarea>
            </div>
        </div>
        
        <div class="detail-actions" style="text-align: right; margin-top: 20px;">
            <button class="btn btn-primary" onclick="submitReview()">提交审核</button>
            <button class="btn" onclick="document.getElementById('review-apply-modal').style.display='none'">取消</button>
        </div>
    `;
    
    // 显示审核模态框
    document.getElementById('review-apply-modal').style.display = 'block';
}

/**
 * 提交审核结果
 */
function submitReview() {
    const reviewResult = document.querySelector('input[name="review-result"]:checked').value;
    const reviewReason = document.getElementById('review-reason').value.trim();
    
    if (!reviewReason) {
        showMessage('请填写审核意见', 'error');
        return;
    }
    
    // 在实际应用中，这里会调用API提交审核结果
    // 这里简单模拟
    showMessage(`审核已提交，结果为${reviewResult === '1' ? '通过' : '驳回'}`, 'success');
    
    // 关闭模态框
    document.getElementById('review-apply-modal').style.display = 'none';
    
    // 重新加载数据
    setTimeout(() => {
        loadApplyRecordsData();
        loadReviewApplicationsData();
    }, 500);
} 