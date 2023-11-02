<template>
  <div class="Log-shadow">
    <div>
      <el-row :gutter="5">
        <el-col :span="8">
          <el-select
              v-model="keyword"
              :clearable="true"
              placeholder="操作类型"
              style="width: 240px"
          >
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button type="primary" @click="initLogData(this.keyword,this.offset,this.pageSize)">刷新日志</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="danger" @click="deleteLog">清空日志</el-button>
        </el-col>
      </el-row>
    </div>
    <div style="margin-top: 20px">
      <el-table :data="LogData" style="width: 90%" border>
        <el-table-column prop="id" label="id" align="center" width="100"/>
        <el-table-column prop="user" label="用户名" align="center" width="100"/>
        <el-table-column prop="type" label="操作类型" align="center" width="200"/>
        <el-table-column prop="operation" label="说明" align="center" width="250"/>
        <el-table-column prop="date" label="操作时间" align="center" width="250"/>
      </el-table>
    </div>
    <div style="margin-top: 20px">
      <el-pagination
          :page-sizes="[10, 20, 30, 40]"
          :small="small"
          layout="total, sizes, prev, pager, next, jumper"
          :total=total
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>
<script>
import request from "@/util/requestUtil";
import {ElMessage} from "element-plus";

export default {
  name: 'LogSet',
  data() {
    return {
      LogData: null,
      total: null,
      keyword: '',
      offset: 1,
      pageSize: 10,
      options: [
        {
          value: '用户登录',
          label: '用户登录',
        },
        {
          value: '退出登录',
          label: '退出登录',
        }]
    }
  },
  methods: {
    // 初始化日期数据
    initLogData(keyword, offset, pageSize) {
      request.initLogData(keyword, offset, pageSize).then(resp => {
        if (resp) {
          this.LogData = resp.data.operationData;
          this.total = resp.data.total;
        }
      })
    },
    // 每页展示条数改变
    handleSizeChange(size) {
      this.initLogData(this.keyword, this.offset, size);
    },
    handleCurrentChange(offset) {
      this.initLogData(this.keyword, offset, this.pageSize);
    },
    // 清空日志
    deleteLog() {
      request.deleteLog().then(resp => {
        if (resp.data.code === 200) {
          ElMessage.success(resp.data.message);
          this.initLogData(this.keyword, this.offset, this.pageSize);
        } else {
          ElMessage.error(resp.data.message)
        }
      })
    }
  },
  mounted() {
    this.initLogData(this.keyword, this.offset, this.pageSize);
  },
  watch: {
    keyword(newValue, oldValue) {
      this.initLogData(newValue, this.offset, this.pageSize);
    }
  }
}
</script>

<style scoped>
.Log-shadow {
  margin-top: 25px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4); /* 设置阴影效果 */
  border: 1px solid #ccc; /* 设置边框 */
  padding: 20px; /* 设置内边距，可根据需要进行调整 */
}
</style>