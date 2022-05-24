package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.TagService;
import com.yushun.blog.vo.admin.tag.TagQueryVo;
import com.yushun.blog.vo.admin.tag.TagUpdateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @PostMapping("/addNewTag")
    public Result addNewTag(Tag newTag) {
        List<Tag> tagList = tagService.list();

        for(Tag existsTag : tagList) {
            if(existsTag.getTagName().equals(newTag.getTagName())) {
                return Result.fail().message("Tag already exists");
            }
        }

        Tag tag = new Tag();
        BeanUtils.copyProperties(newTag, tag);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        tag.setIsDeleted(0);

        boolean save = tagService.save(tag);

        if(save) {
            return Result.ok().message("Successfully added new tag");
        }else {
            return Result.fail().message("Failed to add new tag");
        }
    }

    @PutMapping("/updateTagByTagId")
    public Result updateTagByTagId(TagUpdateVo tagUpdateVo) {
        List<Tag> tagList = tagService.list();

        for(Tag existsTag : tagList) {
            if(existsTag.getTagName().equals(tagUpdateVo.getTagName())) {
                return Result.fail().message("Tag already exists");
            }
        }

        Tag tag = new Tag();
        BeanUtils.copyProperties(tagUpdateVo, tag);
        tag.setUpdateTime(new Date());

        boolean update = tagService.updateById(tag);

        if(update) {
            return Result.ok(tag).message("Successfully updated tag");
        }else {
            return Result.fail().message("Failed to update tag");
        }
    }

    @DeleteMapping("/deleteTagById/{tagId}")
    public Result deleteTagById(@PathVariable Long tagId) {
        Tag tag = tagService.getById(tagId);
        tag.setUpdateTime(new Date());

        boolean update = tagService.updateById(tag);

        boolean delete = tagService.removeById(tagId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted tag");
        }else {
            return Result.fail().message("Failed to delete tag");
        }
    }

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
