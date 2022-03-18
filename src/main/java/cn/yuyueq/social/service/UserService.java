package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    String index(Map<String, Object> paramMap, HttpServletRequest request);

    String index(String account, String password, Map<String, Object> paramMap, HttpServletRequest request);

    String content(HttpServletRequest request, Map<String, Object> paramMap);

    String logout(HttpServletRequest request);

    ResponseInfo getUser(String account);

    ResponseInfo getAllUser();

    String addUser(String account, String password, String age, String gender, String email, String address, String nickname, Map<String, Object> map);

    ResponseInfo deleteUser(String account);

    ResponseInfo fixInfo(String account, String nickname,
                         Integer age, String email,
                         String address);

    ResponseInfo fixPass(String account, String password);

    ResponseInfo fixImg(String account, String imgurl);
}
