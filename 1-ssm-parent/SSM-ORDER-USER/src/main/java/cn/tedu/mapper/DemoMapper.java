package cn.tedu.mapper;

import org.apache.ibatis.annotations.Param;

public interface DemoMapper {
    String getNameById(@Param("id") int id);
}
