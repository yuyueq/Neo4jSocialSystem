package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface HobbyService {

    String getAll(HttpServletRequest request, Map<String, Object> map);

    ResponseInfo addHobby(String hname, String htype);

    ResponseInfo deleteHobby(Long id);

    ResponseInfo search(String hname);

    ResponseInfo fix(Long id, String hname, String htype);
}
