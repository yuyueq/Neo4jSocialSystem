package cn.yuyueq.social.service;

import cn.yuyueq.social.domain.util.ResponseInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseInfo upload(MultipartFile file);

    String storeFile(MultipartFile file) throws Exception;

    Resource loadFileAsResource(String fileName) throws Exception;
}
