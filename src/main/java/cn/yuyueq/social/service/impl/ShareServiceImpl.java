package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.FollowDao;
import cn.yuyueq.social.dao.ShareDao;
import cn.yuyueq.social.domain.node.Share;
import cn.yuyueq.social.domain.node.User;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author liangyihui.net
 * @since 2022/3/16
 */
@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    ShareDao shareDao;

    @Autowired
    FollowDao followDao;

    @Override
    public ResponseInfo addShare(String account, String publisher, String publisherimg, String title, String content, String related_hobby, Long hobbyid, String imgurl, String address) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
        Share share = shareDao.publishShare(account, publisher, publisherimg, title, content, related_hobby, hobbyid, imgurl, address, time);
        return new ResponseInfo(share != null ? "success" : "fail", share != null, share);
    }

    @Override
    public ResponseInfo deleteShare(String account, Long shareid) {
        Integer affect_rows = shareDao.deleteShareById(account, shareid);
        return new ResponseInfo(affect_rows > 0 ? "success" : "fail", affect_rows > 0, affect_rows);
    }

    @Override
    public ResponseInfo getShareByAccount(String account) {
        ArrayList<Share> shares = (ArrayList<Share>) shareDao.getShareByAccount(account);
        return new ResponseInfo("success", true, shares);
    }

    @Override
    public String publishShare(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";
        map.put("user", user);
        map.put("index", "发现动态");
        map.put("title", "发布动态");
        return "addshare";
    }

    @Override
    public String getShareByFriend(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";
        Long following_num = followDao.howManyIFollow(user.getAccount());
        Long follower_num = followDao.howManyPeopleFollowMe(user.getAccount());
        session.setAttribute("follower", follower_num);
        session.setAttribute("myfollowing", following_num);

        // 读取动态内容
        ArrayList<Share> shares = shareDao.getFriendShares(user.getAccount());
        map.put("shares", shares);
        map.put("user", user);
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("index", "发现动态");
        map.put("title", "好友动态");
        return "sharelist";
    }

    @Override
    public String getShareByHobby(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";
        Long following_num = followDao.howManyIFollow(user.getAccount());
        Long follower_num = followDao.howManyPeopleFollowMe(user.getAccount());
        session.setAttribute("follower", follower_num);
        session.setAttribute("myfollowing", following_num);

        // 读取动态内容
        ArrayList<Share> shares = shareDao.recommendByHobby(user.getAccount());
        map.put("shares", shares);
        map.put("user", user);
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("index", "发现动态");
        map.put("title", "好友动态");
        return "sharelist";
    }
}
