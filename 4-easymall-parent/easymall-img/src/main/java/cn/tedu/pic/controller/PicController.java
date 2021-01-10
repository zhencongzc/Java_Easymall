package cn.tedu.pic.controller;

import cn.tedu.pic.service.PicService;
import common.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PicController {
    @Autowired
    private PicService picService;

    //上传图片
    @RequestMapping("/pic/uploadImg")
    public PicUploadResult picUpload(MultipartFile pic) {
        return picService.picUpload(pic);
    }
}
