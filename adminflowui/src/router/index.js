import {createRouter, createWebHashHistory, createWebHistory} from 'vue-router'
import Login from "@/components/Login.vue";


const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
   {
        path: '/home',
        name: 'Home',
        component: import('../components/Home.vue'),
        children:[
            {
                path:'/log',
                name:'操作日志',
                component:import('../components/log/LogSet.vue')
            },
            {
                path: '/UserSet',
                name: '用户管理',
                component: import('../components/set/UserSet.vue')
            },
            {
                path: '/RoleSet',
                name: '角色管理',
                component: import('../components/set/RoleSet.vue')
            },
            {
                path: '/Permissions',
                name: '权限资源',
                component: import('../components/set/Permission.vue')
            }
        ]
    },

]

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router
