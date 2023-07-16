
package com.postcomments;

import com.postcomments.entity.Post;
import com.postcomments.repo.CommentRepository;
import com.postcomments.repo.PostRepository;
import com.postcomments.service.CommentService;
import com.postcomments.service.PostService;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTests {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @Test
    void createPost_ValidPost_ReturnsCreatedPost() {
        // Arrange
        Post post = new Post();
        when(postRepository.save(post)).thenReturn(post);

        // Act
        Post createdPost = postService.createPost(post.getTitle(), post.getContent(), post.getAuthor());

        // Assert
        assertEquals(post, createdPost);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void getPostById_ExistingId_ReturnsPost() {
        // Arrange
        String postId = "1";
        Post post = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        Post retrievedPost = postService.getPostById(postId);

        // Assert
        assertEquals(post, retrievedPost);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_NonExistingId_ReturnsNull() {
        // Arrange
        String postId = "1";
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        Post retrievedPost = postService.getPostById(postId);

        // Assert
        assertNull(retrievedPost);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getAllPosts_ReturnsListOfPosts() {
        // Arrange
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postRepository.findAll()).thenReturn(posts);

        // Act
        List<Post> retrievedPosts = postService.getAllPosts();

        // Assert
        assertEquals(posts.size(), retrievedPosts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void updatePost_ExistingPost_ReturnsUpdatedPost() {
        // Arrange
        String postId = "1";
        Post existingPost = new Post();
        Post updatedPost = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(updatedPost);

        // Act
        Post result = postService.updatePost(postId, updatedPost);

        // Assert
        assertEquals(updatedPost, result);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    void updatePost_NonExistingPost_ReturnsNull() {
        // Arrange
        String postId = "1";
        Post updatedPost = new Post();
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        Post result = postService.updatePost(postId, updatedPost);

        // Assert
        assertNull(result);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).save(any());
    }

    @Test
    void deletePost_ExistingPost_ReturnsTrue() {
        // Arrange
        String postId = "1";
        when(postRepository.existsById(postId)).thenReturn(true);

        // Act
        boolean deleted = postService.deletePost(postId);

        // Assert
        assertTrue(deleted);
        verify(postRepository, times(1)).existsById(postId);
        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void deletePost_NonExistingPost_ReturnsFalse() {
        // Arrange
        String postId = "1";
        when(postRepository.existsById(postId)).thenReturn(false);

        // Act
        boolean deleted = postService.deletePost(postId);

        // Assert
        assertFalse(deleted);
        verify(postRepository, times(1)).existsById(postId);
        verify(postRepository, never()).deleteById(any());
    }
}