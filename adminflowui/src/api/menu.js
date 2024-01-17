import instance from "@/util/axiosUtil";

const menu_request={
    // 获取所有图标
    getIcons(){
        return instance({
            method:"get",
            url:"/getAllIcons"
        })
    },
    // 获取所有菜单
    getAllMenus(){
        return instance({
            method:'get',
            url:'/getAllMenus',
        })
    },
    // 获取一级菜单
    getParentMenu(){
        return instance({
            method:'get',
            url:'/getParentMenu',
        })
    },
    // 根据登录用户角色查询菜单
    getMenusByRole(rid){
        return instance({
            method:'get',
            url:'/getMenusByRole/'+rid,
        })
    },
    // 获取当前角色已经拥有的menu id
    getMenusChecked(rid){
        return instance({
            method:'get',
            url:'/getMenusChecked/'+rid,
        })
    },
    // 修改角色菜单权限
    updateRoleMenuId(mIds,rId){
        return instance({
            method:'get',
            url:'/updateRoleMenuId',
            params:{
                mIds:mIds,
                rId:rId
            }
        })
    },
    // 修改菜单
    updateMenu(menu){
        return instance({
            method:'post',
            url:'/updateMenu',
            data:menu
        })
    },
    // 按条件查询菜单
    getMenuByCondition(menu){
        return instance({
            method:'post',
            url:'/getMenuByCondition',
            data:menu
        })
    }
}

export default menu_request;
