package top.kwseeker.www.ssm_island.service;

import top.kwseeker.www.ssm_island.common.ServerResponse;
import top.kwseeker.www.ssm_island.pojo.User;

public interface IUserService {

    // 用户注册
    ServerResponse<String> userRegister(String username, String password, String introduction, String phone, String email);
    // 用户登录
    ServerResponse<User> userLogin(String username, String password);
    // 用户名校验
    ServerResponse<String> userCheck(String username);
    // 邮箱校验
    int emailCheck(String email);
    // 密码重置
    ServerResponse<String> updatePassword(String username, String password);
    ServerResponse<String> updatePasswordByEmail(String email, String password);
    // 更新用户信息
    ServerResponse<String> updateUserInfo(String username, String introduction, String phone, String email);
}
