package com.yushun.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yushun.blog.common.result.Result;
import com.yushun.blog.common.utils.RequestUtils;
import com.yushun.blog.model.channel.Channel;
import com.yushun.blog.model.tag.Tag;
import com.yushun.blog.model.user.User;
import com.yushun.blog.service.ChannelService;
import com.yushun.blog.service.UserService;
import com.yushun.blog.vo.admin.channel.ChannelQueryVo;
import com.yushun.blog.vo.admin.channel.ChannelUpdateVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * admin channel controller
 * </p>
 *
 * @author yushun zeng
 * @since 2022-5-23
 */

@CrossOrigin
@RestController
@RequestMapping("/admin/channel")
public class AdminChannelController {
    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserService userService;

    @GetMapping("/getChannelByChannelId/{channelId}")
    public Result getChannelByChannelId(@PathVariable Long channelId) {
        Channel channel = channelService.getById(channelId);

        return Result.ok(channel);
    }

    @PostMapping("/addNewChannel")
    public Result addNewChannel(Channel newChannel) {
        List<Channel> channelList = channelService.list();

        for(Channel existsChannel : channelList) {
            if(existsChannel.getName().equals(newChannel.getName())) {
                return Result.fail().message("Channel already exists");
            }
        }

        Channel channel = new Channel();
        BeanUtils.copyProperties(newChannel, channel);

        if(channel.getParentId() == null) {
            channel.setParentId(0L);
        }

        channel.setCreateTime(new Date());
        channel.setUpdateTime(new Date());
        channel.setIsDeleted(0);

        boolean save = channelService.save(channel);

        if(save) {
            return Result.ok().message("Successfully added new channel");
        }else {
            return Result.fail().message("Failed to add new channel");
        }
    }

    @PutMapping("/updateChannelByChannelId")
    public Result updateChannelByChannelId(ChannelUpdateVo channelUpdateVo) {
        QueryWrapper<Channel> wrapper = new QueryWrapper<>();
        wrapper.notIn("id", channelUpdateVo.getId());
        List<Channel> channelList = channelService.list(wrapper);

        for(Channel existsChannel : channelList) {
            if(existsChannel.getName().equals(channelUpdateVo.getName())) {
                return Result.fail().message("Channel already exists");
            }
        }

        Channel channel = new Channel();
        BeanUtils.copyProperties(channelUpdateVo, channel);

        if(channel.getParentId() == null) {
            channel.setParentId(0L);
        }

        channel.setUpdateTime(new Date());

        boolean update = channelService.updateById(channel);

        if(update) {
            return Result.ok(channel).message("Successfully updated channel");
        }else {
            return Result.fail().message("Failed to update channel");
        }
    }

    @DeleteMapping("/deleteChannelById/{channelId}")
    public Result deleteChannelById(@PathVariable Long channelId) {
        Channel channel = channelService.getById(channelId);
        channel.setUpdateTime(new Date());

        boolean update = channelService.updateById(channel);

        boolean delete = channelService.removeById(channelId);

        if(update && delete) {
            return Result.ok().message("Successfully deleted channel");
        }else {
            return Result.fail().message("Failed to delete channel");
        }
    }

    @GetMapping("/getChannelQueryPaginatedList/{current}/{limit}")
    public Result getChannelQueryPaginatedList(@PathVariable Long current,
                                               @PathVariable Long limit,
                                               ChannelQueryVo channelQueryVo) {
        Page<Channel> page = new Page<>(current, limit);

        QueryWrapper<Channel> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(channelQueryVo.getName())) {
            wrapper.like("name", channelQueryVo.getName());
        }

        Page<Channel> paginatedChannelList = channelService.page(page, wrapper);

        for(Channel channel : paginatedChannelList.getRecords()) {
            User user = userService.getById(channel.getCreateUser());

            if(user != null) {
                channel.setUserName(user.getUserName());
            }
        }

        return Result.ok(paginatedChannelList);
    }

    @GetMapping("/getParentTreeData")
    public Result getParentTreeData() {
        List<Channel> channelList = channelService.list();
        List<Map<String,Object>> mapList = new ArrayList<>();

        for(Channel parent : channelList) {
            if(parent.getParentId() == 0) {
                Map<String,Object> map = new HashMap<>();
                map.put("id", parent.getId());
                map.put("label", parent.getName());

                List<Map<String,Object>> children = new ArrayList<>();

                for (Channel child : channelList) {
                    if (child.getParentId().equals(parent.getId())) {
                        Map<String,Object> subMap = new HashMap<>();
                        subMap.put("id", child.getId());
                        subMap.put("label", child.getName());
                        children.add(subMap);
                    }
                }
                map.put("children",children);
                mapList.add(map);
            }
        }

        return Result.ok(mapList);
    }

    @PostMapping("/uploadChannelImage")
    public Result upload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String ex = originalFilename.substring(originalFilename.lastIndexOf(".") + 1,originalFilename.length());
        String newFileNamePrefix = UUID.randomUUID().toString();
        String newFileName = newFileNamePrefix + "." + ex;
        file.transferTo(new File("E:/Personal_blog/Personal_blog_client/public/images/channel", newFileName));

        return Result.ok(RequestUtils.getBasePath(request) + "images/channel/" + newFileName).message("Successfully uploaded channel image");
    }
}
