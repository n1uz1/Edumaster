package com.comment.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 回复实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("replies")
public class Reply {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long commentId; // 关联的评论ID

    private String username;

    private String avatar;

    private String content;

    private LocalDateTime time;
}