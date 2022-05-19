package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.article.ArticleTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * article tag Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Repository
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {
}
