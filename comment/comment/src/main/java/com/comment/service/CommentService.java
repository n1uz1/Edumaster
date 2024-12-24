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

}
