package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenxin.du
 * @since 2022-01-23
 */

@Controller
public class FileController {

    @Resource
    private FileService fileService;


    @PostMapping("/upload")
    @ResponseBody
    public ResponseInfo upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }
}
