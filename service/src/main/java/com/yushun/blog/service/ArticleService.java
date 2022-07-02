package com.yushun.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    List<Article> getToppedArticleListByChannelId(Long channelId);

    List<Article> getToppedArticleByTagId(Long tagId);

    Article getArticleDetailByArticleId(Article article);

    boolean addNewArticle(Article newArticle);

    Article packageArticles(Article article);

    boolean updateArticle(Article updateArticle);

    Page<Article> getPaginatedArticlesList(Page<Article> page, QueryWrapper<Article> wrapper);
}
