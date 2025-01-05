package edu.sdagri.smbms.service;

import com.github.pagehelper.PageInfo;
import edu.sdagri.smbms.entity.User;

/**
 * Package: edu.sdagri.smbms.service
 * File: UserService.java
 * Author: Yang Song
 * Date: 04/01/2025
 * Time: 10:26
 * Description: TODO: change me
 */

public interface UserService {
    //添加用户
    void add(User user);
    //修改用户
    void modify(User user);
    //根据逐渐id查询用户
    User findtById(Long id);
    //删除用户
    void remove(Long id);
    //登录
    User findUser(String userCode, String userPassword);
    //带条件分页查询
    PageInfo<User> findByPage(User condition, Integer pageNum, Integer pageSize);
}
