<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择" @change="subjectOneLevelChanged">
          <el-option
            v-for="subject in oneLevelSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
         <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
        <el-option
          v-for="subject in twoLevelSubjectList"
          :key="subject.value"
          :label="subject.title"
          :value="subject.id"/>
      </el-select>
      </el-form-item>
    
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess" 
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/edu/teacher/upload/avatar'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <!-- 课程价格 -->
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import course from '@/api/course'
import subject from '@/api/subject'
import Tinymce from '@/components/Tinymce'


const defaultForm = {
  title: '',
  subjectId: '',
  teacherId: '',
  lessonNum: 0,
  description: '',
  cover: 'https://img3.mukewang.com/5b62867d0001d91106000338-240-135.jpg',
  price: 0
}

export default {
  components: { Tinymce },
  data() {
    return {
      courseInfo: defaultForm,
      saveBtnDisabled: false, // 保存按钮是否禁用
      teacherList: [], // 讲师列表
      oneLevelSubjectList: [], // 一级分类集合
      twoLevelSubjectList: [],  // 二级分类集合
      BASE_API: process.env.BASE_API // 接口API地址
    }
  },

  watch: {
    // 监听路由变化，也调用 init 方法
    $route(to, from) {
      console.log('watch $route')
      this.init()
    }
  },

  created() {
    console.log('info created')
    this.init()
  },

  methods: {
    // 获取所有分类信息
    getAllSubjectList(){
      subject.getAllSubjectList()
      .then(response => {
          this.oneLevelSubjectList = response.data.OneSubjectDto
      }).catch(response => {
          this.$message({
            type: 'error',
            message: '获取分类信息失败'
          })
      })
    },
    // 根据 ID 查询课程信息
    getCorseInfoById(id){
        course.getCorseInfoById(id)
        .then(response => {
            this.courseInfo = response.data.courseInfo
            // 课程类别下拉框回显
            // 查询出所有一级分类
            subject.getAllSubjectList()
            .then(response => {
                this.oneLevelSubjectList = response.data.OneSubjectDto
                 for (let i = 0; i < this.oneLevelSubjectList.length; i++) {
                    if (this.oneLevelSubjectList[i].id === this.courseInfo.subjectParentId) {
                      this.twoLevelSubjectList = this.oneLevelSubjectList[i].children
                    }
                  }
            })
            .catch()
        })
        .catch(response => {
            this.$message({
              type: 'error',
              message: '获取课程信息失败'
          })
        })
    },
    // 获取所有讲师信息
    getAllTeacherList(){
      course.getAllTeacherList()
      .then(response => {
          this.teacherList = response.data.item
      })
      .catch(response => {
          this.$message({
            type: 'error',
            message: '获取讲师信息失败'
          })
      })
    },

    init() {
      // 判断路由中是否有 ID 值，有 ID 值表示用于点击了回到上一步，需要进行数据回显。如果没有 ID 值，需要将表单清空
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        console.log(id)
        // 有 ID 值，做数据回显
        this.getCorseInfoById(id)
      } else {
        // 没有 ID，清空表单数据
        this.courseInfo = { ...defaultForm }
        // 获取所有讲师列表
        this.getAllTeacherList()
        // 获取所有 subject 列表
        this.getAllSubjectList()
      }
    },

    // 点击下一步
    next() {
      console.log('next')
      this.saveBtnDisabled = true
      // 有 ID 则进行更新，没有则新增
      if (!this.courseInfo.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 新增
    saveData() {
      course.saveCourseInfo(this.courseInfo).then(response => {
        this.$message({
          type: 'success',
          message: '保存成功!'
        })
        return response// 将响应结果传递给then
      }).then(response => {
        this.$router.push({ path: '/course/chapter/' + response.data.courseId })
      }).catch((response) => {
        this.$message({
          type: 'error',
          message: response.message
        })
      })
    },

    // 更新
    updateData() {
      course.updateCourseInfo(this.courseInfo.id, this.courseInfo)
      .then(response => {
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        // 修改后回到课程列表
        this.$router.push({ path: '/course/list/'})
      })
      .catch()
    },

    // 选择了一级分类
    subjectOneLevelChanged(oneLevelId){
      this.twoLevelSubjectList = []
      // 根据一级分类 ID 的值，获取下面的所有二级分类
       for (let i = 0; i < this.oneLevelSubjectList.length; i++) {
        if (this.oneLevelSubjectList[i].id === oneLevelId) {
            this.twoLevelSubjectList = this.oneLevelSubjectList[i].children
            this.courseInfo.subjectId = ''
        }
      }
    },
  
    // 课程封面上传成功之后
    handleAvatarSuccess(res, file) {
      console.log(res)// 上传响应
      console.log(URL.createObjectURL(file.raw))// base64编码
      this.courseInfo.cover = res.data.url
    },
    // 课程封面上传成功之前
    beforeAvatarUpload(file) {
      const isPNG = file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isPNG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isPNG && isLt2M
    }
  }
}
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>