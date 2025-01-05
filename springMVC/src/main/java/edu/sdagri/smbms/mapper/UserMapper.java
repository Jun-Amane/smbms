package edu.sdagri.smbms.mapper;

import edu.sdagri.smbms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //根据主键id删用户
    void delete(long id);
    //修改用户，先根据id查出来，然后再改
    void update(User user);
    //新增用户
    void insert(User user);
    //使用分页插件，带条件的分页查询
    List<User> selectByPage(@Param("condition") User condition);
    //根据id查user对象
    //select * from smbms_user where id=1
    User selectById(@Param("id") Long id);
    //登录
    User selectUser(@Param("userCode") String userCode, @Param("userPassword") String userPassword);
    //符合条件的记录共条
    Integer selectCount(@Param("condition") User condition);
    //带条件分页查询,当前页的记录集合
    //分页元素:第几页，没条几页，共几条，共几页
    List<User> select(@Param("condition") User condition, @Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);


}
