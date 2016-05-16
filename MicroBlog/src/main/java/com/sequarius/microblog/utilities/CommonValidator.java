package com.sequarius.microblog.utilities;

import com.sequarius.microblog.entities.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Sequarius on 2015/6/1.
 */
public class CommonValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof User){
            User user=(User)o;
            if(user.getUsername()==null||user.getUsername().length()==0){
                errors.reject("用户名不能为空");
                return;
            }
            if(user.getPassword()==null||user.getPassword().length()<7) {
                errors.reject("密码不能少于6位");
            }
        }
    }
}
