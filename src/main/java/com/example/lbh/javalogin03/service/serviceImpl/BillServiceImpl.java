package com.example.lbh.javalogin03.service.serviceImpl;

import com.example.lbh.javalogin03.domain.Bill;
import com.example.lbh.javalogin03.domain.User;
import com.example.lbh.javalogin03.repository.BillDao;
import com.example.lbh.javalogin03.repository.UserDao;
import com.example.lbh.javalogin03.service.BillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.example.lbh.javalogin03.service.UserService;

@Service
public class BillServiceImpl implements BillService {
    @Resource
    private BillDao billDao;
    @Resource
    private UserDao userDao;


    @Override
    public Bill insertService(Bill bill) {
        Bill newBill = billDao.save(bill);
        return newBill;
    }

    @Override
    public Bill findRecordService(String uname, String date) {
        Bill newBill = billDao.findByUnameAndTime(uname, date);
        return newBill;
    }

    @Override
    public ArrayList<Bill> findAllRecordService(String uname) {
        ArrayList<Bill> billList = billDao.findByUname(uname);
        return billList;
    }

    @Override
    public String getUnameByUid(Long uid) {
        return userDao.findByUid(uid).getUname();
    }

    @Override
    public ArrayList<User> findAllUnameByRoom(int roomId) {
        return userDao.findByRoom(roomId);
    }
}
