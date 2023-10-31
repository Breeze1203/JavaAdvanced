<template>
  <div style="margin-top: 20px">
    <el-row :gutter="5">
      <el-col :span="8">
        <el-select
            multiple
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
        <el-button type="primary">刷新日志</el-button>
      </el-col>
      <el-col :span="3">
      <el-button type="danger">清空日志</el-button>
      </el-col>
    </el-row>
  </div>
  <div style="margin-top: 20px">
    <el-table :data="LogData" style="width: 85%" border>
      <el-table-column prop="id" label="id" align="center" width="100" />
      <el-table-column prop="user" label="用户名" align="center" width="100" />
      <el-table-column prop="type" label="操作类型" align="center" width="100" />
      <el-table-column prop="operation" label="说明" align="center" width="300" />
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
</template>
<script>
import request from "@/util/requestUtil";
export default {
  name:'LogSet',
  data(){
    return{
      LogData:null,
      total:null,
      keyword:'',
      offset:1,
      pageSize:10,
      options:[
        {
          value: 'Option1',
          label: 'Option1',
        },
        {
          value: 'Option2',
          label: 'Option2',
        },
        {
          value: 'Option3',
          label: 'Option3',
        }]
    }
  },
  methods:{
    // 初始化日期数据
    initLogData(){
      console.log(typeof this.keyword);
      console.log(this.offset);
      console.log(this.pageSize)
      request.initLogData(this.keyword,this.offset,this.pageSize).then(resp=>{
        if(resp){
          this.LogData=resp.data.operationData;
          this.total=resp.data.total;
        }
      })
    }
  },
  mounted() {
    this.initLogData();
  }
}
</script>

<style scoped>

</style>