package com.sequarius.microblog.handlers;


import com.sequarius.microblog.entities.User;
import com.sequarius.microblog.services.UserService;
import com.sequarius.microblog.utilities.MessageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sequarius on 2015/5/31.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageManager messageManager;

    @ModelAttribute
    public void getUser(@CookieValue(value = "user_name", required = false) String username, Map<String, Object> map) {
        System.out.println("username==null+" + (username == null));
        if (username != null) {
            map.put("user", userService.findUserByUsername(username));
        }
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String createAccount(@Valid User user, BindingResult result, HttpServletRequest request) {
        System.out.println(user);

        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            return "sign_up";
        }
        if (userService.isMailExisted(user.getEmail())) {
            result.rejectValue("email", null, messageManager.getMessage("user.username.existed.error", user.getEmail()));
            return "sign_up";
        }
        if (userService.isUsernameExisted(user.getUsername())) {
            result.rejectValue("username", null, messageManager.getMessage("user.username.existed.error", user.getUsername()));
            return "sign_up";
        }
        user.setRegistTime(System.currentTimeMillis());
        user.setLastLoginIp(request.getRemoteHost());
        System.out.println(user);
        userService.saveUser(user);
        return "redirect:/";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String signIn(@Valid User user, BindingResult result, HttpServletResponse response) {
        System.out.println(user);
        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            return "sign_in";
        }
        if (!userService.isMailExisted(user.getEmail())) {
            result.rejectValue("email", null, messageManager.getMessage("user.username.unexist.error", user.getEmail()));
            return "sign_in";
        }
        if (userService.isLegalUser(user)) {
            changeLogStatus(true, response, user.getUsername());
            return "redirect:/";
        } else {
            result.rejectValue("email", null, messageManager.getMessage("user.password.legal.error"));
            return "sign_in";
        }
    }

    @RequestMapping(value = "/sign_out")
    public String signOut(HttpServletResponse response) {
        changeLogStatus(false, response, "");
        return "redirect:/";
    }

    private void changeLogStatus(boolean logIn, HttpServletResponse response, String username) {
        Cookie cookieLogged = new Cookie("logged_in", "true");
        Cookie cookieUsername = null;
        try {
            cookieUsername = new Cookie("user_name", URLEncoder.encode(username, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (logIn) {
            cookieLogged.setMaxAge(60 * 60 * 24 * 7);
            cookieUsername.setMaxAge(60 * 60 * 24 * 7);
        } else {
            cookieLogged.setMaxAge(0);
            cookieUsername.setMaxAge(0);
        }
        response.addCookie(cookieLogged);
        response.addCookie(cookieUsername);
    }

    @RequestMapping("/editinfo")
    public String editUserInfo(@Valid User user, BindingResult result, ModelMap modelMap, HttpServletResponse response,HttpServletRequest request) {
        if (result.hasErrors()) {
            System.out.println(result.getErrorCount());
            return "edit_user_info";
        }
        userService.saveUser(user);
        Cookie[] cookies=request.getCookies();
        int i=0;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("user_name")){
                try {
                    cookie.setValue(URLEncoder.encode(user.getUsername(), "UTF-8"));
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(cookie);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            }
            i++;
        }
        if(i==cookies.length) {
            try {
                response.addCookie(new Cookie("user_name", URLEncoder.encode(user.getUsername(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        modelMap.addAttribute("edit_success",messageManager.getMessage("user.info.update.success"));
        return "edit_user_info";
    }

    @ResponseBody
    @RequestMapping(value = "/upload_avatar", method = RequestMethod.POST)
    public Message updateAvatar(@CookieValue(value = "user_name", required = true) String username,
                                @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        System.out.println("name" + file.getOriginalFilename());

        String realPath = request.getSession().getServletContext().getRealPath("/avatars");
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        File outputFile = new File(realPath, fileName);
        try {
            InputStream inputStream = file.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            System.out.println(outputFile.getAbsolutePath());
            System.out.println(outputFile.getCanonicalPath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
            User user = userService.findUserByUsername(username);
            user.setAvatar("/avatars/" + fileName);
            userService.saveUser(user);
            fileOutputStream.close();
            return new Message("1", "修改头像成功", "/avatars/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Message("2", "上传失败", fileName);
        }

    }

    private class Message {
        private String code;
        private String msg;
        private String src;

        public Message(String code, String msg, String src) {
            this.code = code;
            this.msg = msg;
            this.src = src;
        }

        public Message() {

        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
