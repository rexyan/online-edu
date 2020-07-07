<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>

    <el-button type="text">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
        <li
            v-for="chapter in chapterNestedList"
            :key="chapter.id">
            <p>
                {{ chapter.title }}

                <span class="acts">
                    <el-button type="text">添加课时</el-button>
                    <el-button style="" type="text">编辑</el-button>
                    <el-button type="text">删除</el-button>
                </span>
            </p>

            <!-- 视频 -->
            <ul class="chanpterList videoList">
                <li
                    v-for="video in chapter.videos"
                    :key="video.id">
                    <p>{{ video.title }}
                        <span class="acts">
                            <el-button type="text">编辑</el-button>
                            <el-button type="text">删除</el-button>
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
  </div>
</template>

<script>
import chapter from '@/api/chapter'

export default {
    data(){
        return {
            saveBtnDisabled: false, // 保存按钮是否禁用
            id: "",
            chapterNestedList: [] // 章节嵌套视频(小节)列表
        }
    },
    created(){
      this.init()
      this.getChapterVideoList(this.id)
    },
    methods: {
        // 根据课程 ID 查询章节和小节信息
        getChapterVideoList(id){
          chapter.getChapterVideoList(id) 
          .then(response => {
            this.chapterNestedList = response.data.chapterVideoList
          })
          .catch(response => {
            this.$message({
                type: 'info',
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
            this.$router.push({ path: '/course/publish/1' })
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