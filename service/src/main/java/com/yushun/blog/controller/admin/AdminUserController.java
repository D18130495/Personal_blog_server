package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.JwtUtils;
import com.yushun.blog.common.utils.RequestUtils;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.UserService;
import com.yushun.blog.vo.admin.user.NewUserVo;
import com.yushun.blog.vo.admin.user.UpdateUserVo;
import com.yushun.blog.vo.admin.user.UserLoginVo;
import com.yushun.blog.vo.admin.user.UserQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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

    @PutMapping("/updateUser")
    public Result updateUser(UpdateUserVo updateUserVo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", updateUserVo.getId());
        List<User> userList = userService.list(wrapper);

        for(User user : userList) {
            if(user.getUserName().equals(updateUserVo.getUserName())) {
                return Result.fail().message("User already exists");
            }
        }

        User user = new User();
        BeanUtils.copyProperties(updateUserVo, user);
        user.setUpdateTime(new Date());

        boolean update = userService.updateById(user);

        if(update) {
            return Result.ok(user).message("Successfully updated user");
        }else {
            return Result.fail().message("Failed to update user");
        }
    }

    @DeleteMapping("/deleteUserById/{userId}")
    public Result deleteUserById(@PathVariable Long userId) {
        User user = userService.getById(userId);
        user.setUpdateTime(new Date());

        boolean update = userService.updateById(user);

        boolean delete = userService.removeById(userId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted user");
        }else {
            return Result.fail().message("Failed to delete user");
        }
    }

    @GetMapping("/getUserQueryPaginatedList/{current}/{limit}")
    public Result getUserQueryPaginatedList(@PathVariable Long current,
                                            @PathVariable Long limit,
                                            UserQueryVo userQueryVo) {
        Page<User> page = new Page<>(current, limit);

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(userQueryVo.getUserName())) {
            wrapper.like("user_name", userQueryVo.getUserName());
        }

        if(!StringUtils.isEmpty(userQueryVo.getNickName())) {
            wrapper.like("nick_name", userQueryVo.getNickName());
        }

        Page<User> paginatedUserList = userService.page(page, wrapper);

        return Result.ok(paginatedUserList);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();
        //获取文件名后缀
        String ex = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        String newFileNamePrefix = UUID.randomUUID().toString();
        String newFileName = newFileNamePrefix + "." + ex;
        file.transferTo(new File("/data/app/personal_blog_client/dist/images/avatar", newFileName));

        return Result.ok(RequestUtils.getBasePath(request) + "images/avatar/" + newFileName).message("Successfully uploaded avatar");
    }
}
