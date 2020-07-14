import request from '@/utils/request'

export default {
    // 保存课程信息
    saveCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/',
            method: 'post',
            data: courseInfo
          })
    },
    // 查询所有讲师
    getAllTeacherList(){
        return request({
            url: '/edu/teacher/',
            method: 'get'
        })
    },
    // 根据课程ID 查询课程信息
    getCorseInfoById(id){
        return request({
            url: '/edu/course/' + id,
            method: 'get'
        })
    },
    // 修改课程信息
    updateCourseInfo(id, courseinfo){
        return request({
            url: '/edu/course/' + id,
            method: 'put',
            data: courseinfo
        })
    },
    // 查询所有课程信息
    getAllCourseInfo(){
        return request({
            url: '/edu/course/',
            method: 'get'
        })
    },
    // 删除课程
    deleteCourseById(id){
        return request({
            url: '/edu/course/'+ id,
            method: 'delete'
        })
    },
    // 根据课程 ID 查询课程详情信息
    getCoursePublishInfo(id){
        return request({
            url: '/edu/course/publish/info/'+ id,
            method: 'get'
        })
    },
    // 发布课程
    publishCourseInfo(id){
        return request({
            url: '/edu/course/publish/'+ id,
            method: 'post'
        })
    },
}