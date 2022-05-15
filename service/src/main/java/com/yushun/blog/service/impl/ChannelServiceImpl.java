package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ChannelMapper;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.service.ChannelService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * channel service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements ChannelService {

}
