package cn.yuyueq.social.service.impl;

import cn.yuyueq.social.dao.PraiseDao;
import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.PraiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author liangyihui.net
 * @since 2022/3/16
 */
@Service
public class PraiseServiceImpl implements PraiseService {

    @Autowired
    PraiseDao praiseDao;

    @Override
    public ResponseInfo praiseShare(String account, Integer shareid) {
        Integer number=praiseDao.isPraised(shareid,account);
        if(number>0) {
            Long praise_number=praiseDao.getPraisedNumber(shareid);
            return new ResponseInfo("check", true, praise_number);
        }
        Long relationshipId=praiseDao.praisedIt(account,shareid);
        Long praise_number=praiseDao.getPraisedNumber(shareid);
        return new ResponseInfo(relationshipId!=null?"success":"fail",relationshipId!=null,praise_number);
    }

    @Override
    public ResponseInfo unpraiseShare(String account, Integer shareid) {
        Integer affect_rowes=praiseDao.canclepraised(account,shareid);
        Long praise_number=praiseDao.getPraisedNumber(shareid);
        return new ResponseInfo(affect_rowes>0?"success":"fail",affect_rowes>0,praise_number);
    }
}
