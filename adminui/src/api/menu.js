import instance from "@/axios/axios";

/*
*获取菜单
 */
function getMenu() {
    return instance({
        method: "get",
        url: "getMenus"
    })
}
