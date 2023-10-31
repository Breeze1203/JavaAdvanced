import { createStore } from 'vuex'

export default createStore({
  state: {
    token:null,
  },
  getters: {
    // 对数据进行过滤
  },
  mutations: {
    getToken(state,data){
      state.token=data;
    },
  },
  actions: {
  },
  modules: {
  }
})
