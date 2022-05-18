package com.yushun.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yushun.blog.model.article.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * article Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-17
 */

@Repository
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> getRandomArticle();

    List<Article> queryArticleByChannelId(Map<String, Object> paramMap);
}
