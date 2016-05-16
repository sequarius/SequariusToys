package com.sequarius.microblog.daos.impl;

import com.sequarius.microblog.daos.PostDao;
import com.sequarius.microblog.entities.Post;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sequarius on 2015/6/7.
 */
@Repository("postDao")
public class PostDaoImpl extends BaseDao implements PostDao {
    @Override
    public void savePost(Post post) {
        getCurrentSession().save(post);
    }

    @Override
    public List<Post> getPostList() {
        String hql="FROM Post post ORDER BY id DESC";
        Query query=getCurrentSession().createQuery(hql);
        return query.list();
//        Post post=(Post)getCurrentSession().load(Post.class,1);
//        getCurrentSession().load()

    }

    @Override
    public List<Post> getPostsByUsername(String username) {
        String hql="FROM Post p WHERE p.user.username=? ORDER BY id DESC";
        Query query=getCurrentSession().createQuery(hql);
        query.setString(0,username);
        return query.list();
    }
}
