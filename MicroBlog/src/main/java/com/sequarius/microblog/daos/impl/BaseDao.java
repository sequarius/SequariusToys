package com.sequarius.microblog.daos.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Sequarius on 2015/6/6.
 */

public class BaseDao {
    @Autowired
    private SessionFactory sessionFactory;
    protected  SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
