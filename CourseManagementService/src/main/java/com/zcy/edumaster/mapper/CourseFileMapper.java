package com.zcy.edumaster.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcy.edumaster.entity.CourseFile;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseFileMapper extends BaseMapper<CourseFile> {

    @Select("SELECT * FROM course_files WHERE course_id = #{courseId}")
    List<CourseFile> findFilesByCourseId(Long courseId);
}
