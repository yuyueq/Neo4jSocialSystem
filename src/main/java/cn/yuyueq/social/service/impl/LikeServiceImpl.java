package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.LikeDao;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author wenxin.du
 * @since 2022/3/16
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    LikeDao likeDao;

    /**
     * 添加个人兴趣
     * @param account
     * @param hobbyid
     * @return
     */
    @Override
    public ResponseInfo likeHobby(String account, Long hobbyid) {
        Integer num=likeDao.likeHobby(account,hobbyid);
        return new ResponseInfo(num==2?"添加兴趣成功":"添加兴趣失败",num==2,num);
    }

    /**
     * 删除个人兴趣
     * @param account
     * @param hobbyid
     * @return
     */
    @Override
    public ResponseInfo unlikeHobby(String account, Long hobbyid) {
        Integer num=likeDao.unlikeHobby(account,hobbyid);
        return new ResponseInfo(num>0?"删除兴趣成功":"删除兴趣失败",num>0,num);
    }
}
