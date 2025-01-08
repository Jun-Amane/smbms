package edu.sdagri.smbms.web;

import com.github.pagehelper.PageInfo;
import edu.sdagri.smbms.entity.Role;
import edu.sdagri.smbms.entity.User;
import edu.sdagri.smbms.service.RoleService;
import edu.sdagri.smbms.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;


/**
 * Package: edu.sdagri.smbms.web
 * File: UserController.java
 * Author: Yang Song
 * Date: 04/01/2025
 * Time: 21:03
 * Description: TODO: change me
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @RequestMapping("/delete")
    public String delete(Long id){
        this.userService.remove(id);
        return "redirect:list";
    }
    @RequestMapping("/detail")
    public String detail(Long id,Model model){
        User user = this.userService.findtById(id);
        model.addAttribute("user",user);
        return "user/detail";

    }
    @RequestMapping("/modify")
    public String modify(User user,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        user.setModifyBy(loginUser);
        user.setModifyDate(new Date());
        this.userService.modify(user);
        return "redirect:list";
    }
    @RequestMapping("/add")
    public String add(User user, HttpSession session){
        user.setCreationDate(new Date());
        User loginUser = (User) session.getAttribute("loginUser");
        user.setCreatedBy(loginUser);
        this.userService.add(user);
        return "redirect:list";
    }
    @RequestMapping("/toAdd")
    public String toAdd(Model model){
        List<Role> roleList = this.roleService.findAll();
        model.addAttribute("roleList",roleList);
        return "user/add";
    }

    @RequestMapping("/toModify")
    public String toModify(Long id,Model model){
        User user = this.userService.findtById(id);
        model.addAttribute("user",user);
        List<Role> roleList = this.roleService.findAll();
        model.addAttribute("rolelist",roleList);
        return "user/modify";
    }
    @RequestMapping("/list")
    public String list(User condition, @RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5")Integer pageSize, Model model){
        PageInfo<User> pageInfo = this.userService.findByPage(condition, pageNum, pageSize);
        model.addAttribute("pageInfo",pageInfo);
        List<Role> roleList= roleService.findAll();
        model.addAttribute("roleList",roleList);
        return "user/list";
    }
}
