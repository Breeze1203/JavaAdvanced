import {createRouter, createWebHashHistory} from 'vue-router'
import Login from "@/components/Login.vue";
import Home from "@/components/Home.vue";
import LogSet from "@/components/log/LogSet.vue";
import UserSet from "@/components/set/UserSet.vue";
import RoleSet from "@/components/set/RoleSet.vue";
import Permission from "@/components/set/Permission.vue";

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
   {
        path: '/home',
        name: 'Home',
        component: Home,
        children:[
            {
                path:'/log',
                name:'操作日志',
                component:LogSet
            },
            {
                path: '/UserSet',
                name: '用户管理',
                component: UserSet
            },
            {
                path: '/RoleSet',
                name: '角色管理',
                component: RoleSet
            },
            {
                path: '/Permissions',
                name: '权限资源',
                component: Permission
            }
        ]
    },

]

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router
