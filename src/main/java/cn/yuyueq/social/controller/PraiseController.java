package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.PraiseService;
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
public class PraiseController {

    @Resource
    private PraiseService praiseService;

    /**
     * 点赞
     * @param account
     * @param shareid
     * @return
     */
    @PostMapping("/praised/zan")
    @ResponseBody
    public ResponseInfo praiseShare(@RequestParam("account") String account, @RequestParam("shareid") Integer shareid) {
        return praiseService.praiseShare(account, shareid);
    }

    /**
     * 取消点赞
     * @param account
     * @param shareid
     * @return
     */
    @PostMapping("/praised/unzan")
    @ResponseBody
    public ResponseInfo unPraiseShare(@RequestParam("account") String account, @RequestParam("shareid") Integer shareid) {
        return praiseService.unpraiseShare(account, shareid);

    }
}
