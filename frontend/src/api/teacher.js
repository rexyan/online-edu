import request from '@/utils/request'

export default {
  // 分页条件查询
  getTeacherPageList(page, limit, searchObj){
    return request({
      url: '/edu/teacher/list/' + page +'/' + limit,
      method: 'get',
      params: searchObj
    })
  },
  // 删除
  deleteTeacherById(id){
    return request({
      url: '/edu/teacher/' + id,
      method: 'delete'
    })
  },
  // 新增
  addTeacher(teacher){
    return request({
      url: '/edu/teacher/',
      method: 'post',
      data:teacher
    })
  },
  // 根据 ID 进行查询
  getTeacherById(id){
    return request({
      url: '/edu/teacher/' + id,
      method: 'get'
    })
  },
  // 根据 ID 修改 Teacher
  updateTeacher(id, teacherObj){
    return request({
      url: '/edu/teacher/' + id,
      method: 'put',
      data: teacherObj
    })
  }
}
