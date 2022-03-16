package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.HobbyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenxin.du
 * @since 2022-01-23
 */

@Controller
public class HobbyController {

    @Resource
    private HobbyService hobbyService;

    /**
     * 获取兴趣部落
     *
     * @param request
     * @param map
     * @return
     */
    @GetMapping("/hobby/getall")
    public String getAll(HttpServletRequest request, Map<String, Object> map) {
        return hobbyService.getAll(request, map);
    }

    /**
     * 添加兴趣
     *
     * @param hname
     * @param htype
     * @return
     */
    @PostMapping("/hobby/addhobby")
    @ResponseBody
    public ResponseInfo addHobby(@RequestParam("hname") String hname, @RequestParam("htype") String htype) {
        return hobbyService.addHobby(hname, htype);
    }

    /**
     * 删除兴趣
     *
     * @param id
     * @return
     */
    @PostMapping("/hobby/delete")
    @ResponseBody
    public ResponseInfo deleteHobby(@RequestParam("id") Long id) {
        return hobbyService.deleteHobby(id);
    }

    /**
     * 搜索兴趣
     *
     * @param hname
     * @return
     */
    @GetMapping("/hobby/search")
    @ResponseBody
    public ResponseInfo search(@RequestParam("hname") String hname) {
        return hobbyService.search(hname);
    }

    /**
     * 修改兴趣
     *
     * @param id
     * @param hname
     * @param htype
     * @return
     */
    @PostMapping("/hobby/fix")
    @ResponseBody
    public ResponseInfo fix(@RequestParam("id") Long id, @RequestParam("hname") String hname,
                            @RequestParam("htype") String htype) {
        return hobbyService.fix(id, hname, htype);
    }
}