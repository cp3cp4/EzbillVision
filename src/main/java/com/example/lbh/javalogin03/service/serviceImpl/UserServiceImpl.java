package com.example.lbh.javalogin03.service.serviceImpl;

import com.example.lbh.javalogin03.domain.User;
import com.example.lbh.javalogin03.repository.UserDao;
import com.example.lbh.javalogin03.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User loginService(String uname, String password) {
        // 如果账号密码都对则返回登录的用户对象，若有一个错误则返回null
        User user = userDao.findByUnameAndPassword(uname, password);
        // 重要信息置空
//        if(user != null){
//            user.setPassword("");
//        }
        return user;
    }

    @Override
    public Optional<User> getUserService(long uid) {
        return Optional.empty();
    }

    @Override
    public User registService(User user) {
        //当新用户的用户名已存在时
        if(userDao.findByUname(user.getUname())!=null){
            // 无法注册
            return null;
        }else{
            //返回创建好的用户对象(带uid)
            User newUser = userDao.save(user);
//            if(newUser != null){
//                newUser.setPassword("");
//            }
            return newUser;
        }
    }

    @Override
    public User deleteService(String uname, String password) {
        //根据用户名和密码来判断用户是否存在
        User user = userDao.findByUnameAndPassword(uname,password);
        if (user != null){
            userDao.delete(user);
            return user;
        }
        return null;
    }

    @Override
    public User updateService(String uname, String password, String updateUname, String updatePassword) {
        //根据用户名和密码来判断用户是否存在
        User user = userDao.findByUnameAndPassword(uname,password);
        if (user != null){
            if (updateUname !=  null){
                user.setUname(updateUname);
            }
            if (updatePassword != null){
                user.setPassword(updatePassword);
            }
            userDao.save(user);
            return user;
        }
        return null;
    }

    @Override
    public User getIdService(String uname) {
        User user = userDao.findByUname(uname);
        if (user != null){
            return user;
        }
        return null;
    }

    @Override
    public User getPasswordService(String uname) {
        User user = userDao.findByUname(uname);
        if (user != null){
            return user;
        }
        return null;
    }

    @Override
    public User findUserbyUid(Long uid) {
        return userDao.findByUid(uid);
    }

    @Override
    public String findUnameByUid(Long uid) {
        User user = userDao.findByUid(uid);
        if (user != null){
            return user.getUname();
        }
        return null;
    }

}
