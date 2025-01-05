package edu.sdagri.smbms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Package: edu.sdagri.smbms.web
 * File: HelloWorldController.java
 * Author: Yang Song
 * Date: 04/01/2025
 * Time: 16:59
 * Description: TODO: change me
 */
@Controller
public class HelloWorldController {
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello springmvc");
        return "/index.jsp";
    }
}
