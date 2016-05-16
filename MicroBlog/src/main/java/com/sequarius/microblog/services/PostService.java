package com.sequarius.microblog.services;

import com.sequarius.microblog.entities.Post;

import java.util.List;

/**
 * Created by Sequarius on 2015/6/7.
 */
public interface PostService {

    void publishPost(Post post);
    List<Post> getPosts();
    List<Post> findPostByUserName(String username);
}
