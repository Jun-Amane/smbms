package edu.sdagri.smbms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.sdagri.smbms.entity.User;
import edu.sdagri.smbms.mapper.UserMapper;
import edu.sdagri.smbms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Package: edu.sdagri.smbms.service.impl
 * File: UserServiceimpl.java
 * Author: Yang Song
 * Date: 04/01/2025
 * Time: 10:49
 * Description: TODO: change me
 */
@Service
public class UserServiceimpl implements UserService {
    /**
     * @param user
     */
    @Autowired
    private UserMapper userMapper;
    @Override
    public void add(User user) {
        user.setUserPassword("123456");
        this.userMapper.insert(user);
    }

    /**
     * @param user
     */
    @Override
    public void modify(User user) {
       this.userMapper.update(user);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public User findtById(Long id) {
        return this.userMapper.selectById(id);
    }

    /**
     * @param id
     */
    @Override
    public void remove(Long id) {
        this.userMapper.delete(id);
    }

    /**
     * @param userCode
     * @param userPassword
     * @return
     */
    @Override
    public User findUser(String userCode, String userPassword) {
        return this.userMapper.selectUser(userCode,userPassword);
    }

    /**
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findByPage(User condition, Integer pageNum, Integer pageSize) {
        PageInfo<User> pageInfo=null;
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = this.userMapper.selectByPage(condition);
        pageInfo=new PageInfo<>(userList);
        return pageInfo;
    }
}
