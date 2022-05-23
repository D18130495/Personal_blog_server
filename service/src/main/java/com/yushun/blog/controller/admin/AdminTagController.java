package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.TagService;
import com.yushun.blog.vo.admin.tag.TagQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * admin tag controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-24
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/getTagQueryPaginatedList/{current}/{limit}")
    public Result getTagQueryPaginatedList(@PathVariable Long current,
                                           @PathVariable Long limit,
                                           TagQueryVo tagQueryVo) {
        Page<Tag> page = new Page<>(current, limit);

        QueryWrapper<Tag> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(tagQueryVo.getTagName())) {
            wrapper.like("tag_name", tagQueryVo.getTagName());
        }

        Page<Tag> paginatedTagList = tagService.page(page, wrapper);

        return Result.ok(paginatedTagList);
    }
}
