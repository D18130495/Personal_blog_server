package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ArticleTagMapper;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * article tag service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}


