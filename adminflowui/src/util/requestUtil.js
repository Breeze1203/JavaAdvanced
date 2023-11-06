import instance from "@/util/axiosUtil";

const request = {
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
    deleteLog(){
        return instance({
            method: 'get',
            url: '/log/deleteLog',
        })
    },
    // 组织架构的请求
    initOrganization(){
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
            data:user
        })
    },
    // 删除节点
    deleteNode(id){
        return instance({
            method: 'get',
            url: '/Organization/deleteById',
            params:{
                id:id
            }
        })
    },
    // 添加节点
    addNode(organization){
        return instance({
            method: 'post',
            url: '/Organization/addOrganization',
            data:organization
        })
    },
    // 修改用户
    updateUser(user){
        return instance({
            method: 'post',
            url: '/updateUser',
            data:user
        })
    },
    // 删除用户
    deleteUser(id){
        return instance({
            method: 'get',
            url: '/deleteUser',
            params:{
                id:id
            }
        })
    },
    // 添加用户
    addUser(user){
        return instance({
            method: 'post',
            url: '/addUser',
            data:user
        })
    },
    // 获取所有节点
    getAllOrganization(){
        return instance({
            method: 'get',
            url: '/Organization/getAllOrganization',
        })
    },
    // 获取所有角色
    getAllRoles(){
        return instance({
            method: 'get',
            url: '/getAllRole',
        })
    },
    // 获取所有权限
    getAllPermission(){
        return instance({
            method: 'get',
            url: '/getAllPermission',
        })
    },
    // 根据用户获取用户的权限
    getPermissionByRole(id){
        return instance({
            method: 'get',
            url: '/getPermissionByrId',
            params:{
                rid:id
            }
        })
    },

}

export default request;