package cn.tedu.seckill.mapper;

import common.pojo.Seckill;
import common.pojo.Success;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SecMapper {
    List<Seckill> selectSeckills();

    Seckill selectSeckillById(Long seckillId);

    int decreNumber(@Param("now") Date nowTime, @Param("seckillId") Long seckillId);

    void insertSuccess(Success suc);
}
