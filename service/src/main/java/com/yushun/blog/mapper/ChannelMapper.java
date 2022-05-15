package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.channel.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * channel Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@Repository
@Mapper
public interface ChannelMapper extends BaseMapper<Channel> {

}
