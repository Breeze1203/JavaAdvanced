<template>
  <div style="margin-top: 25px;width: 100%">
    <div style="width: 100%;margin-top:30px;">
      <div>
        <el-input v-model="organization.name" placeholder="请输入部门名称" style="width: 200px;margin-left: 20px"
                  type="text">
        </el-input>
        <el-select v-model="organization.parentId" clearable placeholder="请选择所属部门" size="default"
                   style="margin-left: 30px">
          <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
        <el-button plain style="margin-left: 30px" type="primary" @click="addOrganization">添加组织
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-button plain style="margin-left: 30px" type="info" @click="initTreeOrganization">重置
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
      </div>
    </div>
  </div>
  <div style="margin-top: 3%">
    <el-tree :data="data"
             :default-expand-all="true"
             :props="defaultProps"
             auto-expand-parent
             class="custom-tree"
             node-key="id"
             style="width: 90%;font-style: italic">
      <template #default="{ node, data }">
        <div class="department">
          <span>{{ node.label }}</span>
          <span class="buttons">
            <el-button size="small" type="danger" @click="deleteOrganization(data)">删除部门</el-button>
          </span>
        </div>
      </template>
    </el-tree>
    <el-table
        :data="data"
        style="width: 90%;margin-bottom: 20px;margin-top: 3%"
        row-key="id"
        :default-expand-all="false"
        :tree-props="{children: 'children'}">
      <el-table-column align="center" prop="id" label="唯一标识"></el-table-column>
      <el-table-column align="center" prop="name" label="部门名称"></el-table-column>
      <el-table-column align="center" prop="parentId" label="所属父部门标识"></el-table-column>
      <el-table-column align="center" fixed="right" label="操作">
        <template #default="scope">
          <el-button plain type="text" icon="Edit" @click="updateOrg(scope.row)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <el-dialog v-model="OrganizationVisible" style="width: 33%">
    <el-row>
      <el-col :span="5">
        <span>部门名称：</span>
      </el-col>
      <el-col :span="5">
        <el-input style="width: 250px" v-model="organization.name" size="small"/>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="5">
        <span>所属部门：</span>
      </el-col>
      <el-col :span="5">
        <el-select v-model="organization.parentId" clearable placeholder="请选择所属部门" style="width: 250px">
          <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-col>
    </el-row>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cancelUpdateOrg">取消</el-button>
        <el-button type="primary" @click="sureUpdateOrg">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import organization_request from "@/api/organization";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  name: "Department",
  data() {
    return {
      data: [],
      organization: {
        id: null,
        name: null,
        parentId: null,
      },
      OrganizationVisible: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      options: []
    }
  },
  methods: {
    // 初始化树形组织
    initTreeOrganization() {
      organization_request.getTreeOrganization().then(resp => {
        this.data = resp.data;
      })
    },
    // 初始化所有组织
    initAllOrganization() {
      organization_request.getAllOrganization().then(resp => {
        this.options = resp.data;
      })
    },
    // 添加组织
    addOrganization() {
      if (this.organization.name === null) {
        ElMessage.error("请输入要添加的部门名称");
        return;
      }
      if (this.organization.parentId === null) {
        ElMessage.error(" 请选择父部门");
        return;
      }
      organization_request.addOrganization(this.organization).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.initTreeOrganization();
          this.initAllOrganization();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    },
    // 删除组织
    deleteOrganization(data) {
      ElMessageBox.confirm(
          '此操作将删除【' + data.name + '】部门，是否继续?',
          'Warning',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            organization_request.deleteOrganization(data.id).then(resp => {
              if (resp.data.code === 200) {
                ElMessage.success(resp.data.message);
                this.initTreeOrganization();
                this.initAllOrganization();
              } else {
                ElMessage.error(resp.data.message);
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消操作',
            })
          })
    },
    //修改组织
    updateOrg(data) {
      this.OrganizationVisible = true;
      this.organization.id = data.id;
      if (data.parentId > 0) this.organization.parentId = data.parentId;
      this.organization.name = data.name;
    },
    // 取消修改
    cancelUpdateOrg() {
      this.OrganizationVisible = false;
      this.organization.id = null;
      this.organization.parentId = null;
      this.organization.name = null;
    },
    // 确定修改
    sureUpdateOrg() {
      organization_request.updateOrg(this.organization).then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.cancelUpdateOrg();
          this.initTreeOrganization();
          this.initAllOrganization();
        } else {
          ElMessage.error(resp.data.message);
        }
      })
    }
  },
  mounted() {
    this.initTreeOrganization();
    this.initAllOrganization();
  }
}
</script>

<style scoped>
.department {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.custom-tree {
  color: #666666;
}
</style>
