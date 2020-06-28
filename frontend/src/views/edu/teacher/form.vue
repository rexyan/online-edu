<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacherObj.name"/>
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacherObj.sort" controls-position="right" min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacherObj.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacherObj.career"/>
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacherObj.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
        <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacherObj.avatar"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
        v-show：是否显示上传组件
        :key：类似于id，如果一个页面多个图片上传控件，可以做区分
        :url：后台上传的url地址
        @close：关闭上传组件
        @crop-upload-success：上传成功后的回调 -->
        <image-cropper
            v-show="imagecropperShow"
            :width="300"
            :height="300"
            :key="imagecropperKey"
            :url="BASE_API+'/edu/teacher/upload/avatar'"
            field="file"
            @close="close"
            @crop-upload-success="cropSuccess"/>
        </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from '@/api/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

const emptyTeacherObj = {
    name: "",
    sort: 0,
    level: "",
    career: "",
    intro: "",
    avatar: ''
}

export default{
    // 声明额外使用的组件
    components: {ImageCropper, PanThumb},

    data(){
        return {
            BASE_API: process.env.BASE_API, // 接口API地址
            teacherObj: emptyTeacherObj,
            imagecropperShow: false, // 是否显示上传组件
            imagecropperKey: 0 // 上传组件id
        }
    },
    watch: {
        $route(to, from) {
            // 监听路由的变化，当路由变化的时候重新执行 init 方法
            console.log('watch $route')
            this.init()
        }
    },
    created(){
        this.init()
    },
    methods: {
        // 上传成功后的回调函数
        cropSuccess(data) {
            console.log(data)
            this.imagecropperShow = false
            this.teacherObj.avatar = data.url
            // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
        },
        // 关闭上传组件
        close() {
            this.imagecropperShow = false
            // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
        },
        init(){
            // 在页面加载之前，根据 URL 判断是修改还是新增页面
            if(this.$route.params && this.$route.params.id){
                const id = this.$route.params.id
                // 如果是修改页面就查询信息
                this.getTeacherById(id)
            }else{
                // 表单数据清空
                this.teacherObj = {...emptyTeacherObj}
            }
        },
        // 保存或更新
        saveOrUpdate(){
            debugger
            if(this.teacherObj.id){
                // 有 id 则是更新
                this.updateTeacher()
            }else{
                // 否则就是新增
                this.saveTeacher()
            }
        },

        // 根据 ID 查询
        getTeacherById(id){
            teacher.getTeacherById(id)
            .then((response) => {
                console.log(response)
                this.teacherObj = response.data.teacher
            }).catch((response) => {
                
            })
        },

        // 添加讲师
        saveTeacher(){
            teacher.addTeacher(this.teacherObj)
            .then(() => {
                // 有多个 then 的时候，加上 return 后面的 then 才执行
                return this.$message({
                    type: 'success',
                    message: '添加成功!'
                })
            }).then(() => {
                // 回到列表页面
                this.$router.push({path: "/teacher/list"})
            })
            .catch((response) => {
                console.log(response)
                return this.$message({
                    type: 'error',
                    message: '添加失败!'
                })
            })
        },

        // 修改信息
        updateTeacher(){
            teacher.updateTeacher(this.teacherObj.id, this.teacherObj)
            .then(() => {
                return this.$message({
                    type: 'success',
                    message: '修改成功!'
                })
            }).then(() =>{
                // 添加成功后回到列表页面
                this.$router.push({path: "/teacher/list"})
            }).catch((response) => {
                console.log(response)
                return this.$message({
                    type: 'error',
                    message: '修改失败!'
                })
            })
        }
    }
}
</script>