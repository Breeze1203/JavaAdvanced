<template>
  <div class="common-layout" style="margin-top: 25px">
    <el-container class="shadow-box">
      <el-aside width="200px">
        <div style="border-bottom: 1px solid;text-align: center">
          <h4>用户架构</h4>
        </div>
        <div style="margin-top: 25px">
          <el-tree @node-click="handleNodeClick" :default-expand-all="true" :props="defaultProps" :data="tree">
            <template #default="{ node, data }">
              <span>{{ node.label }}</span>
              <el-dropdown size="small" trigger="hover">
                <el-icon>
                  <Setting/>
                </el-icon>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="add(data)">添加</el-dropdown-item>
                    <el-dropdown-item @click="Delete(data)">删除</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </template>
          </el-tree>
        </div>
      </el-aside>
      <el-container>
        <el-header>Header</el-header>
        <el-main>
          <el-table stripe border style="width: 100%" :data="userData">
            <el-table-column align="center" prop="id" label="id" width="90"/>
            <el-table-column align="center" prop="username" label="姓名" width="90"/>
            <el-table-column align="center" prop="address" label="地址" width="90"/>
            <el-table-column align="center" prop="organization.name" label="所属节点" width="90"/>
            <el-table-column align="center" label="头像" width="100">
              <template #default="scope">
                <el-avatar size="small" :src="scope.row.userFace"/>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="embod" label="个性签名" width="180"/>
            <el-table-column align="center" label="状态" width="90">
              <template #default="scope">
                <el-tag type="success" v-if="scope.row.state">启用</el-tag>
                <el-tag type="danger" v-else>禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="email" label="邮箱" width="150"/>
          </el-table>
        </el-main>
      </el-container>
    </el-container>
  </div>
  <el-dialog
      v-model="dialogVisible"
      title="增加节点"
      width="25%">
    <el-row>
      <el-col :span="12">
        <span>上级节点：</span>
      </el-col>
      <el-col :span="12">
        <el-input v-model="nodeName" size="small" disabled/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 25px">
      <el-col :span="12">
        <span>名称</span>
      </el-col>
      <el-col :span="12">
        <el-input v-model="addOrganization.name" size="small"/>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button size="small" @click="cancelAdd">取消</el-button>
        <el-button size="small" type="primary" @click="sureAdd">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>
<script>
import request from "@/util/requestUtil";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: 'UseSet',
  data() {
    return {
      tree: [],
      dialogVisible: false,
      userData: [],
      nodeName: '',
      defaultProps: {
        label: 'name',
        children: 'children',
      },
      // 添加节点要用到的对象
      addOrganization: {
        id: null,
        name: null,
        parentId: null,
      }
    }
  },
  methods: {
    // 初始化组织架构节点
    initOrganization() {
      request.initOrganization().then(resp => {
        if (resp.data) {
          this.tree = resp.data;
        }
      })
    },
    // 初始化所有用户信息
    initAllUser() {
      request.initAllUser().then(resp => {
        if (resp.data) {
          this.userData = resp.data;
        }
      })
    },
    add(data) {
      this.dialogVisible = true;
      this.nodeName = data.name;
      this.addOrganization.parentId=data.id;
    },
    // 确定添加node
    sureAdd(){
      if(this.addOrganization.name===null){
        ElMessage.error("请输入节点名称");
        return;
      }
      request.addNode(this.addOrganization).then(resp=>{
            if(resp.data.code===200){
              ElMessage.success(resp.data.message);
              this.initOrganization();
            }else {
              ElMessage.error(resp.data.message);
            }
      })
      this.dialogVisible = false;
    },
    //取消添加
    cancelAdd(){
      this.dialogVisible=false;
      this.addOrganization.name=null},
    // 删除组织
    Delete(data) {
      if (data.children.length > 0) {
        ElMessage.error("操作失败，该节点下有子节点");
        return;
      }
      ElMessageBox.confirm(
          '此操作将永久删除【' + data.name + '】节点，是否继续?',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      )
          .then(() => {
            console.log(data.id)
            request.deleteNode(data.id).then(resp => {
              if (resp.data.code === 200) {
                ElMessage.success(resp.data.message);
                // 重新初始化用户架构
                this.initOrganization();
              } else {
                ElMessage.error(resp.data.message);
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消操作'
            })
          })

    }
  },
  mounted() {
    this.initOrganization();
    this.initAllUser();
  }
}
</script>

<style scoped>
.shadow-box {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); /* 设置阴影效果 */
  border: 1px solid #ccc; /* 设置边框 */
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}
</style>