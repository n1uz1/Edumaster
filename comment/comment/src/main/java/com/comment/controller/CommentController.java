package com.comment.controller;

import com.comment.model.Comment;
import com.comment.model.Reply;
import com.comment.service.CommentService;
import com.comment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@RequestBody Comment comment) {
        if (comment.getUsername() == null || comment.getContent() == null) {
            throw new IllegalArgumentException("用户名和内容为必填项");
        }
        return commentService.addComment(comment);
    }


    @PostMapping("/{id}/like")
    public Comment likeComment(@PathVariable Long id) {
        Comment comment = commentService.likeComment(id);
        if (comment == null) {
            throw new ResourceNotFoundException("评论不存在");
        }
        return comment;
    }


    @PostMapping("/{id}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply addReply(@PathVariable Long id, @RequestBody Reply reply) {
        if (reply.getUsername() == null ||  reply.getContent() == null) {
            throw new IllegalArgumentException("用户名、头像和内容为必填项");
        }
        Reply createdReply = commentService.addReply(id, reply);
        if (createdReply == null) {
            throw new ResourceNotFoundException("评论不存在");
        }
        return createdReply;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    // 根据用户名获取该用户的所有评论及其回复
    @GetMapping("/user/{username}")
    public List<Comment> getCommentsByUsername(@PathVariable String username) {
        return commentService.getCommentsByUsername(username);
    }

}
