package com.comment.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.comment.mapper.CommentMapper;
import com.comment.mapper.ReplyMapper;
import com.comment.model.Comment;
import com.comment.model.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Comment 服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public List<Comment> getAllComments() {
        List<Comment> comments = commentMapper.selectList(new QueryWrapper<Comment>());
        // 查询每个评论的回复
        for (Comment comment : comments) {
            QueryWrapper<Reply> replyQuery = new QueryWrapper<>();
            replyQuery.eq("comment_id", comment.getId());
            List<Reply> replies = replyMapper.selectList(replyQuery);
            comment.setReplies(replies);
        }
        return comments;
    }

    @Override
    public Comment addComment(Comment comment) {
        comment.setTime(LocalDateTime.now());
        comment.setLikes(0);
        commentMapper.insert(comment);
        return comment;
    }

    @Override
    public Comment likeComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment != null) {
            comment.setLikes(comment.getLikes() + 1);
            commentMapper.updateById(comment);
        }
        return comment;
    }

    @Override
    public Reply addReply(Long commentId, Reply reply) {
        // 检查评论是否存在
        Comment comment = commentMapper.selectById(commentId);
        if (comment != null) {
            reply.setCommentId(commentId);
            reply.setTime(LocalDateTime.now());
            replyMapper.insert(reply);
            return reply;
        }
        return null;
    }

    @Override
    public void deleteComment(Long id) {
        // 删除评论前删除所有关联的回复
        QueryWrapper<Reply> replyQuery = new QueryWrapper<>();
        replyQuery.eq("comment_id", id);
        replyMapper.delete(replyQuery);

        // 删除评论
        commentMapper.deleteById(id);
    }
}
