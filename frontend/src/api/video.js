import request from '@/utils/request'

export default {
    // 添加小节
    addVideo(video) {
        return request({
            url: '/edu/video',
            method: 'post',
            data: video
        })
    },
    // 查询小节
    getVideoById(id) {
        return request({
            url: '/edu/video/' + id,
            method: 'get'
        })
    },
    // 更新小节
    updateVideoById(id,video) {
        return request({
            url: '/edu/video/' + id,
            method: 'put',
            data: video
        })
    },
    // 删除小节
    deleteVideoById(id) {
        return request({
            url: '/edu/video/' + id,
            method: 'delete'
        })
    },
    // 删除阿里云视频
    removeAliVideo(videoId){
        return request({
            url: '/edu/videotape/' + videoId,
            method: 'delete'
        })
    }
}