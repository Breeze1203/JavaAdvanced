import axios from "axios";
import {ElMessage} from 'element-plus';

const errorHandle = (status) => {
    switch (status) {
        case 400:
            ElMessage.error("语义有误");
            break;
        case 401:
            ElMessage.error("认证失败");
            break;
        case 403:
            ElMessage.error("你暂无访问权限");
            break;
        case 404:
            break;
        case 500:
            ElMessage.error("服务器遇到意外");
            break;
        case 502:
            ElMessage.error("服务器无响应");
            break;
        default:
            break;
    }
}
// 创建一个axios的实例
const instance = axios.create({
    // 网络请求的公共配置信息
    baseURL: '/api',
    timeout: 50000,
    withCredentials: true,
    headers:{'Content-Type': 'application/json'}
})

// 请求拦截器(发送请求之前)
instance.interceptors.request.use(
    config => {
        return config
    },
    error => {
        errorHandle(error.response.status);
        return Promise.reject(error)
    }
)

// 响应
instance.interceptors.response.use(
    success => {
        return success;
    },
    error => {
        errorHandle(error.response.status);
        return Promise.reject(error)
    }
)

// 导出网络实例
export default instance;
