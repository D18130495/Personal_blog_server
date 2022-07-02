package com.yushun.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yushun.blog.model.tag.Tag;

import java.util.List;

/**
 * <p>
 * tag service
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-19
 */

public interface TagService extends IService<Tag> {
    boolean checkIfTagCanBeRemoved(Long tagId);

    List<Tag> getAllTag();
}