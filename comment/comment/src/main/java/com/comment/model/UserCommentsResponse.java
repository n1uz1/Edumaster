package com.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentsResponse {
    private List<Comment> comments;
    private List<Reply> replies;
}
