package cn.tedu.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int selectPointsByUserId(@Param("userId") String userId);
}
