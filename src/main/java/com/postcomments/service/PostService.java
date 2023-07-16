package com.postcomments.service;

import com.postcomments.entity.Post;
import com.postcomments.repo.PostRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService{

    @Autowired
    private PostRepository postRepository;



    @Override
    public Post createPost(String title, String content, String author) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setAuthor(author);
            return postRepository.save(post);
        } catch (Exception e) {
            throw new ServiceException("Error creating the post.", e);
        }
    }

    @Override
    public Post getPostById(String id) {
        try {
            return postRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving the post.", e);
        }
    }

    @Override
    public List<Post> getAllPosts() {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Error retrieving posts.", e);
        }
    }

    @Override
    public Post updatePost(String id, Post updPost) {
        try {
            Post existingPost = postRepository.findById(id).orElse(null);
            if (existingPost != null) {
                existingPost.setTitle(updPost.getTitle());
                existingPost.setContent(updPost.getContent());
                existingPost.setAuthor(updPost.getAuthor());
                return postRepository.save(existingPost);
            }
            return null;
        } catch (Exception e) {
            throw new ServiceException("Error updating the post.", e);
        }
    }

    @Override
    public boolean deletePost(String id) {
        try {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new ServiceException("Error deleting the post.", e);
        }
    }
}
