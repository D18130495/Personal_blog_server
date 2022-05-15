package com.yushun.blog.controller.front;

import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * channel Mapper
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-15
 */

@RestController
@RequestMapping("/front/channel")
public class FrontChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/findAll")
    public List<Channel> findAllChannel() {
        List<Channel> list = channelService.list();
        return list;
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        channelService.removeById(id);
    }
}
