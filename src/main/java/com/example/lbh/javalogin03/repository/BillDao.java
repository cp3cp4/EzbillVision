package com.example.lbh.javalogin03.repository;
import com.example.lbh.javalogin03.domain.Bill;
import com.example.lbh.javalogin03.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;

@Repository
public interface BillDao extends JpaRepository<Bill, Long> {
//    User findByUname(String uname); //通过用户名uname查找用户，注意要按照JPA的格式使用驼峰命名法
    Bill findByUnameAndTime(String uname, String date);//通过用户名uname和密码查找用户

    ArrayList<Bill> findByUname(String uname);


}