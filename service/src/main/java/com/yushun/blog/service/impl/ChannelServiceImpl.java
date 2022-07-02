package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.service.ChannelService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * channel service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 *
 * Cacheable for main page channel display
 *
 */

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

    @Override
    @Cacheable(value="channel", keyGenerator="keyGenerator")
    public List<Channel> getChannelByPos(String pos) {
        QueryWrapper<Channel> wrapper = new QueryWrapper<>();
        wrapper.eq("pos", pos);
        List<Channel> channelList = baseMapper.selectList(wrapper);

        return channelList;
    }
}
