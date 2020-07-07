import request from '@/utils/request'

export default {
    // 根据 课程ID 查询所有章节和小节的信息
    getChapterVideoList(id) {
        return request({
            url: '/edu/chapter/chaptervideo/' + id,
            method: 'get'
        })
    },
    // 添加章节
    addChapter(chapter){
        return request({
            url: '/edu/chapter/',
            method: 'post',
            data: chapter
        })
    },
    // 根据 ID 查询章节信息
    getChapterById(id){
        return request({
            url: '/edu/chapter/'+id,
            method: 'get'
        })
    },
    // 修改章节信息
    updateChapterById(id, chapter){
        return request({
            url: '/edu/chapter/'+id,
            method: 'put',
            data: chapter
        })
    },
    // 删除章节信息
    deleteChapterById(id){
        return request({
            url: '/edu/chapter/'+id,
            method: 'delete'
        })
    },
}