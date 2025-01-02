package com.comment.exception;

/**
 * 自定义异常：资源未找到
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
