import request from '@/utils/request'

export default {
    // 根据 课程ID 查询所有章节和小节的信息
    getChapterVideoList(id) {
        return request({
            url: '/edu/chapter/chaptervideo/' + id,
            method: 'get'
          })
    }
}