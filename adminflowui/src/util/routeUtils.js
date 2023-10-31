
export function addRoutes(m) {
    const parentRoute = {
        path: '/home',
        name: 'Home',
        component:()=>import("@/components/Home.vue"),
        children: []
    };
    for (let i = 0; i < m.length; i++) {
        const parent = m[i];
        if (parent.children && parent.children.length > 0) {
            for (let j = 0; j < parent.children.length; j++) {
                const child = parent.children[j];
                const childRoute = {
                    path: child.path,
                    component: null,
                    name: child.name
                };
                switch (child.component) {
                    case 'PerConfig':
                        childRoute.component = ()=>import("@/components/per/PerConfig.vue");
                        break;
                    case 'DepSetting':
                        childRoute.component = ()=>import("@/components/per/DepSetting.vue");
                        break;
                    case 'Announcement':
                        childRoute.component = ()=>import("@/components/anno/Announcement.vue");
                        break;
                    case 'Chat':
                        childRoute.component = ()=>import("@/components/chat/Chat.vue");
                        break;
                    case 'EmpBasic':
                        childRoute.component = ()=>import("@/components/emp/EmpBasic.vue");
                        break;
                    case 'EmpAdvanch':
                        childRoute.component = ()=>import("@/components/emp/EmpAdvanch.vue");
                        break;
                    case 'LogSet':
                        childRoute.component = ()=>import("@/components/log/LogSet.vue");
                        break;
                    case 'LoginLog':
                        childRoute.component = ()=>import("@/components/log/LoginLog.vue");
                        break;
                    case 'LogAccess':
                        childRoute.component = ()=>import("@/components/log/LogAccess.vue");
                        break;
                    case 'Onboard':
                        childRoute.component = ()=>import("@/components/onbo/Onboard.vue");
                        break;
                    case 'PosSetting':
                        childRoute.component = ()=>import("@/components/per/PosSetting.vue");
                        break;
                }
                parentRoute.children.push(childRoute);
            }
        }
    }
    return parentRoute;
}

