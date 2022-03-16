package cn.yuyueq.social.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String storeFile(MultipartFile file) throws Exception;

    Resource loadFileAsResource(String fileName) throws Exception;
}
