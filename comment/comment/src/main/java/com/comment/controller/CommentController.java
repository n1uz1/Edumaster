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

    /**
     * 获取所有评论
     *
     * @return 评论列表
     */
    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    /**
     * 添加新评论
     *
     * @param comment 评论对象
     * @return 添加的评论
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment addComment(@RequestBody Comment comment) {
        if (comment.getUsername() == null || comment.getAvatar() == null || comment.getContent() == null) {
            throw new IllegalArgumentException("用户名、头像和内容为必填项");
        }
        return commentService.addComment(comment);
    }

    /**
     * 点赞评论
     *
     * @param id 评论ID
     * @return 点赞后的评论
     */
    @PostMapping("/{id}/like")
    public Comment likeComment(@PathVariable Long id) {
        Comment comment = commentService.likeComment(id);
        if (comment == null) {
            throw new ResourceNotFoundException("评论不存在");
        }
        return comment;
    }

    /**
     * 添加回复
     *
     * @param id     评论ID
     * @param reply 回复对象
     * @return 添加的回复
     */
    @PostMapping("/{id}/replies")
    @ResponseStatus(HttpStatus.CREATED)
    public Reply addReply(@PathVariable Long id, @RequestBody Reply reply) {
        if (reply.getUsername() == null || reply.getAvatar() == null || reply.getContent() == null) {
            throw new IllegalArgumentException("用户名、头像和内容为必填项");
        }
        Reply createdReply = commentService.addReply(id, reply);
        if (createdReply == null) {
            throw new ResourceNotFoundException("评论不存在");
        }
        return createdReply;
    }

    /**
     * 删除评论
     *
     * @param id 评论ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
