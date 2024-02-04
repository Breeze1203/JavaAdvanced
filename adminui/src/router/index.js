import {createRouter, createWebHashHistory} from 'vue-router'


const routes = [

    {
        path: '/',
        name: 'Login',
        component:() => import('../components/Login.vue')
    },

]

const router = createRouter({
    history: createWebHashHistory(process.env.BASE_URL),
    routes
})

export default router
