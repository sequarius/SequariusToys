package com.sequarius.microblog.daos.impl;

import com.sequarius.microblog.daos.UserDao;
import com.sequarius.microblog.entities.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sequarius on 2015/5/31.
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDao implements UserDao  {

    private Session getSession(){
        return getSessionFactory().getCurrentSession();
    }
    @Override
    public void saveUser(User user) {
        getSession().saveOrUpdate(user);
    }

    @Override
    public User findUserByMail(String mail) {
        String hql="FROM User u WHERE u.email = ?";
        Query query=getSession().createQuery(hql);
        query.setString(0,mail);
        query.setMaxResults(1);
        List<User> users=query.list();
        return users.size()==1?users.get(0):null;
    }

    @Override
    public User findUserByUsername(String username) {
        String hql="FROM User u WHERE u.username = ?";
        Query query=getSession().createQuery(hql);
        query.setString(0,username);
        query.setMaxResults(1);
        List<User> users=query.list();
        return users.size()==1?users.get(0):null;
    }

}
