package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;

public interface LikeService {

    ResponseInfo likeHobby(String account, Long hobbyid);

    ResponseInfo unlikeHobby(String account, Long hobbyid);
}
