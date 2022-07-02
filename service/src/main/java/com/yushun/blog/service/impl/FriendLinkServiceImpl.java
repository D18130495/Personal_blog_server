package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.FriendLinkMapper;
import com.yushun.blog.model.friend.FriendLink;
import com.yushun.blog.service.FriendLinkService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * friend link service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 *
 * Cacheable for main page right menu friend link display
 *
 */

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {
    @Override
    @Cacheable(value="friendLink", keyGenerator="keyGenerator")
    public List<FriendLink> getAllFriendLinkList() {
        QueryWrapper<FriendLink> wrapper = new QueryWrapper<>();

        List<FriendLink> friendLinkList = baseMapper.selectList(wrapper);

        return friendLinkList;
    }
}