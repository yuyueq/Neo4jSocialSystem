package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;
import org.springframework.web.bind.annotation.RequestParam;

public interface PraiseService {
    ResponseInfo praiseShare(String account,  Integer shareid);
    ResponseInfo unpraiseShare(String account,  Integer shareid);
}
