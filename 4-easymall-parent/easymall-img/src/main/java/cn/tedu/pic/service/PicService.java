package cn.tedu.pic.service;

import common.vo.PicUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface PicService {
    PicUploadResult picUpload(MultipartFile pic);
}
