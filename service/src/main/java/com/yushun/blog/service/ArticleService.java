package com.yushun.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yushun.blog.model.article.Article;

import java.util.List;

/**
 * <p>
 * article service
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-17
 */

public interface ArticleService extends IService<Article> {
    List<Article> getRandomArticle();

    List<Article> getArticleByChannelId(Long channelId);

    List<Article> getToppedArticleList();

    Article articleDetail(Article article);
}