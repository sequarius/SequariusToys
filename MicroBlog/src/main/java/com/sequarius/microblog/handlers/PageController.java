package com.sequarius.microblog.handlers;

import com.sequarius.microblog.entities.Post;
import com.sequarius.microblog.entities.User;
import com.sequarius.microblog.services.PostService;
import com.sequarius.microblog.services.UserService;
import com.sequarius.microblog.utilities.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.DocFlavor;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sequarius on 2015/6/6.
 */
@Controller
public class PageController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @RequestMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @RequestMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }
    @RequestMapping("/")
    public String index(ModelMap model){
        List<Post> posts = postService.getPosts();
        List<String> formatedPostTime=new ArrayList<String>();
        for(Post post:posts){
            formatedPostTime.add(TimeUtil.converTimeString(post.getPostTime()));
        }
        model.addAttribute("posts",posts);
        model.addAttribute("post_times",formatedPostTime);
        return "index";
    }
    @RequestMapping("/user_index/{username}")
    public String userIndex(@PathVariable String username,ModelMap model){
        List<Post> posts = postService.findPostByUserName(username);
        List<String> formatedPostTime=new ArrayList<String>();
        for(Post post:posts){
            formatedPostTime.add(TimeUtil.converTimeString(post.getPostTime()));
        }
        try {
            model.addAttribute("username", URLEncoder.encode(username, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("user",userService.findUserByUsername(username));
        model.addAttribute("posts",posts);
        model.addAttribute("post_times",formatedPostTime);
        return "user_index";
    }
    @RequestMapping("/edit_user_info")
    public String editUserInfo(@CookieValue(value = "user_name",required = true)String username,ModelMap model){
        User user=userService.findUserByUsername(username);
        model.addAttribute(user);
        return "edit_user_info";
    }

    @RequestMapping("/test")
    public String test(){
        return "testpage";
    }
}
