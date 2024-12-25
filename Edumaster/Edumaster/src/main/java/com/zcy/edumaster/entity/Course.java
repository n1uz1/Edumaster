package com.zcy.edumaster.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("course")
public class Course {

    @TableId(type = IdType.AUTO)
    private Long courseId;

    private String title;
    private String description;
    private Long creatorId;
}

