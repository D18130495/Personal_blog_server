package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ArticleAttachmentMapper;
import com.yushun.blog.model.article.ArticleAttachment;
import com.yushun.blog.service.ArticleAttachmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * article attachment service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Service
public class ArticleAttachmentServiceImpl extends ServiceImpl<ArticleAttachmentMapper, ArticleAttachment> implements ArticleAttachmentService {

}