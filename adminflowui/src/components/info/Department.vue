<template>
  <div style="margin-top: 25px;width: 100%">
    <div style="width: 100%;margin-top:30px;">
      <div>
        <el-input placeholder="请输入部门名称" style="width: 200px;margin-left: 20px"
                  type="text">
        </el-input>
        <el-select v-model="value" style="margin-left: 30px" placeholder="请选择所属部门" size="default">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
        <el-button @click="addRole" plain style="margin-left: 30px" type="primary">添加部门
          <el-icon>
            <Plus/>
          </el-icon>
        </el-button>
        <el-button @click="cancelUpdate" plain style="margin-left: 30px" type="info">重置
          <el-icon>
            <Refresh/>
          </el-icon>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Department",
  data(){
    return{
      value:null,
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
        },
        {
          value: 'Option4',
          label: 'Option4',
        },
        {
          value: 'Option5',
          label: 'Option5',
        }]
    }
  }
}
</script>

<style scoped>

</style>
