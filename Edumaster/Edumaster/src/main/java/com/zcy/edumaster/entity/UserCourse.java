package com.zcy.edumaster.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("user_courses")
public class UserCourse {

    @TableId(type = IdType.AUTO)
    private Long userCourseId;

    private Long userId;
    private Long courseId;
}
