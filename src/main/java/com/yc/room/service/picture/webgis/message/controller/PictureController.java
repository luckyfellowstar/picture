package com.yc.room.service.picture.webgis.message.controller;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;


/**
 * picture接口
 * xing.yang
 */
@RestController
@RequestMapping("/v1/picture")
public class PictureController {

    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);

    String accessKey = "your access key";
    String secretKey = "your secret key";
    String bucket = "your bucket name";

    //默认不指定key的情况下，以文件内容的hash值作为文件名
    String key = null;

    /**
     * 图片文件上传
     *
     * @return
     */
    @PostMapping(value = "/upload")
    public ResponseEntity<Void> uploadPicture() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // 如果服务器出错，返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }


    /**
     * 图片文件下载
     *
     * @return
     */
    @PostMapping(value = "/download")
    public ResponseEntity<Void> downloadPicture() {
        try {
            String fileName = "七牛/云存储/qiniu.jpg";
            String domainOfBucket = "http://devtools.qiniu.com";
            String encodedFileName = URLEncoder.encode(fileName, "utf-8");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);

            //私有空间的处理
            Auth auth = Auth.create(accessKey, secretKey);
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            System.out.println(finalUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // 如果服务器出错，返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);


    }

}
