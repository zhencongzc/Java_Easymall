package cn.tedu.pic.service.impl;

import cn.tedu.pic.service.PicService;
import common.utils.UploadUtil;
import common.vo.PicUploadResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PicServiceImpl implements PicService {
    @Override
    public PicUploadResult picUpload(MultipartFile pic) {
        /*
            1.校验合法性,通过后缀校验
            2.存储本地路径数据 d:/img/upload/2/e/r/f/4/f/e/f/32k23kdk23.jpg
            3.根据存储,生成url
            存储路径前缀 d:/img/ +/upload/2/e/r/f/4/f/e/f/32k23kdk23.jpg
            url路径前缀 http://image.jt.com/ +/upload/2/e/r/f/4/f/e/f/32k23kdk23.jpg
         */
        PicUploadResult result = new PicUploadResult();
        String oName = pic.getOriginalFilename();
        String suffixName = oName.substring(oName.lastIndexOf("."));
        if (suffixName == null || !suffixName.matches("^.(png|jpg|jpeg|gif)$")) {
            result.setError(1);
            return result;
        }

        String path = "/" + UploadUtil.getUploadPath(oName, "easymall") + "/";
        String dir = "d:/img" + path;
        File _dir = new File(dir);
        if (!_dir.exists()) {
            _dir.mkdirs();
        }
        String nName = UUID.randomUUID().toString() + suffixName;
        try {
            pic.transferTo(new File(dir + nName));
        } catch (IOException e) {
            e.printStackTrace();
            result.setError(1);
            return result;
        }

        String url = "http://image.jt.com/" + path + nName;
        result.setUrl(url);
        return result;
    }
}
