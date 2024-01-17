<template>
  <div class="shadow">
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>菜单名称:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7"
      >
        <el-input v-model="menu.menu_name" placeholder="请输入菜单名称"/>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>菜单路径:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="9" :xl="7"
      >
        <el-input v-model="menu.path" placeholder="请输入菜单路径">
          <template #prepend>/system</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>父级菜单:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-select :clearable="true" v-model="parent_id" placeholder="请选择父级菜单">
          <el-option
              v-for="(item,index) in menus_role"
              :key="index"
              :label="item.menu_name"
              :value="item.menu_id"
          />
        </el-select>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>菜单角色:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-select :disabled="true" :clearable="true" v-model="menu.menu_role" placeholder="请选择菜单角色">
          <el-option
              v-for="item in menus"
              :key="item"
              :label="item"
              :value="item"
          />
        </el-select>
      </el-col>
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1"
      ><span>菜单图标:</span></el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-select :clearable="true" v-model="menu.icon" placeholder="请选择菜单图标">
          <el-option
              v-for="item in icons"
              :key="item"
              :label="item"
              :value="item"
          />
        </el-select>
      </el-col>
    </el-row>
    <el-row :gutter="6" style="margin-bottom: 30px">
      <el-col :xs="4" :sm="6" :md="4" :lg="3" :xl="1">
        <el-button type="primary" plain @click="getMenuByCondition" icon="Search">搜索</el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-button plain icon="Refresh" @click="refresh">刷新</el-button>
      </el-col>
      <el-col :xs="2" :sm="3" :md="5" :lg="5" :xl="7">
        <el-button :disabled="!permissions_data.includes('export_data')" plain type="success" icon="Upload">导出
        </el-button>
      </el-col>
    </el-row>
    <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="menu_id"
        border
        :default-expand-all="false"
        :tree-props="{children: 'menus'}">
      <el-table-column align="center" prop="menu_id" label="唯一标识" width="100"></el-table-column>
      <el-table-column align="center" prop="menu_name" label="菜单名称" width="180"></el-table-column>
      <el-table-column align="center" prop="parent_id" label="父级菜单标识" width="180"></el-table-column>
      <el-table-column align="center" prop="path" label="路径" width="180"></el-table-column>
      <el-table-column align="center" prop="menu_role" label="菜单角色" width="180"></el-table-column>
      <el-table-column align="center" label="图标" width="150">
        <template #default="scope">
          <el-icon>
            <component :is="scope.row.icon"/>
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column fixed="right" align="center" label="操作" width="200">
        <template #default="scope">
          <el-button icon="Edit" type="text" :disabled="!permissions_data.includes('update_menu')"
                     @click="updateMenu(scope.row)">修改菜单
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog
      v-model="update_menu"
      title="修改菜单"
      style="margin-top: 12%"
      width="28%"
  >
    <el-row>
      <el-col :span="5">
        <span>菜单名称：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width:200px" v-model="menu.menu_name" size="small"/>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="5">
        <span>父级菜单：</span>
      </el-col>
      <el-col :span="5">
        <el-input disabled style="width:200px" v-model="menu.parent_id" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>菜单角色：</span>
      </el-col>
      <el-col :span="5">
        <el-select disabled v-model="menu.menu_role" style="width:200px" placeholder="请选择菜单角色" size="small">
          <el-option
              v-for="item in menus"
              :key="item"
              :label="item"
              :value="item"
          />
        </el-select>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>菜单路径：</span>
      </el-col>
      <el-col :span="5">
        <el-input disabled style="width:200px" v-model="menu.menu_path" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>菜单图标：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width:200px" v-model="menu.icon" size="small"/>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdate">取消</el-button>
        <el-button type="primary" :disabled="!permissions_data.includes('update_menu')" @click="sureUpdate">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import menu_request from "@/api/menu";
import {mapState} from "vuex";
import {ElMessage} from "element-plus";
import store from "@/store";

export default {
  data() {
    return {
      tableData: [],
      menu: {
        menu_id: null,
        menu_name: null,
        parent_id: null,
        path: null,
        menu_role: null,
        icon: null
      },
      update_menu: false,
      icons: [],
      parent_id: null,
      menus: ['一级菜单', '二级菜单'],
      menus_role: []
    }
  },
  computed: {
    ...mapState(["permissions_data"])
  },
  methods: {
    //初始化所有菜单图标
    initAllIcons() {
      menu_request.getIcons().then(resp => {
        this.icons = resp.data;
      })
    },
    // 获取所有菜单
    initMenus() {
      menu_request.getAllMenus().then(resp => {
        this.tableData = resp.data;
      })
    },
    // 获取所有菜单角色
    initMenuRole() {
      menu_request.getParentMenu().then(resp => {
        this.menus_role = resp.data;
      })
    },
    // 按要求查询菜单
    getMenuByCondition(){
      menu_request.getMenuByCondition(this.menu).then(resp=>{
        this.tableData = resp.data;
      })
    },
    // 刷新
    refresh(){
      this.empty();
      this.initMenus();
    },
    // 修改菜单
    updateMenu(data) {
      this.update_menu = true;
      this.menu.menu_role=data.menu_role;
      this.menu.menu_id = data.menu_id;
      this.menu.parent_id = data.parent_id;
      this.menu.menu_path = data.path;
      this.menu.icon = data.icon;
      this.menu.menu_name = data.menu_name;
    },
    // 取消修改
    cancelUpdate() {
      this.empty();
      this.update_menu = false;
    },
    // 确认修改
    sureUpdate(){
      menu_request.updateMenu(this.menu).then(resp=>{
        if(resp.data.code==200){
          ElMessage.success(resp.data.message);
          this.initMenus();
          menu_request.getMenusByRole(JSON.parse(sessionStorage.getItem("user")).id).then(resp=>{
            store.commit("initMenu",resp.data);
          })
          this.cancelUpdate();
        }else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    empty() {
      this.menu.parent_id =null;
      this.menu.menu_id = null;
      this.menu.menu_path = null;
      this.menu.icon = null;
      this.menu.menu_name = null;
      this.parent_id=null;
    }

  },
  watch: {
    parent_id(newValue, oldValue) {
      if (newValue == null || newValue == '') {
        this.menu.menu_role = '一级菜单';
        this.menu.menu_path = '/system'
      } else {
        this.menu.menu_role = '二级级菜单';
        this.menu.menu_path = null;
      }
    }
  },
  mounted() {
    this.initMenus();
    this.initMenuRole();
    this.initAllIcons();
  }
}
</script>
<style scoped>
.shadow {
  margin-top: 30px;
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}
</style>
