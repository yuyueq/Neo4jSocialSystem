package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FollowService {

    ResponseInfo followIt(String follower, String following);

    ResponseInfo unfollow(String follower, String following);

    String getFans(HttpServletRequest request, Map<String, Object> map);

    String getMyFollowing(HttpServletRequest request, Map<String, Object> map);

}
