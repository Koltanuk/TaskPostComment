package com.postcomments.service;

import com.postcomments.entity.Post;

import java.util.List;

public interface IPostService {
    Post createPost(String title, String content, String author);

    Post getPostById(String id);

    List<Post> getAllPosts();

    Post updatePost(String id, Post post);

    boolean deletePost(String id);


}
