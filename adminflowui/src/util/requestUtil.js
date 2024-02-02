import instance from "@/util/axiosUtil";

const request = {
    // 用户名登录
    login(username, password, remember) {
        return instance({
            method: 'get',
            url: '/login',
            params: {
                username: username,
                password: password,
                remember: remember
            }
        })
    }
    ,
    // 手机号登录
    loginByPhone(phone,code,remember){
        return instance({
            method: 'get',
            url: '/loginByPhone',
            params: {
                phone: phone,
                code: code,
                remember: remember
            }
        })
    }
    ,
    // 退出登录
    loginOut(id) {
        return instance({
            method: 'get',
            url: '/loginOut',
            params: {
                id: id
            }
        })
    },
    // 渲染每天登录人数
    getCount() {
        return instance({
            method: 'get',
            url: '/getCount',
        })
    }
    ,
    // 初始化登录日志格式数据
    initLogData(keyword, offset, pageSize) {
        return instance({
            method: 'get',
            url: '/log/getAllLog',
            params: {
                keyword: keyword,
                offset: offset,
                pageSize: pageSize
            }
        })
    },
    // 删除日志
    deleteLog() {
        return instance({
            method: 'get',
            url: '/log/deleteLog',
        })
    },
    // 组织架构的请求
    initOrganization() {
        return instance({
            method: 'get',
            url: '/Organization/',
        })
    },
    // 初始化所有用户
    initAllUser(user) {
        return instance({
            method: 'post',
            url: '/getAllUser',
            headers: {
                'Content-Type': 'application/json'
            },
            data: user
        })
    },
    // 修改用户
    updateUser(user) {
        return instance({
            method: 'post',
            url: '/updateUser',
            headers: {
                'Content-Type': 'application/json'
            },
            data: user
        })
    },
    // 删除用户
    deleteUser(id) {
        return instance({
            method: 'get',
            url: '/deleteUser',
            params: {
                id: id
            }
        })
    },
    // 添加用户
    addUser(user) {
        return instance({
            method: 'post',
            url: '/addUser',
            headers: {
                'Content-Type': 'application/json'
            },
            data: user
        })
    },
    // 获取所有角色
    getAllRoles() {
        return instance({
            method: 'get',
            url: '/getAllRole',
        })
    },
    // 添加角色
    addRole(role){
        return instance({
            method:'post',
            url:'/addRole',
            headers: {
                'Content-Type': 'application/json'
            },
            data:role
        })
    },
    // 删除角色
    deleteRole(id){
        return instance({
            method:'get',
            url:'/deleteRole',
            params: {
                id:id
            }
        })
    },
    // 修改用户角色
    updateUserRole(rid,id){
        return instance({
            method:'post',
            url:'/updateUserRole',
            params: {
                rid:rid,
                id:id
            }
        })
    },
    // 修改角色
    updateRole(role){
        return instance({
            method:'post',
            url:'/updateRole',
            data:role,
            headers: {
                'Content-Type': 'application/json'
            },
        })
    },
    // 修改用户
    updatePassword(user) {
        return instance({
            method: 'post',
            url: '/updatePassword',
            headers: {
                'Content-Type': 'application/json'
            },
            data: user
        })
    },
    // 根据用户id获取用户
    getUserById(id){
        return instance({
            method: 'get',
            url: '/getUserById',
            params: {
                id:id
            }
        })
    },
    // 用户验证码登录 获取短信验证码
    getCode(phone){
        return instance({
            method:'get',
            url:'/getVerification',
            params:{
                phone:phone
            }
        })
    },
    // 获取除当前用户外的所有用户
    WithOutUser(id){
        return instance({
            method:'get',
            url:'/WithOutUser',
            params:{
                id:id
            }
        })
    },
    // 获取消息
    MessageInit(id){
        return instance({
            method:'get',
            url:'/messageInit',
            params:{
                mid:id
            }
        })
    },
    // 发送消息
    sendMess(sid,rid,content){
        return instance({
            method:'post',
            url:'/sendMessage',
            params:{
                sid:sid,
                rid:rid,
                content:content
            }
        })
    },
    // 修改消息状态是否已读
    upState(id){
        return instance({
            method:'get',
            url:'/updateState',
            params:{
                id:id
            }
        })
    },
    // 删除消息
    deleteMessage(id,uId){
        return instance({
            method:'post',
            url:'/deleteMessage',
            params:{
                id:id,
                mId:uId
            }
        })
    },
}

export default request;
