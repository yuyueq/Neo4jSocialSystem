package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.FollowDao;
import cn.yuyueq.social.dao.RecommendDao;
import cn.yuyueq.social.dao.ShareDao;
import cn.yuyueq.social.dao.UserDao;
import cn.yuyueq.social.domain.node.Share;
import cn.yuyueq.social.domain.node.User;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
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
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    FollowDao followDao;

    @Autowired
    ShareDao shareDao;

    @Autowired
    RecommendDao recommendDao;

    public static HashSet<User> subList(ArrayList<User> list, int length, String account) {
        HashSet<User> res = new HashSet<>();
        int range = Math.min(list.size(), length);
        for (int i = 0; i < range; i++) {
            if (list.get(i).getAccount().equals(account)) continue;
            res.add(list.get(i));
        }
        return res;
    }


    @Override
    public String index(Map<String, Object> paramMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        } else {
            return "redirect:/content";
        }
    }

    @Override
    public String index(String account, String password, Map<String, Object> paramMap, HttpServletRequest request) {
        User user = userDao.checkUser(account, password);
        if (user == null) {
            paramMap.put("msg", "用户名或者密码错误");
            return "login";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:content";
        }
    }

    @Override
    public String content(HttpServletRequest request, Map<String, Object> paramMap) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null)
            return "login";
        Long following_num = followDao.howManyIFollow(user.getAccount());
        Long follower_num = followDao.howManyPeopleFollowMe(user.getAccount());
        // 读取动态内容
        ArrayList<Share> shares = shareDao.getFriendShares(user.getAccount());
        // 读取推荐用户列表
        ArrayList<User> byfriend = (ArrayList<User>) recommendDao.byFriend(user.getAccount());
        ArrayList<User> byshare = (ArrayList<User>) recommendDao.byshare(user.getAccount());
        ArrayList<User> byhobby = (ArrayList<User>) recommendDao.byHobby(user.getAccount());

        paramMap.put("byfriend", subList(byfriend, 3, user.getAccount()));
        paramMap.put("byshare", subList(byshare, 3, user.getAccount()));
        paramMap.put("byhobby", subList(byhobby, 3, user.getAccount()));

        paramMap.put("shares", shares);
        paramMap.put("user", user);
        paramMap.put("myfollowing", following_num);
        paramMap.put("follower", follower_num);
        return "content";
    }

    @Override
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);
        return "login";
    }

    @Override
    public ResponseInfo getUser(String account) {
        User user = userDao.getUserByAccount(account);
        if (user != null) {
            return new ResponseInfo("1", true, user);
        }
        return new ResponseInfo("0", true, null);
    }

    @Override
    public ResponseInfo getAllUser() {
        ArrayList<User> users = (ArrayList<User>) userDao.getAllUser();
        return new ResponseInfo(Integer.toString(users.size()), true, users);
    }

    @Override
    public String addUser(String account, String password, Integer age, String gender, String email, String address, String nickname, Map<String, Object> map) {
        User user = userDao.getUserByAccount(account);
        if (user != null) {
            map.put("msg", "该账号已经存在，请登录");
            return "login";
        }
        user = userDao.adduser(account, password, nickname, age, gender, email, address);
        if (user != null) {
            return "redirect:/content";
        }
        map.put("msg", "注册失败，请联系管理员");
        return "register";
    }

    @Override
    public ResponseInfo deleteUser(String account) {
        User user = userDao.deleteUserByAccount(account);
        return new ResponseInfo("删除成功", true, user);
    }

    @Override
    public ResponseInfo fixInfo(String account, String nickname, Integer age, String email, String address) {
        Long num = userDao.fixInfo(account, nickname, age, address, email);
        return new ResponseInfo(num > 0 ? "success" : "fail", num > 0, num);
    }

    @Override
    public ResponseInfo fixPass(String account, String password) {
        Long num = userDao.fixPass(account, password);
        return new ResponseInfo(num > 0 ? "success" : "fail", num > 0, num);
    }

    @Override
    public ResponseInfo fixImg(String account, String imgurl) {
        Long num = userDao.fiximg(account, imgurl);
        return new ResponseInfo(num > 0 ? "success" : "fail", num > 0, num);
    }
}
