package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.FollowDao;
import cn.yuyueq.social.dao.HobbyDao;
import cn.yuyueq.social.dao.RecommendDao;
import cn.yuyueq.social.dao.UserDao;
import cn.yuyueq.social.domain.node.Hobby;
import cn.yuyueq.social.domain.node.User;
import cn.yuyueq.social.service.RecommendUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *
 * </p>
 *
 * @author wenxin.du
 * @since 2022/3/16
 */
@Slf4j
@Service
public class RecommendUserServiceImpl implements RecommendUserService {

    @Autowired
    UserDao userDao;
    @Autowired
    FollowDao followDao;
    @Autowired
    RecommendDao recommendDao;
    @Autowired
    HobbyDao hobbyDao;
    @Autowired
    Neo4jClient neo4jClient;


    public final static double SIMiILAR = 0.7;


    public static boolean contains(ArrayList<User> arr, User target) {
        for (User user : arr) {
            if (user.getId().equals(target.getId()))
                return true;
        }
        return false;
    }

    @Override
    public String recommendFriendsByFriend(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        ArrayList<User> users = (ArrayList<User>) recommendDao.byFriend(user.getAccount());
        ArrayList<User> myfollowing = (ArrayList<User>) followDao.getMyFollowing(user.getAccount());
        HashSet<User> res = new HashSet<>();
        for (User value : users) {
            if (value.getAccount().equals(user.getAccount()))
                continue;
            if (contains(myfollowing, value))
                continue;
            res.add(value);
        }
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);


        map.put("recommends", res);

        map.put("user", user);
        map.put("index", "朋友推荐");
        map.put("title", "可能认识的人");
        return "userlist";
    }

    @Override
    public String recommendFriendsByShare(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        ArrayList<User> users = (ArrayList<User>) recommendDao.byshare(user.getAccount());
        ArrayList<User> myfollowing = (ArrayList<User>) followDao.getMyFollowing(user.getAccount());
        HashSet<User> res = new HashSet<>();
        for (User value : users) {
            if (value.getAccount().equals(user.getAccount()))
                continue;
            if (contains(myfollowing, value)) continue;
            res.add(value);
        }
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("recommends", res);
        map.put("user", user);
        map.put("index", "朋友推荐");
        map.put("title", "最常互动的人");
        return "userlist";
    }

    @Override
    public String recommendFriendsByHobby(HttpServletRequest request, Map<String, Object> map) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "login";
        }
        HashSet<User> res = new HashSet<>();
        List<Hobby> myHobby = hobbyDao.getMyHobby(user.getAccount());
        List<Long> myHobbyList = myHobby.stream().map(Hobby::getId).collect(Collectors.toList());


        String queryWrapper = "match ((user:User)-[:Like]->(hobby:Hobby)) where ID(hobby) IN $hobbys and user.account <> $account  return distinct user.account ";
        Collection<String> accounts = neo4jClient.query(queryWrapper)
                .bind(myHobbyList).to("hobbys")
                .bind(user.getAccount()).to("account")
                .fetchAs(String.class)
                .all();
        List<User> myFollowing = followDao.getMyFollowing(user.getAccount());

        for (String account : accounts) {
            String wrapper = "MATCH (p1:User {account:$account1})-[:Like]->(hobby1) WITH p1, collect(id(hobby1)) AS p1Hobby MATCH (p2:User {account:$account2})-[:Like]->(cuisine2) WITH p1, p1Hobby, p2, collect(id(cuisine2)) AS p2Hobby RETURN p1.account AS from, p2.account AS to, algo.similarity.jaccard(p1Hobby, p2Hobby) AS similarity ";
            Collection<Map<String, Object>> sames = neo4jClient.query(wrapper).bind(user.getAccount()).to("account1")
                    .bind(account).to("account2").fetch().all();
            List<Map<String, Object>> collect = sames.stream().collect(Collectors.toList());
            collect.stream().map(m -> {
                double similarity = (double) m.get("similarity");
                User sameUser = userDao.getUserByAccount((String) m.get("to"));
                List<Hobby> userHobby = hobbyDao.getMyHobby((String) m.get("to"));
                StringBuilder stringBuilder = new StringBuilder();
                userHobby.stream().map(hobby->{
                    stringBuilder.append(hobby.getHname()+"\t");
                    //log.info("______"+stringBuilder);
                    return stringBuilder;
                }).collect(Collectors.toList());

                sameUser.setHobbyList(stringBuilder.toString());

                if (!myFollowing.contains(sameUser)) {
                    if (similarity >= SIMiILAR) {
                        res.add(sameUser);
                    }
                }
                return res;
            }).collect(Collectors.toList());
            //log.info("_____________________" + res);
        }
        //HashSet<User> users = SimilarInterests(user.getAccount());
        Integer following_num = followDao.getMyFollowing(user.getAccount()).size();
        Integer follower_num = followDao.getPeopleWhoFollowMe(user.getAccount()).size();
        map.put("myfollowing", following_num);
        map.put("follower", follower_num);
        map.put("recommends", res);
        map.put("user", user);
        map.put("index", "朋友推荐");
        map.put("title", "趣味相投的人");
        map.put("reverse", false);
        return "userlist";
    }

    public HashSet<User> SimilarInterests(String myAccount) {
        List<Hobby> myHobby = hobbyDao.getMyHobby(myAccount);
        List<Long> myHobbyList = myHobby.stream().map(Hobby::getId).collect(Collectors.toList());
        String queryWrapper = "match ((user:User)-[:Like]->(hobby:Hobby)) where ID(hobby) IN $hobbys and user.account <> $account  return distinct user.account ";
        Collection<String> accounts = neo4jClient.query(queryWrapper)
                .bind(myHobbyList).to("hobbys")
                .bind(myAccount).to("account")
                .fetchAs(String.class)
                .all();

        double rate;
        HashSet<User> res = new HashSet<>();
        for (String account : accounts) {
            //获取当前用户兴趣
            List<Hobby> hobbies = hobbyDao.getMyHobby(account);
            List<Long> hobbyList = hobbies.stream().map(Hobby::getId).collect(Collectors.toList());
            //求交集
            List<Long> intersection = hobbyList.stream().filter(myHobbyList::contains).collect(Collectors.toList());
            double intersectionSize = intersection.size();
            //求并集
            hobbyList.addAll(myHobbyList);
            List<Long> union = hobbyList.stream().distinct().collect(Collectors.toList());
            double unionSize = union.size();
            rate = intersectionSize / unionSize;
            if (rate >= SIMiILAR) {
                res.add(userDao.getUserByAccount(account));
            }
        }
        return res;

    }
}
