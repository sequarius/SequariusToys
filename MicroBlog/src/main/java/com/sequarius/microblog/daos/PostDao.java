package com.sequarius.microblog.daos;

import com.sequarius.microblog.entities.Post;

import java.util.List;

/**
 * Created by Sequarius on 2015/6/7.
 */
public interface PostDao {
    void savePost(Post post);
    List<Post> getPostList();

    List<Post> getPostsByUsername(String username);
}
