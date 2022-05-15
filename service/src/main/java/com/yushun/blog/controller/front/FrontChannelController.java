package com.yushun.blog.controller.front;

import com.yushun.blog.common.result.Result;
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

@CrossOrigin
@RestController
@RequestMapping("/front/channel")
public class FrontChannelController {
    @Autowired
    private ChannelService channelService;

    @GetMapping("/findAll")
    public Result findAllChannel() {
        List<Channel> list = channelService.list();
        return Result.ok(list);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        channelService.removeById(id);
    }

    @GetMapping("/queryByPos/{pos}")
    public Result getChannelByPos(@PathVariable String pos){
        System.out.println(pos);
        return Result.ok("123");
    }
}
