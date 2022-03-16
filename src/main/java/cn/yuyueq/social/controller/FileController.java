package cn.yuyueq.social.controller;

import cn.yuyueq.social.domain.util.ResponseInfo;
import cn.yuyueq.social.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    FileServiceImpl fileServiceImpl;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseInfo upload(@RequestParam("file") MultipartFile file){
        try{
            return new ResponseInfo("上传成功",true, fileServiceImpl.storeFile(file));
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseInfo("上传失败",false,null);
    }
}
