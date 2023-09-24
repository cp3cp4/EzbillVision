package com.example.lbh.javalogin03.repository;
 import com.example.lbh.javalogin03.domain.User;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 import java.util.ArrayList;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUname(String uname); //通过用户名uname查找用户，注意要按照JPA的格式使用驼峰命名法
    User findByUnameAndPassword(String uname, String password);//通过用户名uname和密码查找用户
    User findByUid(Long uid);

    //find all user by certain room id
    ArrayList<User> findByRoom(int room);

}