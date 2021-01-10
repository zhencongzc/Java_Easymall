package cn.tedu.mapper;
import cn.tedu.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
public interface UserMapper {
    @Select("SELECT *\n" +
            "FROM tb_user\n" +
            "WHERE username = #{username}")
    public User selectUserByUsername(@Param("username") String username);
}