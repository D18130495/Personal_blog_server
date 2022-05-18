package com.yushun.blog.common.utils;

import com.yushun.blog.model.user.User;

public class UserNullUtils {
    public static User userIsNull(){
        User nullUser = new User();
        nullUser.setUserName("匿名");
        nullUser.setAvatar("hhh");
        nullUser.setNickName("匿名");
        return nullUser;
    }
}
