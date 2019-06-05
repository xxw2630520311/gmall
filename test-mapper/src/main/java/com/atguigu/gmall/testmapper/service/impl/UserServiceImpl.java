package com.atguigu.gmall.testmapper.service.impl;


import com.atguigu.gmall.testmapper.bean.UserInfo;
import com.atguigu.gmall.testmapper.mapper.UserMapper;
import com.atguigu.gmall.testmapper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UserMapper userMapper;

    @Override
    public List<UserInfo> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public boolean addUserInfo(UserInfo userInfo) {

        int insert = userMapper.insert(userInfo);
        return insert > 0;
    }

    /** 根据主键修改 */
    @Override
    public boolean UpdateUserById(String id) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setNickName("测试根据主键修改");
        int i = userMapper.updateByPrimaryKey(userInfo);
        return i > 0;
    }

    /** 根据名称修改 */
    @Override
    public boolean UpdateUserById(UserInfo userInfo) {
        userInfo.setNickName("asdasd");
        Example example = new Example(UserInfo.class,true);
        example.createCriteria().andEqualTo("name",userInfo.getName());
        int i = userMapper.updateByExample(userInfo,example);
        return i > 0;
    }

    @Override
    public boolean delUserById(String id) {
        int i = userMapper.deleteByPrimaryKey(id);
        return i > 0;
    }

    @Override
    public boolean delUserById(UserInfo userInfo) {
        int delete = userMapper.delete(userInfo);
        return delete > 0;
    }
}
