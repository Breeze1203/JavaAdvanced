import {createRouter, createWebHashHistory} from 'vue-router'
import Login from "@/components/Login.vue";
import Home from "@/components/Home.vue";
import LogSet from "@/components/log/LogSet.vue";
import UserSet from "@/components/set/UserSet.vue";
import RoleSet from "@/components/set/RoleSet.vue";
import Permission from "@/components/set/Permission.vue";
import About from "@/components/pub/About.vue";
import MenuSet from "@/components/set/MenuSet.vue";
import Department from "@/components/info/Department.vue";
import Position from "@/components/info/Position.vue";

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
                path: '/system/user',
                name: '用户管理',
                component: UserSet
            },
            {
                path: '/system/role',
                name: '角色管理',
                component: RoleSet
            },
            {
                path: '/system/permissions',
                name: '权限管理',
                component: Permission
            },
            {
                path: '/system/menus',
                name: '菜单管理',
                component: MenuSet
            },
            {
                path: '/system/department',
                name: '部门管理',
                component: Department
            },
            {
                path: '/system/position',
                name: '职位管理',
                component: Position
            },
            {
                path: '/system/operation',
                name: '操作日志记录',
                component: LogSet
            },
            {
                path: '/system/scheduled',
                name: '定时任务',
                component: LogSet
            },
            {
                path: '/about',
                name: '关于项目',
                component: About
            },
        ]
    },

]

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router
