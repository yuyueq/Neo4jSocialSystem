package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.ShareService;
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
 * @author yuyueq
 * @since 2022-01-23
 */
@Controller
public class ShareController {


    @Resource
    private ShareService shareService;

    /**
     * 发布动态
     *
     * @param account
     * @param publisher
     * @param publisherimg
     * @param title
     * @param content
     * @param related_hobby
     * @param hobbyid
     * @param imgurl
     * @param address
     * @return
     */
    @PostMapping("/share/add")
    @ResponseBody
    public ResponseInfo addShare(
            @RequestParam("account") String account, @RequestParam("publisher") String publisher,
            @RequestParam("publisherimg") String publisherimg, @RequestParam("title") String title,
            @RequestParam("content") String content, @RequestParam("related_hobby") String related_hobby,
            @RequestParam("hobbyid") Long hobbyid,
            @RequestParam("imgurl") String imgurl, @RequestParam("address") String address) {
        return shareService.addShare(account, publisher, publisherimg, title, content, related_hobby, hobbyid, imgurl, address);
    }

    /**
     * 删除动态
     *
     * @param account
     * @param shareid
     * @return
     */
    @PostMapping("/share/delete")
    @ResponseBody
    public ResponseInfo deleteShare(@RequestParam("account") String account,
                                    @RequestParam("shareid") Long shareid) {
        return shareService.deleteShare(account, shareid);
    }

    /**
     * 获取动态
     *
     * @param account
     * @return
     */
    @GetMapping("/share/getbyaccount")
    @ResponseBody
    /**\
     * 这个接口需要更改，动态应该包含点赞数量和评论情况
     */
    public ResponseInfo getShareByAccount(@RequestParam("account") String account) {
        return shareService.getShareByAccount(account);
    }

    /**
     * 发现动态
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/share/publish")
    public String publishShare(HttpServletRequest request, Map<String, Object> map) {
        return shareService.publishShare(request, map);
    }

    /**
     * 通过好友获取动态
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/share/friend")
    public String getShareByFriend(HttpServletRequest request, Map<String, Object> map) {
        return shareService.getShareByFriend(request, map);
    }

    /**
     * 通过兴趣获取动态
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/share/recommend")
    public String getShareByHobby(HttpServletRequest request, Map<String, Object> map) {
        return shareService.getShareByHobby(request, map);
    }
}
