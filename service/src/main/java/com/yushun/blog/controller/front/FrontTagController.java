package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.ArticleTagService;
import com.yushun.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * front tag controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

@CrossOrigin
@RestController
@RequestMapping("/front/tag")
public class FrontTagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleTagService articleTagService;

    @GetMapping("/getAllTag")
    public Result getAllTag() {
        List<Tag> tagList = tagService.getAllTag();

        return Result.ok(tagList);
    }

    @GetMapping("/getTagByTagId/{tagId}")
    public Result getTagByTagId(@PathVariable Long tagId) {
        Tag tag = tagService.getById(tagId);

        return Result.ok(tag);
    }

    @GetMapping("/getArticleRelatedTagByArticleId/{articleId}")
    public Result getArticleRelatedTagByArticleId(@PathVariable Long articleId) {
        QueryWrapper<ArticleTag> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId);

        List<ArticleTag> articleTagList = articleTagService.list(wrapper);

        if(articleTagList.isEmpty()) {
            return Result.ok();
        }else {
            List<Tag> tagList = new ArrayList<>();

            for(ArticleTag articleTag : articleTagList) {
                Tag tag = tagService.getById(articleTag.getTagId());
                tagList.add(tag);
            }

            return Result.ok(tagList);
        }
    }
}
