package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * comment Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-20
 */

@Repository
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
