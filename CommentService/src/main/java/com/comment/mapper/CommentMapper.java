package com.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
