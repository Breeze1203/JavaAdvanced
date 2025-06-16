import instance from "@/util/axiosUtil";

const permissions_request={
    /*
    获取操作权限总条数
     */
    getPermissionCount(){
        return instance({
            method:"get",
            url:"/getPermissionCount"
        })
    },
    // 分页获取权限
    getAllPermByPage(page,size) {
        return instance({
            method: 'get',
            url: '/getAllPermByPage',
            params:{
                page:page,
                size:size
            }
        })
    },
    // 根据用户获取用户的权限
    getPermissionByRole(id) {
        return instance({
            method: 'get',
            url: '/getPermissionByrId',
            params: {
                rid: id
            }
        })
    },
    updatePermission(permission) {
        return instance({
            method:'post',
            url:'/updatePermission',
            headers: {
                'Content-Type': 'application/json'
            },
            data:permission
        })
    },
    // 查询所有权限
    getAllPermission(){
        return instance({
            method: 'get',
            url: '/getAllPermissions'
        })
    }
}

export default permissions_request;
