import instance from "@/util/axiosUtil";

const organization_request = {
    // 获取所有节点
    getAllOrganization() {
        return instance({
            method: 'get',
            url: '/Organization/getAllOrganization',
        })
    },

    // 树形展示节点
    getTreeOrganization() {
        return instance({
            method: 'get',
            url: '/Organization/getTreeOrganization',
        })
    },
    addOrganization(organization) {
        return instance({
            method: 'post',
            url: '/Organization/addOrganization',
            data: organization
        })
    },
    // 删除组织
    deleteOrganization(id) {
        return instance({
            method: 'get',
            url: '/Organization/deleteById',
            params: {
                id: id
            }
        })
    },
    // 修改组织
    updateOrg(organization) {
        return instance({
            method: 'post',
            url: '/Organization/updateOrganization',
            data:organization
        })
    }
}
export default organization_request;
