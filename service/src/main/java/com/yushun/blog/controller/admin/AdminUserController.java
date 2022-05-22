package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.JwtUtils;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.UserService;
import com.yushun.blog.vo.user.NewUserVo;
import com.yushun.blog.vo.user.UserLoginVo;
import com.yushun.blog.vo.user.UserQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * admin user controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-21
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Result login(UserLoginVo userLoginVo) {
        String userName = userLoginVo.getUserName();
        String passWord = userLoginVo.getPassWord();

        QueryWrapper userWrapper = new QueryWrapper();
        userWrapper.eq("user_name", userName);
        userWrapper.eq("password", passWord);

        User user = userService.getOne(userWrapper);

        if (user!= null){
            String token = JwtUtils.sign(user);

            if (user.getStatus().equals("F")){
                return Result.fail("Your account has been locked");
            }

            if (user.getStatus().equals("D")){
                return Result.fail("Your account has been deleted");
            }

            Map<String, Object> userData = new HashMap<>();
            userData.put("token", token);
            userData.put("user", user);

            return Result.ok(userData).message("Successfully login");
        }else{
            return Result.fail().message("UserName or Password is incorrect");
        }
    }

    @PostMapping("/addNewUser")
    public Result addNewUser(NewUserVo newUserVo) {
        List<User> userList = userService.list();

        for(User user : userList) {
            if(user.getUserName().equals(newUserVo.getUserName())) {
                return Result.fail().message("User already exists");
            }
        }

        User user = new User();
        BeanUtils.copyProperties(newUserVo, user);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);
        
        boolean save = userService.save(user);

        if(save) {
            return Result.ok().message("Successfully added new user");
        }else {
            return Result.fail().message("Failed to add new user");
        }
    }

    @GetMapping("/getUserQueryPaginatedList/{current}/{limit}")
    public Result getUserQueryPaginatedList(@PathVariable Long current,
                                            @PathVariable Long limit,
                                            UserQueryVo userQueryVo) {
        Page<User> page = new Page<>(current, limit);
        System.out.println(userQueryVo.getUserName());

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(userQueryVo.getUserName())) {
            wrapper.like("user_name", userQueryVo.getUserName());
        }

        if(!StringUtils.isEmpty(userQueryVo.getNickName())) {
            wrapper.like("nick_name", userQueryVo.getNickName());
        }

        Page<User> paginatedArticlesList = userService.page(page, wrapper);

        return Result.ok(paginatedArticlesList);
    }
}
