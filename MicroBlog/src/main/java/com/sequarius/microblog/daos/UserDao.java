package com.sequarius.microblog.daos;

import com.sequarius.microblog.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Sequarius on 2015/5/31.
 */

public interface UserDao {

    void saveUser(User user);

    User findUserByMail(String mail);

    User findUserByUsername(String username);
}
