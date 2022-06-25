package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.ArticleTagMapper;
import com.yushun.blog.mapper.TagMapper;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * tag service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public boolean checkIfTagCanBeRemoved(Long tagId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<ArticleTag>();
        wrapper.eq("tag_id", tagId);

        Integer exit = articleTagMapper.selectCount(wrapper);

        return exit == 0? true : false;
    }
}
