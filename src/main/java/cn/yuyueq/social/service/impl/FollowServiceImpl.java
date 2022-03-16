package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.FollowDao;
import cn.yuyueq.social.dao.UserDao;
import cn.yuyueq.social.domain.node.User;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

/**
 * <p>
 * 关注模块
 * </p>
 *
 * @author wenxin.du
 * @since 2022/3/16
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FollowDao followDao;

    @Autowired
    UserDao userDao;

    public static boolean contains(ArrayList<User> arr, User target) {
        for (User user : arr) {
            if (user.getId().equals(target.getId()))
                return true;
        }
        return false;
    }

    @Override
    public ResponseInfo followIt(String follower, String following) {
        Long id = followDao.createFollow(follower, following);
        if (id != null) {
            return new ResponseInfo("success", true, id);
        }
        return new ResponseInfo("fail", false, null);
    }

    @Override
    public ResponseInfo unfollow(String follower, String following) {
        Long num = followDao.deleteRelation(follower, following);
        if (num == 0) {
            return new ResponseInfo("fail", false, num);
        }
        return new ResponseInfo("success", true, num);
    }

    @Override
    public String getFans(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        ArrayList<User> follower = (ArrayList<User>) followDao.getPeopleWhoFollowMe(user.getAccount());
        ArrayList<User> myfollowing = (ArrayList<User>) followDao.getMyFollowing(user.getAccount());
        HashSet<User> recommends = new HashSet<>();
        HashSet<User> subFollowing = new HashSet<>();
        for (User value : follower) {
            if (contains(myfollowing, value)) {
                subFollowing.add(value);
                continue;
            }
            recommends.add(value);
        }
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("recommends", recommends);
        map.put("mystar", subFollowing);
        map.put("user", user);
        map.put("title", "关注我的人");
        map.put("index", "首页");
        map.put("hascategory", true);
        return "userlist";
    }

    @Override
    public String getMyFollowing(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        ArrayList<User> following = (ArrayList<User>) followDao.getMyFollowing(user.getAccount());
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("recommends", following);
        map.put("user", user);
        map.put("title", "我关注的人");
        map.put("index", "首页");
        return "myfollowing";
    }
}
