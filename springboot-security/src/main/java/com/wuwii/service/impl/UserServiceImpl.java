package com.wuwii.service.impl;

import com.wuwii.dao.UserDao;
import com.wuwii.entity.User;
import com.wuwii.service.UserService;
import com.wuwii.vo.UserAddDTO;
import com.wuwii.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserVO castUserVO(User user) {
        return null;
    }

    @Override
    public List<UserVO> castUserVO(List<User> users) {
        List<UserVO> userViews = new LinkedList<>();
        for (User user : users) {
            UserVO userView = new UserVO();
            BeanUtils.copyProperties(user, userView);
            userViews.add(userView);
        }
        return userViews;
    }

    @Override
    @Cacheable
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void insertUser(User user) {
        user.setCreateDate(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void insertUser(UserAddDTO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        insertUser(user);
    }
}
