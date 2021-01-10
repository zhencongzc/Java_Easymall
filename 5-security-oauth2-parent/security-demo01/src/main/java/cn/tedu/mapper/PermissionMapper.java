package cn.tedu.mapper;
import cn.tedu.domain.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
public interface PermissionMapper {
    //通过userid查询对应权限list
    @Select("SELECT\n" +
            "  p.*\n" +
            "FROM tb_permission AS p\n" +
            "  LEFT JOIN tb_user AS u\n" +
            "    ON p.user_id = u.id\n" +
            "WHERE u.id = #{userId}")
    List<Permission> selectPermissionByUserId(@Param("userId") Long userId);
}
