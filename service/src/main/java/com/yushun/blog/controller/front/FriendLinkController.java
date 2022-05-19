package com.yushun.blog.controller.front;

import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.friend.FriendLink;
import com.yushun.blog.service.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * front friend link controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@CrossOrigin
@RestController
@RequestMapping("/front/friendLink")
public class FriendLinkController {
    @Autowired
    private FriendLinkService friendLinkService;

    @GetMapping("/getAllFriendLinkList")
    public Result getAllTag() {
        List<FriendLink> friendLinkList = friendLinkService.list();

        return Result.ok(friendLinkList);
    }
}

