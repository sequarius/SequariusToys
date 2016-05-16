package com.sequarius.microblog.services.impl;

import com.sequarius.microblog.daos.UserDao;
import com.sequarius.microblog.entities.User;
import com.sequarius.microblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sequarius on 2015/5/31.
 */
@Service("userService")
public class UserServiceImpl extends BaseService implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public void saveUser(User user){
        userDao.saveUser(user);
    }

    @Override
    public boolean isMailExisted(String mail) {

        return userDao.findUserByMail(mail)==null?false:true;
    }
    @Override
    public boolean isUsernameExisted(String username){
        return userDao.findUserByUsername(username)==null?false:true;
    }


    @Override
    public boolean isLegalUser(User user) {
        User userFind=userDao.findUserByMail(user.getEmail());
        if(userFind==null){
            return false;
        }
        if(userFind.getPassword().equals(user.getPassword())) {
            user.setUsername(userFind.getUsername());
            return true;
        }else{
            return false;
        }
    }

    @Override
    public User findUserByMail(String mail) {
        return userDao.findUserByMail(mail);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }


}
