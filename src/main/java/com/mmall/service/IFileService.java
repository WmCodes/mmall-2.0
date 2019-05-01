package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangmeng
 * @date 2019/4/22
 * @desciption
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
