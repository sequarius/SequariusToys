package com.sequarius.microblog.services;

import com.sequarius.microblog.entities.User;

/**
 * Created by Sequarius on 2015/6/6.
 */
public interface UserService {
    void saveUser(User user);

    boolean isMailExisted(String mail);

    boolean isUsernameExisted(String username);

    boolean isLegalUser(User user);

    User findUserByMail(String email);

    User findUserByUsername(String username);
}
