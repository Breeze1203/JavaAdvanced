import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)


app.use(store).use(router).use(ElementPlus).mount('#app')
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// router.beforeEach((to, from, next) => {
//     if (to.path === '/') {
//         next();
//     } else {
//         if (sessionStorage.getItem("user") == null) {
//             next('/');
//         } else if (store.state.menus.length === 0) {
//             let user = JSON.parse(sessionStorage.getItem("user"));
//             request.initMenu(user.id).then(resp => {
//                 //固定调用 mutations里面的getMenu
//                 store.commit("getMenu", resp.data);
//                 const b = addRoutes(resp.data);
//                 router.addRoute(b);
//             })
//             next();
//         } else {
//             next();
//         }
//     }
// })