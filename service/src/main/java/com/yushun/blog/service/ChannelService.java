package com.yushun.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushun.blog.model.channel.Channel;

import java.util.List;

/**
 * <p>
 * channel service
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

public interface ChannelService extends IService<Channel> {
    List<Channel> getChannelByPos(String pos);
}
