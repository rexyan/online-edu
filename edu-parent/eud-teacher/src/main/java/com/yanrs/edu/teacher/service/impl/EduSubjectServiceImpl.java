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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 删除信息（只要此 ID 不是任何记录的 parent id ，那就可以删除）
     * @param id
     * @return
     */
    @Override
    public Boolean deleteSubjectById(String id) {
        // 只要此 ID 不是任何记录的 parent id ，那就可以删除
        QueryWrapper<EduSubject> eduSubjectQueryWrapper = new QueryWrapper<>();
        eduSubjectQueryWrapper.eq("parent_id", id);
        Integer selectCount = baseMapper.selectCount(eduSubjectQueryWrapper);
        if (selectCount > 0){
            return false;
        }else{
            int i = baseMapper.deleteById(id);
            return i>0;
        }
    }

    /**
     * 添加一级分类
     * @return
     */
    @Override
    public Boolean addLevelOne(EduSubject eduSubject) {
        EduSubject existOneSubject = this.existOneSubject(eduSubject.getTitle());
        if(existOneSubject == null){
            eduSubject.setParentId("0");
            int i = baseMapper.insert(eduSubject);
            return i > 0;
        }
        return false;
    }

    /**
     * 添加二级分类
     * @return
     */
    @Override
    public Boolean addLevelTwo(EduSubject eduSubject) {
        EduSubject existTwoSubject = this.existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        if(existTwoSubject == null){
            int i = baseMapper.insert(eduSubject);
            return i > 0;
        }
        return false;
    }

    /**
     * 查询所有信息，并按层级组合
     * @return
     */
    @Override
    public List getAllSubject() {
        ArrayList<Map<String, Object>> allLevelSubject = new ArrayList<>();

        // 查询所有一级分类信息
        QueryWrapper<EduSubject> oneLevelQueryWrapper = new QueryWrapper<>();
        oneLevelQueryWrapper.eq("parent_id", "0");
        List<EduSubject> allOneLevelEduSubject = baseMapper.selectList(oneLevelQueryWrapper);
        for (EduSubject eduSubject: allOneLevelEduSubject) {
            HashMap<String, Object> oneLevelSubject = new HashMap<>();
            oneLevelSubject.put("id", eduSubject.getId());
            oneLevelSubject.put("title", eduSubject.getTitle());
            oneLevelSubject.put("children", new ArrayList<Map<String, Object>>());
            allLevelSubject.add(oneLevelSubject);
        }

        // 查询所有二级分类信息
        QueryWrapper<EduSubject> twoLevelQueryWrapper = new QueryWrapper<>();
        oneLevelQueryWrapper.ne("parent_id", "0");
        List<EduSubject> allTwoLevelEduSubject = baseMapper.selectList(twoLevelQueryWrapper);
        for (EduSubject level2: allTwoLevelEduSubject) {
            for (Map<String, Object> levelItem: allLevelSubject) {
                // 如果 level2 的 parent id 是levelItem 的 id，那么就将其加入 children 中
                if(level2.getParentId().equals(levelItem.get("id"))){
                    HashMap<String, Object> twoLevelSubject = new HashMap<>();
                    twoLevelSubject.put("id", level2.getId());
                    twoLevelSubject.put("title", level2.getTitle());
                    ArrayList<Map<String, Object>> children = (ArrayList<Map<String, Object>>) levelItem.get("children");
                    children.add(twoLevelSubject);
                }
            }
        }
        return allLevelSubject;
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
