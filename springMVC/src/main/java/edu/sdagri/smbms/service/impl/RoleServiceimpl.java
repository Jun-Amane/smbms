package edu.sdagri.smbms.service.impl;

import edu.sdagri.smbms.entity.Role;
import edu.sdagri.smbms.mapper.RoleMapper;
import edu.sdagri.smbms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Package: edu.sdagri.smbms.service.impl
 * File: RoleServiceimpl.java
 * Author: Yang Song
 * Date: 05/01/2025
 * Time: 10:04
 * Description: TODO: change me
 */
@Service
public class RoleServiceimpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> findAll(){

        return this.roleMapper.selectAll();
    }
}
