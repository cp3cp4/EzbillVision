package com.example.lbh.javalogin03.service;

import com.example.lbh.javalogin03.domain.Bill;
import com.example.lbh.javalogin03.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public interface BillService {


    /**
     * insert data into the bill table
     */
    Bill insertService(Bill bill);
    Bill findRecordService(String uname, String date);
    ArrayList<Bill> findAllRecordService(String uname);

    //get Uname by Uid
    String getUnameByUid(Long uid);

    //find all user by certain room id
    ArrayList<User> findAllUnameByRoom(int roomId);


}
