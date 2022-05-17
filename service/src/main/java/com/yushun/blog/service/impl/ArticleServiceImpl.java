package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ArticleMapper;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * article service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-17
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getRandomArticle() {
        List<Article> articleList = articleMapper.getRandomArticle();
//        List<Article> articleList = new ArrayList<>();
//        articleList.add(baseMapper.selectById(60));
//        System.out.println(baseMapper.selectById(60).getTitleImg());
        return articleList;
    }
}
