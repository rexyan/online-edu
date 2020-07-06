import request from '@/utils/request'

export default {
    getAllSubjectList() {
        return request({
            url: '/edu/subject/',
            method: 'get'
          })
    },
    removeSubjectId(id) {
        return request({
            url: '/edu/subject/'+id,
            method: 'delete'
        })
    },
    addSubjectOne(subject) {
        return request({
            url: '/edu/subject/level1',
            method: 'post',
            data:subject
        })
    },
    addSubjectTwo(subject) {
        return request({
            url: '/edu/subject/level2',
            method: 'post',
            data:subject
        })
    }
}