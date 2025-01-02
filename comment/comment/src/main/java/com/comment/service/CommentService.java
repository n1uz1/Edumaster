package com.comment.service;

import com.comment.model.Comment;
import com.comment.model.Reply;

import java.util.List;


public interface CommentService {
    List<Comment> getAllComments();
    Comment addComment(Comment comment);
    Comment likeComment(Long id);
    Reply addReply(Long commentId, Reply reply);
    void deleteComment(Long id);
    List<Comment> getCommentsByUsername(String username);
    List<Comment> getCommentsByVideo(Long courseId, Long lessonId);
    // 新增方法：获取论坛评论
    List<Comment> getForumComments();
    // 根据 courseId 和 lessonId 删除课程视频的所有评论
    boolean deleteCommentsByVideo(Long courseId, Long lessonId);
}
