package com.sykj.uusmart.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/7/3 0003.
 */
public interface PictureService {
    String uploadPicture(MultipartFile file);
}
