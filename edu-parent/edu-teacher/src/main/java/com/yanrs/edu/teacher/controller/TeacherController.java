package com.yanrs.edu.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanrs.edu.common.R;
import com.yanrs.edu.common.ResponseCode;
import com.yanrs.edu.teacher.components.AliOss;
import com.yanrs.edu.teacher.entity.Teacher;
import com.yanrs.edu.teacher.entity.vo.AddTeacherReqVo;
import com.yanrs.edu.teacher.entity.vo.TeacherListReqVo;
import com.yanrs.edu.teacher.entity.vo.UpdateTeacherReqVo;
import com.yanrs.edu.teacher.handler.EduException;
import com.yanrs.edu.teacher.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author rex
 * @since 2020-06-14
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin // 允许跨域
public class TeacherController {
    // 注入 TeacherService
    @Autowired
    private TeacherService teacherService;

    @Autowired
    AliOss aliOss;

    // 模拟登录
    @PostMapping("/login")
    public R login(){
        return R.success().data("token", "admin");
    }

    // 模拟获取用户信息
    @GetMapping("/info")
    public R info(){
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        return R.success().data("roles", roles).data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    // 查询所有讲师
    @GetMapping
    public R getAllTeacher() {
        List<Teacher> teacherList = teacherService.list(null);
        return R.success().data("item", teacherList);
    }

    // 删除讲师
    @DeleteMapping("{id}")
    public R deleteTeacherById(@PathVariable("id") String id) {
        boolean removeById = teacherService.removeById(id);
        if (removeById) {
            return R.success();
        } else {
            return R.fail();
        }
    }

//    // 分页查询讲师列表
//    @GetMapping("/list/{currentPage}/{pageSize}")
//    public R getPageTeacher(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize) {
//        Page<Teacher> teacherPage = new Page<>(currentPage, pageSize);
//        teacherService.page(teacherPage, null);
//        return R.success().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());
//    }

    // 分页多条件查询讲师列表
    @GetMapping("/list/{currentPage}/{pageSize}")
    public R getConditionPageTeacher(@PathVariable("currentPage") Integer currentPage, @PathVariable("pageSize") Integer pageSize, TeacherListReqVo teacherListReqVo) {
        Page<Teacher> teacherPage = new Page<>(currentPage, pageSize);
        teacherService.getTeacherListByCondition(teacherPage, teacherListReqVo);
        return R.success().data("total", teacherPage.getTotal()).data("items", teacherPage.getRecords());
    }

    // 添加讲师
    @PostMapping
    public R addTeacher(@RequestBody AddTeacherReqVo addTeacherReqVo) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(addTeacherReqVo, teacher);
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.success().data("teacher", teacher);
        } else {
            return R.fail();
        }
    }

    // 根据 ID 查询讲师信息
    @GetMapping("{id}")
    public R getTeacherById(@PathVariable("id") String id) {
        Teacher teacher = teacherService.getById(id);
        return R.success().data("teacher", teacher);
    }

    // 根据 ID 修改讲师信息
    @PutMapping("{id}")
    public R updateTeacherById(@PathVariable("id") String id, @RequestBody UpdateTeacherReqVo updateTeacherReqVo) {
        // 没有 updateTeacherReqVo 抛出异常
        if (updateTeacherReqVo==null){
            throw new EduException(ResponseCode.PARAM_INVAILD.getCode(), ResponseCode.PARAM_INVAILD.getMessage());
        }
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(updateTeacherReqVo, teacher);
        teacher.setId(id);
        boolean update = teacherService.updateById(teacher);
        if (update) {
            return R.success().data("teacher", teacher);
        } else {
            return R.fail();
        }
    }

    // 上传讲师头像
    @PostMapping("/upload/avatar")
    public R uploadAvatar(@RequestParam("file") MultipartFile file){
        try {
            String url = aliOss.UploadFile(file.getOriginalFilename(), file.getInputStream());
            return R.success().data("url", url);
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail();
        }
    }
}

