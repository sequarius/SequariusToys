package com.sequarius.microblog.handlers;

import com.sequarius.microblog.entities.Post;
import com.sequarius.microblog.entities.User;
import com.sequarius.microblog.services.PostService;
import com.sequarius.microblog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Sequarius on 2015/6/7.
 */
@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "publishPost", method = RequestMethod.POST)
    public String publishPost(@Valid Post post,BindingResult result,@CookieValue(value = "user_name",required = true)String username){
        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            return "index";
        }
        User user=userService.findUserByUsername(username);
        post.setUser(user);
        post.setPostTime(System.currentTimeMillis());
        System.out.println(post);
        postService.publishPost(post);
        return "redirect:/";
    }

}
