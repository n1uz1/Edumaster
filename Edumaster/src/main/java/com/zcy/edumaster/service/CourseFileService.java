package com.zcy.edumaster.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zcy.edumaster.entity.CourseFile;
import com.zcy.edumaster.mapper.CourseFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseFileService extends ServiceImpl<CourseFileMapper, CourseFile> {

    @Autowired
    private CourseFileMapper courseFileMapper;

    // 保存课程文件信息
    public boolean saveFile(CourseFile courseFile) {
        return save(courseFile);  // 使用 MyBatis-Plus 的 save 方法
    }

    // 根据 courseId 查询该课程的所有文件信息
    public List<CourseFile> getFilesByCourseId(Long courseId) {
        return courseFileMapper.findFilesByCourseId(courseId);
    }
}
