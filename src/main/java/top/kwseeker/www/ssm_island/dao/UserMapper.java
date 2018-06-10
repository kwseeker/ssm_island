package top.kwseeker.www.ssm_island.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import top.kwseeker.www.ssm_island.pojo.User;

@Component
public interface UserMapper {

    int insert(User record);

    // 动态插入（可用于新用户注册）
    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);

    // 使用用户名和密码查询（登录查询）
    User selectLogin(@Param("username") String username, @Param("password") String password);

    // 使用用户名查询 （注册前用户名有效性检查）
    int selectCheckName(@Param("username") String username);    // TODO: 返回值

    int selectCheckEmail(String email);

    // 使用手机号查询
//    int selectCheckPhone(@Param("phone") String phone);

    // 更新用户密码 （密码重置、更新个人信息等）
    int updateUserInfo(@Param("username") String username,
                       @Param("password") String password,
                       @Param("introduction") String introduction,
                       @Param("phone") String phone,
                       @Param("email") String email);
    String selectUsernameByEmail(String email);
//    int updatePasswordByEmail(String email, String password);
}