package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.UserNullUtils;
import com.yushun.blog.model.article.Article;
import com.yushun.blog.model.article.ArticleTag;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getAllTag")
    public Result getAllTag() {
        List<Tag> tagList = tagService.list();

        return Result.ok(tagList);
    }

    @GetMapping("/getTagByTagId/{tagId}")
    public Result getTagByTagId(@PathVariable Long tagId) {
        Tag tag = tagService.getById(tagId);

        return Result.ok(tag);
    }
}
