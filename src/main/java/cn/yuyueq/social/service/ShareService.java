package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface ShareService {
    ResponseInfo addShare(String account, String publisher, String publisherimg, String title,
                          String content, String related_hobby, Long hobbyid, String imgurl, String address);

    ResponseInfo deleteShare(String account, Long shareid);

    ResponseInfo getShareByAccount(String account);

    String publishShare(HttpServletRequest request, Map<String, Object> map);

    String getShareByFriend(HttpServletRequest request, Map<String, Object> map);

    String getShareByHobby(HttpServletRequest request, Map<String, Object> map);

}
