package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.CommentMapper;
import com.yushun.blog.model.comment.Comment;
import com.yushun.blog.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * comment service impl
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-12
 */

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}