package com.postcomments;

import com.postcomments.entity.Comment;
import com.postcomments.entity.Post;
import com.postcomments.repo.CommentRepository;
import com.postcomments.repo.PostRepository;
import com.postcomments.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommentServiceTests {

    @Autowired
    private CommentService commentService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    void createComment_ValidComment_ReturnsCreatedComment() {
        // Arrange
        String postId = "1";
        Comment comment = new Comment();
        comment.setPostId(postId);
        when(postRepository.findById(postId)).thenReturn(Optional.of(new Post()));
        when(commentRepository.save(comment)).thenReturn(comment);

        // Act
        Comment createdComment = commentService.createComment(postId, comment.getContent(), comment.getAuthor());

        // Assert
        assertEquals(comment, createdComment);
        verify(postRepository, times(1)).findById(postId);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void createComment_InvalidPostId_ReturnsNull() {
        // Arrange
        String postId = "1";
        Comment comment = new Comment();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        Comment createdComment = commentService.createComment(postId, comment.getContent(), comment.getAuthor());

        // Assert
        assertNull(createdComment);
        verify(postRepository, times(1)).findById(postId);
        verify(commentRepository, never()).save(comment);
    }

    @Test
    void getAllCommentsForPost_ReturnsListOfComments() {
        // Arrange
        String postId = "1";
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());
        when(commentRepository.findByPostId(postId)).thenReturn(comments);

        // Act
        List<Comment> retrievedComments = commentService.getAllCommentsForPost(postId);

        // Assert
        assertEquals(comments.size(), retrievedComments.size());
        verify(commentRepository, times(1)).findByPostId(postId);
    }

    @Test
    void updateComment_ExistingComment_ReturnsUpdatedComment() {
        // Arrange
        String commentId = "1";
        Comment existingComment = new Comment();
        Comment updatedComment = new Comment();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(updatedComment);

        // Act
        Comment result = commentService.updateComment(commentId, updatedComment);

        // Assert
        assertEquals(updatedComment, result);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(existingComment);
    }

    @Test
    void updateComment_NonExistingComment_ReturnsNull() {
        // Arrange
        String commentId = "1";
        Comment updatedComment = new Comment();
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        // Act
        Comment result = commentService.updateComment(commentId, updatedComment);

        // Assert
        assertNull(result);
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, never()).save(any());
    }

    @Test
    void deleteComment_ExistingComment_ReturnsTrue() {
        // Arrange
        String commentId = "1";
        when(commentRepository.existsById(commentId)).thenReturn(true);

        // Act
        boolean deleted = commentService.deleteComment(commentId);

        // Assert
        assertTrue(deleted);
        verify(commentRepository, times(1)).existsById(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void deleteComment_NonExistingComment_ReturnsFalse() {
        // Arrange
        String commentId = "1";
        when(commentRepository.existsById(commentId)).thenReturn(false);

        // Act
        boolean deleted = commentService.deleteComment(commentId);

        // Assert
        assertFalse(deleted);
        verify(commentRepository, times(1)).existsById(commentId);
        verify(commentRepository, never()).deleteById(any());
    }
}
