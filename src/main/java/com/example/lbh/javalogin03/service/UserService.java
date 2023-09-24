package com.example.lbh.javalogin03.service;

import com.example.lbh.javalogin03.domain.User;

import java.util.Optional;

public interface UserService {
    /**
     * 登录业务逻辑
     * @param uname 账户名
     * @param password 密码
     * @return
     */
    User loginService(String uname, String password);

    Optional<User> getUserService(long uid);

    /**
     * 注册业务逻辑
     * @param user 要注册的User对象，属性中主键uid要为空，若uid不为空可能会覆盖已存在的user
     * @return
     */
    User registService(User user);

    /**
     * 删除业务逻辑
     * @param uname 账户名
     * @param password 密码
     * @return
     */
    User deleteService(String uname, String password);

    /**
     * 更新业务逻辑
     * @param uname 账户名
     * @param password 密码
     * @param updateUname 更新账户名
     * @param updatePassword 更新密码
     * @return
     */
    User updateService(String uname, String password,String updateUname,String updatePassword);

    User getIdService(String uname);

    User getPasswordService(String uname);

    //find Uame by uid
    String findUnameByUid(Long uid);

    //find user by uid
    User findUserbyUid (Long uid);
}