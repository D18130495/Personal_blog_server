package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.comment.Comment;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.CommentService;
import com.yushun.blog.vo.admin.comment.CommentQueryVo;
import com.yushun.blog.vo.admin.tag.TagQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * admin comment controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-25
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/comment")
public class AdminCommentController {
    @Autowired
    private CommentService commentService;

    @DeleteMapping("/deleteCommentById/{commentId}")
    public Result deleteCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getById(commentId);
        comment.setUpdateTime(new Date());

        boolean update = commentService.updateById(comment);

        boolean delete = commentService.removeById(commentId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted commend");
        }else {
            return Result.fail().message("Failed to delete commend");
        }
    }

    @GetMapping("/getCommentQueryPaginatedList/{current}/{limit}")
    public Result getCommentQueryPaginatedList(@PathVariable Long current,
                                               @PathVariable Long limit,
                                               CommentQueryVo commentQueryVo) {
        Page<Comment> page = new Page<>(current, limit);

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(commentQueryVo.getContent())) {
            wrapper.like("content", commentQueryVo.getContent());
        }

        Page<Comment> paginatedCommentList = commentService.page(page, wrapper);

        return Result.ok(paginatedCommentList);
    }

    @GetMapping("/getAllUnacceptCommentPaginatedList/{current}/{limit}")
    public Result getAllUnacceptCommentPaginatedList(@PathVariable Long current,
                                                     @PathVariable Long limit) {
        Page<Comment> page = new Page<>(current, limit);

        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 0);

        Page<Comment> paginatedCommentList = commentService.page(page, wrapper);

        return Result.ok(paginatedCommentList);
    }

    @GetMapping("/getCommentStatus")
    public Result getStatusComment(){
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("status", 0);
        commentQueryWrapper.eq("status", 2);
        int total = commentService.count(commentQueryWrapper);

        return Result.ok(total);
    }
}
