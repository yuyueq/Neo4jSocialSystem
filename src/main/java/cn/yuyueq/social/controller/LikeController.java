package cn.yuyueq.social.controller;

import cn.yuyueq.social.dao.LikeDao;
import cn.yuyueq.social.domain.util.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuyueq
 * @since 2022-01-23
 */

@Controller
public class LikeController {
    @Autowired
    LikeDao likeDao;
    @PostMapping("/user/like")
    @ResponseBody
    public ResponseInfo likeHobby(@RequestParam("account") String account,
                                  @RequestParam("hobbyid") Long hobbyid){
        Integer num=likeDao.likeHobby(account,hobbyid);
        return new ResponseInfo(num==2?"添加兴趣成功":"添加兴趣失败",num==2,num);
    }

    @PostMapping("/user/unlike")
    @ResponseBody
    public ResponseInfo unlikeHobby(@RequestParam("account") String account,
                                    @RequestParam("hobbyid") Long hobbyid){
        Integer num=likeDao.unlikeHobby(account,hobbyid);
        return new ResponseInfo(num>0?"删除兴趣成功":"删除兴趣失败",num>0,num);
    }
}