package top.kwseeker.www.ssm_island.dao;

import top.kwseeker.www.ssm_island.pojo.User;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}