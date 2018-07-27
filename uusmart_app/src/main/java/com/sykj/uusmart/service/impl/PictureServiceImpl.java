package com.sykj.uusmart.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional( propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT, rollbackFor = CustomRunTimeException.class)
public class PictureServiceImpl implements PictureService {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    @Override
    public String uploadPicture(MultipartFile file){
        //截取后缀
        try {
//            String originalFilename = file.getOriginalFilename();
//            System.out.println(originalFilename);
//            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//            System.out.println(extName);
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
//            System.out.println(storePath);
            String fileUrl = IMAGE_SERVER_URL + storePath.getFullPath();
            return fileUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}