package com.postcomments.service;

import com.postcomments.entity.Comment;
import com.postcomments.entity.Post;
import com.postcomments.repo.CommentRepository;
import com.postcomments.repo.PostRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(String postId, String content, String author) {
        try {
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                Comment comment = new Comment();
                comment.setPostId(postId);
                comment.setContent(content);
                comment.setAuthor(author);
                return commentRepository.save(comment);
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException("Error creating the comment.", e);
        }
    }

    @Override
    public List<Comment> getAllCommentsForPost(String postId) {
        try {
            return commentRepository.findByPostId(postId);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving comments.", e);
        }
    }

    @Override
    public Comment updateComment(String commentId, Comment updComment) {
        try {
            Comment existingComment = commentRepository.findById(commentId).orElse(null);
            if (existingComment != null) {
                existingComment.setContent(updComment.getContent());
                existingComment.setAuthor(updComment.getAuthor());
                return commentRepository.save(existingComment);
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException("Error updating the comment.", e);
        }
    }

    @Override
    public boolean deleteComment(String commentId) {
        try {
            if (commentRepository.existsById(commentId)) {
                commentRepository.deleteById(commentId);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException("Error deleting the comment.", e);
        }
    }
}
