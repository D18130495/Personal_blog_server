package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.FriendLinkMapper;
import com.yushun.blog.model.friend.FriendLink;
import com.yushun.blog.service.FriendLinkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * friend link service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {
}