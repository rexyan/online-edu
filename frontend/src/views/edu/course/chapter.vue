<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-button type="text" @click="openAddChapterFormVisible">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in chapterNestedList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}

                <span class="acts">
                    <el-button type="text" @click="openVideoDialog(chapter.id)">添加小节</el-button>
                    <el-button type="text" @click="getChapterByID(chapter.id)">编辑</el-button>
                    <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
                </span>
            </p>

            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.videos"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text" @click="openVideoEditDialog(video.id)">编辑</el-button>
                            <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
                        </span>
                    </p>
                </li>
            </ul>
        </li>
    </ul>
    <div>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单(模态框) -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
        <el-form :model="chapter" label-width="120px">
            <el-form-item label="章节标题">
                <el-input v-model="chapter.title"/>
            </el-form-item>
            <el-form-item label="章节排序">
                <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
    </el-dialog>

    <!-- 添加和修改小节表单(模态框) -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加小节">
        <el-form :model="video" label-width="120px">
            <el-form-item label="小节标题">
                <el-input v-model="video.title"/>
            </el-form-item>
            <el-form-item label="小节排序">
                <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
        </div>
    </el-dialog>

  </div>
</template>

<script>
import video from '@/api/video'
import chapter from '@/api/chapter'

export default {
    data(){
        return {
            saveBtnDisabled: false, // 保存按钮是否禁用
            id: "",
            chapterNestedList: [], // 章节嵌套视频(小节)列表
            dialogChapterFormVisible: false, //是否显示章节表单
            chapter: {// 章节对象
              title: '',
              sort: 0,
              courseId: ""
            },
            video:{
              title: '',
              sort: 0,
              chapterId: "",
              courseId: "",
              id: ""
            },
            dialogVideoFormVisible: false //是否显示小节表单
        }
    },
    created(){
      this.init()
      this.getChapterVideoList(this.id)
    },
    methods: {
        // 删除小节
        removeVideo(videoId){
          this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            return video.deleteVideoById(videoId)
          }).then(() => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getChapterVideoList(this.id) // 刷新列表
          }).catch((response) => { // 失败
            if (response === 'cancel') {
              this.$message({
                type: 'info',
                message: '已取消删除'
              })
            } else {
              this.$message({
                type: 'error',
                message: "删除失败！"
              })
            }
          })
        },

        // 点击编辑小节信息
        openVideoEditDialog(videoId){
          this.dialogVideoFormVisible = true
          // 查询小节信息
          video.getVideoById(videoId)
          .then(response => {
            this.video = response.data.eduVideo
          })
          .catch(response => {
            this.$message({
                type: 'error',
                message: "获取小节信息失败！"
             })
          })
        },

        // 添加或者修改小节信息
        saveOrUpdateVideo(){
          // 有小节信息就修改，否则就新增
          if(this.video.id){
            this.updateVideo(this.video.id, this.video)
          }else{
            this.addVideo()
          }
        },
        // 修改小节
        updateVideo(videoId, videoObj){
          video.updateVideoById(videoId, videoObj)
          .then(response => {
            // 关闭弹窗
            this.dialogVideoFormVisible = false;
            // 提示信息
            this.$message({
              type: 'success',
              message: '修改小节信息成功!'
            })
            // 刷新页面
            this.getChapterVideoList(this.id) 
          })
          .catch(response => {
              this.$message({
                type: 'error',
                message: "修改小节信息失败！"
              })
          })
        },

        // 添加小节
        addVideo(){
          video.addVideo(this.video)
          .then(response => {
            // 关闭弹窗
            this.dialogVideoFormVisible = false;
            // 提示信息
            this.$message({
              type: 'success',
              message: '添加小节成功!'
            })
            // 刷新页面
            this.getChapterVideoList(this.id) 
          })
          .catch(response => {
              this.$message({
                type: 'error',
                message: "删除小节失败！"
              })
          })
        },

        // 弹出修改小节信息的模态框
        openVideoDialog(chapterId){
          this.dialogVideoFormVisible = true;
          this.video.chapterId = chapterId;
          this.video.courseId = this.id

          // 清空模态框数据
          this.video.title = ""
          this.video.sort = ""
        },

        // 删除章节
        removeChapter(id){
           this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            return chapter.deleteChapterById(id)
          }).then(() => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getChapterVideoList(this.id) // 刷新列表
          }).catch((response) => { // 失败
            if (response === 'cancel') {
              this.$message({
                type: 'info',
                message: '已取消删除'
              })
            } else {
              this.$message({
                type: 'error',
                message: "删除失败！"
              })
            }
          })
        },

        // 章节编辑
        getChapterByID(id){
          // 数据回显
          this.dialogChapterFormVisible = true 
          chapter.getChapterById(id)
          .then(response => {
            this.chapter = response.data.eduChapter
          })
          .catch(response => {
            this.$message({
                type: 'error',
                message: '添加章节信息失败'
            })
          })
        },
        
        // 保存或修改章节信息
        saveOrUpdate(){
          // chapter 中没有 ID 则添加，否则为修改
          if(this.chapter.courseId){
            this.updateChapter()
          }else{
            this.saveChapter()
          }
        },

        // 新增保存章节弹出框
        openAddChapterFormVisible(){
          this.dialogChapterFormVisible = true
          // 表单清空
          this.chapter.title = "" 
          this.chapter.sort = "" 
          this.chapter.courseId = "" 
        },
        // 保存章节
        saveChapter(){
          // 设置课程 ID
          this.chapter.courseId = this.id

          chapter.addChapter(this.chapter)
          .then(response => {
              // 关闭弹窗
              this.dialogChapterFormVisible = false 
              this.$message({
                type: 'success',
                message: '添加章节成功!'
              })
              // 刷新页面
              this.getChapterVideoList(this.id) 
          })
          .catch(response => {
              this.$message({
                type: 'error',
                message: '添加章节失败'
            })
          })
        },

        // 更新章节信息
        updateChapter(){
          chapter.updateChapterById(this.chapter.id, this.chapter)
          .then(response => {
             // 关闭弹窗
             this.dialogChapterFormVisible = false 
             // 提示信息
             this.$message({
                type: 'success',
                message: '更新章节成功!'
              })
            // 刷新页面
            this.getChapterVideoList(this.id)
          })
          .catch(response => {
            this.$message({
                type: 'error',
                message: '更新章节失败'
            })
          })
        },

        // 根据课程 ID 查询章节和小节信息
        getChapterVideoList(id){
          chapter.getChapterVideoList(id) 
          .then(response => {
            this.chapterNestedList = response.data.chapterVideoList
          })
          .catch(response => {
            this.$message({
                type: 'error',
                message: '获取章节和小节信息失败'
            })
          })
        },

        init(){
            // 获取路由中的 ID 值
            if (this.$route.params && this.$route.params.id) {
                this.id = this.$route.params.id
                console.log(this.id)
            }
        },
        previous() {
            console.log('previous')
            this.$router.push({ path: '/course/info/' + this.id })
        },

        next() {
            console.log('next')
            this.$router.push({ path: '/course/publish/' + this.id })
        }
    }
}
</script>

<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 12px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>