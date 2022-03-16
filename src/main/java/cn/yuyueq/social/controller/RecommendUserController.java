package cn.yuyueq.social.controller;

import cn.yuyueq.social.service.RecommendUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * 用于实现向用户推荐朋友的功能
 * </p>
 *
 * @author yuyueq
 * @since 2022-01-23
 */
@Controller
public class RecommendUserController {

    @Resource
    private RecommendUserService recommendUserService;

    /**
     * 根据关注的人推荐好友
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/recommend/user/byfriend")
    public String recommendFriendsByFriend(HttpServletRequest request, Map<String, Object> map) {
        return recommendUserService.recommendFriendsByFriend(request, map);
    }

    /**
     * 根据动态推荐好友
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/recommend/user/byshare")
    public String recommendFriendsByShare(HttpServletRequest request, Map<String, Object> map) {
        return recommendUserService.recommendFriendsByShare(request, map);
    }

    /**
     * 根据兴趣推荐好友
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/recommend/user/byhobby")
    public String recommendFriendsByHobby(HttpServletRequest request, Map<String, Object> map) {
        return recommendUserService.recommendFriendsByHobby(request, map);
    }
}
