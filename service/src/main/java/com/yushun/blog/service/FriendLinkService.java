package com.yushun.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushun.blog.model.friend.FriendLink;

import java.util.List;

/**
 * <p>
 * friend link service
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

public interface FriendLinkService extends IService<FriendLink> {
    List<FriendLink> getAllFriendLinkList();
}