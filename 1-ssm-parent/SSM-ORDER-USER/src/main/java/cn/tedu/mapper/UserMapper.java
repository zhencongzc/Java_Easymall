package cn.tedu.mapper;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int selectPointsByUserId(@Param("userId") String userId);

    void updatePointByUserId(@Param("userId") String userId, @Param("points") int points);
}
