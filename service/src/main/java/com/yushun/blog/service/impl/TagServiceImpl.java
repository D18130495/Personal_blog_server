package com.yushun.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yushun.blog.mapper.TagMapper;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.service.TagService;
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
}
