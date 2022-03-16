package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.FollowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenxin.du
 * @since 2022-01-23
 */
@Controller
public class FollowController {

    @Resource
    private FollowService followService;

    /**
     * 关注别人
     *
     * @param follower
     * @param following
     * @return
     */
    @PostMapping("/follow/followit")
    @ResponseBody
    public ResponseInfo followIt(@RequestParam("follower") String follower, @RequestParam("following") String following) {
        return followService.followIt(follower, following);
    }

    /**
     * 取关别人
     *
     * @param follower
     * @param following
     * @return
     */
    @PostMapping("/follow/unfollow")
    @ResponseBody
    public ResponseInfo unfollow(@RequestParam("follower") String follower, @RequestParam("following") String following) {
        return followService.unfollow(follower, following);
    }


    /**
     * 查询关注我的人
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/user/follower")
    public String getFans(HttpServletRequest request, Map<String, Object> map) {
        return followService.getFans(request, map);
    }

    /**
     * 查询我关注的人
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/user/myfollowing")
    public String getMyFollowing(HttpServletRequest request, Map<String, Object> map) {
        return followService.getMyFollowing(request, map);
    }
}
