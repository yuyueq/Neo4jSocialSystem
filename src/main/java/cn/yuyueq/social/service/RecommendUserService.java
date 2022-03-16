package cn.yuyueq.social.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RecommendUserService {

    String recommendFriendsByFriend(HttpServletRequest request, Map<String, Object> map);

    String recommendFriendsByShare(HttpServletRequest request, Map<String, Object> map);

    String recommendFriendsByHobby(HttpServletRequest request, Map<String, Object> map);
}
