package cn.tedu.encoder;

import cn.tedu.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    //加密计算

    /**
     * rawPassword 加密加计算之前的明文
     * charSequence类型，使得很多加密计算逻辑变的简单专业
     * 返回值就是加密文本
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String encoder = rawPassword.toString();
        return MD5Util.md5(encoder);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encode = encode(rawPassword);
        return encode.equals(encodedPassword);
    }
}
