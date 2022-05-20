package com.yushun.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.model.comment.Comment;
import com.yushun.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * front comment controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-20
 */

@CrossOrigin
@RestController
@RequestMapping("/front/comment")
public class FrontCommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/newComment")
    public Result newComment(Comment comment) {
        comment.setStatus(1);
        comment.setCreateTime(new Date());
        comment.setUpdateTime(new Date());
        comment.setIsDeleted(0);
        commentService.save(comment);
        return Result.ok().message("Successfully comment");
    }

    @GetMapping("/getArticleAllComments/{articleId}")
    public Result getArticleAllComments(@PathVariable Long articleId) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>();
        wrapper.eq("article_id", articleId);
        List<Comment> commentList = commentService.list(wrapper);

        return Result.ok(commentList);
    }
}