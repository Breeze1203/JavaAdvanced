import axios from "axios";
import router from "../router";
import {ElMessage} from 'element-plus';
import {getCookie} from "@/util/cookieUtil";

const errorHandle = (status) => {
    switch (status.code) {
        case 400:
            ElMessage.error("语义有误");
            break;
        case 403:
            router.replace('/');
            break;
        case 404:
            break;
        case 500:
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
    headers: {'Content-Type': 'application/json'}
})


instance.interceptors.request.use(
    config => {
        if (sessionStorage.getItem("user") !== null) {
            let user = JSON.parse(sessionStorage.getItem("user"));
            let token = getCookie(user.username + 'token');
            config.headers["Authorization"] = token;
        }
        return config;
    },
    error => {
        errorHandle(error.response.status);
        return Promise.reject(error);
    }
);



instance.interceptors.response.use(
    success => {
        if(success.data.hasOwnProperty("code")){
            errorHandle(success.data);
        }
        return success;
    },
    error => {
        return Promise.reject(error);
    }
)

// 导出网络实例
export default instance;

