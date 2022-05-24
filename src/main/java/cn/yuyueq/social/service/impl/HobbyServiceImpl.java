package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.FollowDao;
import cn.yuyueq.social.dao.HobbyDao;
import cn.yuyueq.social.dao.LikeDao;
import cn.yuyueq.social.domain.node.Hobby;
import cn.yuyueq.social.domain.node.User;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

/**
 * <p>
 * 兴趣模块
 * </p>
 *
 * @author wenxin.du
 * @since 2022/3/16
 */
@Service
public class HobbyServiceImpl implements HobbyService {

    @Autowired
    HobbyDao hobbyDao;

    @Autowired
    LikeDao likeDao;

    @Autowired
    FollowDao followDao;

    public static boolean contains(ArrayList<Hobby> hobbies, Hobby target) {
        for (Hobby hobby : hobbies) {
            if (hobby.getId().equals(target.getId()))
                return true;
        }
        return false;
    }

    @Override
    public String getAll(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) return "login";
        ArrayList<Hobby> hobbies = (ArrayList<Hobby>) hobbyDao.getAllHobbies();
        ArrayList<Hobby> myhobbies = (ArrayList<Hobby>) hobbyDao.getMyHobby(user.getAccount());
        ArrayList<Hobby> res = new ArrayList<>();
        for (Hobby hobby : hobbies) {
            if (contains(myhobbies, hobby)) continue;
            res.add(hobby);
        }
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("myhobbies", myhobbies);
        map.put("hobbies", res);
        map.put("user", user);
        return "hobbys";
    }

    @Override
    public ResponseInfo addHobby(String hname, String htype) {
        Hobby hobby = hobbyDao.addHobby(hname, htype);
        if (hobby == null) {
            return new ResponseInfo("fail", false, null);
        }
        return new ResponseInfo("sucess", true, hobby);
    }

    @Override
    public ResponseInfo deleteHobby(Long id) {
        Hobby hobby = hobbyDao.deleteWithId(id);
        return new ResponseInfo("success", true, hobby);
    }

    @Override
    public ResponseInfo search(String hname) {
        ArrayList<Hobby> hobbies = (ArrayList<Hobby>) hobbyDao.searchHobbyByName(".*" + hname + ".*");
        return new ResponseInfo("", true, hobbies);
    }

    @Override
    public ResponseInfo fix(Long id, String hname, String htype) {
        Hobby hobby = hobbyDao.fixHobby(id, hname, htype);
        if (hobby != null) {
            return new ResponseInfo("修改成功", true, hobby);
        }
        return new ResponseInfo("修改失败", false, null);
    }
}
