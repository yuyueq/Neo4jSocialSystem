package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.UserService;
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
 * 用于返回界面信息
 * </p>
 *
 * @author yuyueq
 * @since 2022-01-23
 */

@Controller
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/")
    public String index(Map<String, Object> paramMap, HttpServletRequest request) {
        return userService.index(paramMap, request);
    }

    /**
     * 登录
     *
     * @param account
     * @param password
     * @param paramMap
     * @param request
     * @return
     */
    @PostMapping("/login")
    public String index(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        Map<String, Object> paramMap,
                        HttpServletRequest request) {
        return userService.index(account, password, paramMap, request);
    }

    /**
     * 获取用户相关信息
     *
     * @param request
     * @param paramMap
     * @return
     */
    @GetMapping("/content")
    public String content(HttpServletRequest request, Map<String, Object> paramMap) {
        return userService.content(request, paramMap);
    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        return userService.logout(request);
    }

    /**
     * 注册
     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     */
    @GetMapping("/user/getuser")
    @ResponseBody
    public ResponseInfo getUser(@RequestParam("account") String account) {
        return userService.getUser(account);
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/user/all")
    @ResponseBody
    public ResponseInfo getAllUser() {
        return userService.getAllUser();
    }

    /**
     * 新增用户
     *
     * @param account
     * @param password
     * @param age
     * @param gender
     * @param email
     * @param address
     * @param nickname
     * @param map
     * @return
     */
    @PostMapping("/user/adduser")
    public String addUser(@RequestParam("account") String account,
                          @RequestParam("password") String password,
                          @RequestParam("age") String age,
                          @RequestParam("gender") String gender,
                          @RequestParam("email") String email,
                          @RequestParam("address") String address,
                          @RequestParam("nickname") String nickname,
                          Map<String, Object> map) {
        return userService.addUser(account, password, age, gender, email, address, nickname, map);
    }

    /**
     * 删除用户
     *
     * @param account
     * @return
     */
    @PostMapping("/user/deleteuser")
    @ResponseBody
    public ResponseInfo deleteUser(@RequestParam("account") String account) {
        return userService.deleteUser(account);
    }

    /**
     * 修改用户信息
     *
     * @param account
     * @param nickname
     * @param age
     * @param email
     * @param address
     * @return
     */
    @PostMapping("/user/fixinfo")
    @ResponseBody
    public ResponseInfo fixInfo(@RequestParam("account") String account, @RequestParam("nickname") String nickname,
                                @RequestParam("age") Integer age, @RequestParam("email") String email,
                                @RequestParam("address") String address) {
        return userService.fixInfo(account, nickname, age, email, address);
    }

    /**
     * 修改密码
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/user/fixpass")
    @ResponseBody
    public ResponseInfo fixPass(@RequestParam("account") String account, @RequestParam("newpass") String password) {
        return userService.fixPass(account, password);
    }

    /**
     * 修改头像
     *
     * @param account
     * @param imgurl
     * @return
     */
    @PostMapping("/user/fiximg")
    @ResponseBody
    public ResponseInfo fixImg(@RequestParam("account") String account, @RequestParam("imgurl") String imgurl) {
        return userService.fixImg(account, imgurl);
    }
}
