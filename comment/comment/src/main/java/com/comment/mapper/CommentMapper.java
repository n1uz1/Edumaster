package com.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * Comment Mapper 接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
