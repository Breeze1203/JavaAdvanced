import instance from "@/util/axiosUtil";

const request = {
    login(username, password, remember) {
        return instance({
            method: 'get',
            url: '/login',
            headers: {
                "Content-Type": "application/json",
            },
            params: {
                username: username,
                password: password,
                remember: remember
            }
        })
    }
    ,
    // 退出登录
    loginOut(username) {
        return instance({
            method: 'get',
            url: '/loginOut',
            headers: {
                "Content-Type": "application/json"
            },
            params: {
                username: username
            }
        })
    },
    // 渲染每天登录人数
    getCount() {
        return instance({
            method: 'get',
            url: '/getCount',
            headers: {
                "Content-Type": "application/json"
            }
        })
    }
    ,
    // 初始化登录日志格式数据
    initLogData(keyword, offset, pageSize) {
        return instance({
            method: 'get',
            url: '/log/getAllLog',
            headers: {
                "Content-Type": "application/json"
            },
            params: {
                keyword: keyword,
                offset: offset,
                pageSize: pageSize
            }
        })
    }
}

export default request;