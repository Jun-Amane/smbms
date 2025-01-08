package edu.sdagri.smbms.web;

import edu.sdagri.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import edu.sdagri.smbms.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Package: edu.sdagri.smbms.web
 * File: LoginOutController.java
 * Author: Yang Song
 * Date: 04/01/2025
 * Time: 18:57
 * Description: TODO: change me
 */
@Controller
public class LoginOutController {
    @Autowired
    private UserService userService;
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:toLogin";
    }
    @RequestMapping("/toWorkspace")
    public String toWorkspce(){
        return "workspace";
    }
    @RequestMapping("/login")
    public String login(String userCode, String userPassword, HttpSession session){
        System.out.println("userCode = " + userCode+"\t"+"userPassword="+userPassword);
        User loginuser = this.userService.findUser(userCode, userPassword);
        if(loginuser!=null){
            session.setAttribute("loginuser",loginuser);
            return "redirect:toWorkspace";
        }
        return "redirect:toLogin?flag=-1";

    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
}
