package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.friend.FriendLink;
import com.yushun.blog.service.FriendLinkService;
import com.yushun.blog.vo.admin.friend.FriendLinkQueryVo;
import com.yushun.blog.vo.admin.friend.FriendLinkUpdateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * admin friend controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-24
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/friendLink")
public class AdminFriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/getFriendLinkByFriendLinkId/{friendLinkId}")
    public Result getFriendLinkByFriendLinkId(@PathVariable Long friendLinkId) {
        FriendLink friendLink = friendLinkService.getById(friendLinkId);

        return Result.ok(friendLink);
    }

    @PostMapping("/addNewFriendLink")
    public Result addNewFriendLink(FriendLink newFriendLink) {
        List<FriendLink> friendLinkList = friendLinkService.list();

        for(FriendLink existsFriendLink : friendLinkList) {
            if(existsFriendLink.getTitle().equals(newFriendLink.getTitle())) {
                return Result.fail().message("Friend link already exists");
            }
        }

        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(newFriendLink, friendLink);
        friendLink.setCreateTime(new Date());
        friendLink.setUpdateTime(new Date());
        friendLink.setIsDeleted(0);

        boolean save = friendLinkService.save(friendLink);

        if(save) {
            return Result.ok().message("Successfully added new friend link");
        }else {
            return Result.fail().message("Failed to add new friend link");
        }
    }

    @PutMapping("/updateFriendLinkByFriendLinkId")
    public Result updateFriendLinkByFriendLinkId(FriendLinkUpdateVo friendLinkUpdateVo) {
        QueryWrapper<FriendLink> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", friendLinkUpdateVo.getId());
        List<FriendLink> friendLinkList = friendLinkService.list(wrapper);

        for(FriendLink existsFriendLink : friendLinkList) {
            if(existsFriendLink.getTitle().equals(friendLinkUpdateVo.getTitle())) {
                return Result.fail().message("Friend link already exists");
            }
        }

        FriendLink friendLink = new FriendLink();
        BeanUtils.copyProperties(friendLinkUpdateVo, friendLink);
        friendLink.setUpdateTime(new Date());

        boolean update = friendLinkService.updateById(friendLink);

        if(update) {
            return Result.ok(friendLink).message("Successfully updated friend link");
        }else {
            return Result.fail().message("Failed to update friend link");
        }
    }

    @DeleteMapping("/deleteFriendLinkById/{friendLinkId}")
    public Result deleteFriendLinkById(@PathVariable Long friendLinkId) {
        FriendLink friendLink = friendLinkService.getById(friendLinkId);
        friendLink.setUpdateTime(new Date());

        boolean update = friendLinkService.updateById(friendLink);

        boolean delete = friendLinkService.removeById(friendLinkId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted friend link");
        }else {
            return Result.fail().message("Failed to delete friend link");
        }
    }

    @GetMapping("/getFriendLinkQueryPaginatedList/{current}/{limit}")
    public Result getFriendLinkQueryPaginatedList(@PathVariable Long current,
                                                  @PathVariable Long limit,
                                                  FriendLinkQueryVo friendLinkQueryVo) {
        Page<FriendLink> page = new Page<>(current, limit);

        QueryWrapper<FriendLink> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(friendLinkQueryVo.getTitle())) {
            wrapper.like("title", friendLinkQueryVo.getTitle());
        }

        Page<FriendLink> paginatedFriendLinkList = friendLinkService.page(page, wrapper);

        return Result.ok(paginatedFriendLinkList);
    }
}
