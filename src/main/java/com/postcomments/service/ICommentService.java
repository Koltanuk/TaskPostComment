package com.postcomments.service;

import com.postcomments.entity.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(String postId, String content, String author);

    List<Comment> getAllCommentsForPost(String postId);

    Comment updateComment(String commentId, Comment comment);

    boolean deleteComment(String commentId);

}
