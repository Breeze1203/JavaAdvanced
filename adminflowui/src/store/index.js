import { createStore } from 'vuex'

const store=createStore({
  state:{
    menu:[],
    Tags:[],
    permissions_data:[],
  },
  getters: {
    // 对数据进行过滤
  },
  mutations: {
    initMenu(state,menuData){
      state.menu=menuData;
    },
    initTag(state,tag){
      state.Tags=tag
    },
    initPermission(state,permission){
      state.permissions_data=permission;
    },
  },
  actions: {
  },
  modules: {
  }
})

export default store;
