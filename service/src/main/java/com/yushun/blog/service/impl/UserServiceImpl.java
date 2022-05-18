package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.UserMapper;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * user service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-18
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
