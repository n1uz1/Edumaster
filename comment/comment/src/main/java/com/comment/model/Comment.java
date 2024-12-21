package com.comment.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("comments")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String avatar;

    private String content;

    private LocalDateTime time;

    private Integer likes;

    @TableField(exist = false)
    private List<Reply> replies;
}
