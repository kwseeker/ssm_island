package top.kwseeker.www.ssm_island.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.kwseeker.www.ssm_island.common.ServerResponse;
import top.kwseeker.www.ssm_island.dao.UserMapper;
import top.kwseeker.www.ssm_island.pojo.User;
import top.kwseeker.www.ssm_island.service.IUserService;
import top.kwseeker.www.ssm_island.util.MD5Util;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl implements IUserService {

    private final static String TAG = "<DevDbg> ";

    @Autowired
    // @Qualifier("userMapper")
    private UserMapper userMapper;

    /* @PostConstruct和@PreDestroy这两个作用于Servlet生命周期的注解，实现Bean初始化之前和销毁之前的自定义操作 */
    @PostConstruct
    public void UserServicePreInit() {
        // ...
        System.out.println(TAG + "调用UserServicePreInit()方法");
    }

    // 用户名校验
    @Override
    public ServerResponse<String> userCheck(String username) {
        int isUserExist = userMapper.selectCheckName(username);
        if(isUserExist != 0){
            return ServerResponse.createByErrorMessage("用户名已注册");
        }
        return ServerResponse.createBySuccessMessage("用户名可用");
    }

    // 邮箱校验
    public int emailCheck(String email) {
        return userMapper.selectCheckEmail(email);
    }

    // 用户注册
    @Override
    public ServerResponse<String> userRegister(String username, String password, String introduction, String phone, String email){
        int isUserExist = userMapper.selectCheckName(username);
        if(isUserExist != 0){
            return ServerResponse.createByErrorMessage("用户名已注册");
        }

        String md5Password = MD5Util.MD5AddSaltEncodeUTF8(username, password);
        User user = new User(username, md5Password, introduction, phone, email);
        int resultCount = userMapper.insertSelective(user);
        if(resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    // 用户登录
    @Override
    public ServerResponse<User> userLogin(String username, String password){
        int hasUsername = userMapper.selectCheckName(username);
        if(hasUsername == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        // 使用MD5加密password
        String md5Password = MD5Util.MD5AddSaltEncodeUTF8(username, password);
        User user = userMapper.selectLogin(username, md5Password);
        if(user == null) {
            return ServerResponse.createByErrorMessage("密码不正确");
        }
        user.setPassword("******");
        return ServerResponse.createBySuccessMsgAndData("登录成功", user);
    }

    // 密码重置
    @Override
    public ServerResponse<String> updatePassword(String username, String password){
        String md5Password = MD5Util.MD5AddSaltEncodeUTF8(username, password);
        System.out.println("updatePassword() username=" + username + " password=" + password);
        int resultCount = userMapper.updateUserInfo(username, md5Password, null, null, null);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("密码更新失败");
        }
        return ServerResponse.createBySuccessMessage("密码更新成功");
    }

    @Override
    public ServerResponse<String> updatePasswordByEmail(String email, String password) {
        // TODO: 用户名、手机、邮箱在数据库中都必须唯一
        // 通过email查询对应的用户名，然后通过用户名更新用户密码
        String username = userMapper.selectUsernameByEmail(email);
        if(username == null) {
            return ServerResponse.createBySuccessMessage("获取用户名失败");
        }
        return updatePassword(username, password);
    }

    @Override
    public ServerResponse<String> updateUserInfo(String username, String introduction, String phone, String email) {
        int resultCount = userMapper.updateUserInfo(username, null, introduction, phone, email);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户信息更新失败");
        }
        return ServerResponse.createBySuccessMessage("用户信息更新成功");
    }

}
