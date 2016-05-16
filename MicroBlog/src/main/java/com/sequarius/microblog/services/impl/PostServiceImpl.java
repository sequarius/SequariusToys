package com.sequarius.microblog.services.impl;

import com.sequarius.microblog.daos.PostDao;
import com.sequarius.microblog.entities.Post;
import com.sequarius.microblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sequarius on 2015/6/7.
 */
@Service("postService")
public class PostServiceImpl extends BaseService implements PostService {
    @Autowired
    PostDao postDao;
    @Override
    public void publishPost(Post post) {
        postDao.savePost(post);
    }

    @Override
    public List<Post> getPosts() {
        return postDao.getPostList();
    }

    @Override
    public List<Post> findPostByUserName(String username) {
        return postDao.getPostsByUsername(username);
    }
}
