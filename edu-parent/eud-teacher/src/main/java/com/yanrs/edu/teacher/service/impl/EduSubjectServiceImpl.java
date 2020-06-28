package com.yanrs.edu.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanrs.edu.common.ResponseCode;
import com.yanrs.edu.teacher.entity.EduSubject;
import com.yanrs.edu.teacher.handler.EduException;
import com.yanrs.edu.teacher.mapper.EduSubjectMapper;
import com.yanrs.edu.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author rex
 * @since 2020-06-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 读取 Excel 中的内容，将其写入分类表中
     * @param file
     */
    @Override
    public void importSubject(MultipartFile file) {
        try {
            // 1. 获取文件输入流
            InputStream inputStream = file.getInputStream();
            // 2. 获取 workbook
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            // 3. 获取 sheet
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 4. 根据 sheet 获取行
            int lastRowNum = sheet.getLastRowNum();// 获取最后一行的位置
            for (int i = 1; i <= lastRowNum; i++) {
                String parentId;

                XSSFRow row = sheet.getRow(i);
                if (row == null){
                    continue;
                }

                // 在添加一级分类之前，需要先判断是否已经存在相同名称的一级分类
                String oneCellValue = row.getCell(0).getStringCellValue();
                EduSubject subjectOneExist = this.existOneSubject(oneCellValue);
                if (subjectOneExist == null){
                    // 数据库中不存在相同的一级分类，可以添加一级分类
                    EduSubject subject = new EduSubject();
                    subject.setParentId("0");
                    subject.setSort(0);
                    subject.setTitle(oneCellValue);
                    baseMapper.insert(subject);
                    parentId = subject.getId();
                }else{
                    // 已经存在一级分类，需要获取分类的 ID 用作 parent_id
                    parentId = subjectOneExist.getId();
                }

                String twoCellValue = row.getCell(1).getStringCellValue();
                EduSubject subjectTwoExist = this.existTwoSubject(twoCellValue, parentId);
                if (subjectTwoExist == null){
                    // 数据库中不存在相同的二级分类，可以添加二级分类
                    EduSubject subject = new EduSubject();
                    subject.setParentId(parentId);
                    subject.setSort(0);
                    subject.setTitle(twoCellValue);
                    baseMapper.insert(subject);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(ResponseCode.IMPORT_SUBJECT_ERROR.getCode(), ResponseCode.IMPORT_SUBJECT_ERROR.getMessage());
        }
    }

    /**
     * 需要先判断是否已经存在相同名称的一级分类
     * @param name 一级分类名称
     * @return Subject
     */
    private EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", 0);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 需要先判断是否已经存在相同名称的二级分类
     * @param name 二级分类名称
     * @param parentId parentId
     * @return Subject
     */
    private EduSubject existTwoSubject(String name, String parentId){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }
}
