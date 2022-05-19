package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.friend.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 * Friend Link Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 20202-5-19
 */

@Repository
@Mapper
public interface FriendLinkMapper extends BaseMapper<FriendLink> {

}