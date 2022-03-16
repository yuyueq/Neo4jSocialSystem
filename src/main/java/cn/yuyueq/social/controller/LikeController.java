package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenxin.du
 * @since 2022-01-23
 */

@Controller
public class LikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/user/like")
    @ResponseBody
    public ResponseInfo likeHobby(@RequestParam("account") String account,
                                  @RequestParam("hobbyid") Long hobbyid) {
        return likeService.likeHobby(account, hobbyid);
    }

    @PostMapping("/user/unlike")
    @ResponseBody
    public ResponseInfo unlikeHobby(@RequestParam("account") String account,
                                    @RequestParam("hobbyid") Long hobbyid) {
        return likeService.unlikeHobby(account, hobbyid);
    }
}
