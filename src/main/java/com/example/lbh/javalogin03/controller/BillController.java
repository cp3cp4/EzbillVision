package com.example.lbh.javalogin03.controller;


import com.baidu.aip.face.AipFace;
import com.example.lbh.javalogin03.domain.Bill;
import com.example.lbh.javalogin03.domain.User;
import com.example.lbh.javalogin03.repository.BillDao;
import com.example.lbh.javalogin03.repository.UserDao;
import com.example.lbh.javalogin03.service.BillService;
import com.example.lbh.javalogin03.service.UserService;
import com.example.lbh.javalogin03.utils.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;

    @Resource
    private UserService userService;

    @Resource
    private BillDao billDao;

    @Resource
    private UserDao userDao;

    @Autowired
    private AipFace aipFace;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Result<Bill> insertController(@RequestBody Bill bill){
        Bill newBill = billService.insertService(bill);
        return Result.success(newBill,"insert success！");
    }

    @RequestMapping(value = "/searchPast", method = RequestMethod.GET)
    public Result<ArrayList<Bill>> searchPastController(@RequestParam Long uid){
        //get uname by uid
        String uname = billService.getUnameByUid(uid);
        int days = 7;
        String[] pastDays = generatePastDays(days);
        ArrayList<Bill> output= new ArrayList<Bill>();
        for (int i = 0; i < days; i++) {
            Bill newBill = billService.findRecordService(uname,pastDays[i]);
            if(newBill != null){
                output.add(newBill);
            }
        }
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i).toString());
        }
        return Result.success(output,"enjoy the data");
    }

    @RequestMapping(value = "/searchTenantAllBill", method = RequestMethod.GET)
    public Result<ArrayList<Bill>> searchTenantAllBillController(@RequestParam Long uid){
        //get uname by uid
        String uname = billService.getUnameByUid(uid);
        //get all bill by uname
        ArrayList<Bill> output = billService.findAllRecordService(uname);
        for (int i = 0; i < output.size(); i++) {
            System.out.println(output.get(i).toString());
        }
        return Result.success(output,"enjoy the data");
    }

    @RequestMapping(value = "/searchLandlordAllBill", method = RequestMethod.GET)
    public Result<ArrayList<Bill>> searchAllBillController(@RequestParam Long uid){
        //get user by uid
        User user = userService.findUserbyUid(uid);
        //get room id by user
        int roomId = user.getRoom();
        //get uname by user
        String uname = user.getUname();
        //find all tenants' uname by certain room id
        ArrayList<User> userList = billService.findAllUnameByRoom(roomId);
        //get all uname in the userlist
        ArrayList<String> unameList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            unameList.add(userList.get(i).getUname());
        }
        System.out.println(unameList.toString());

        //put all the bill of those user in a arraylist
        ArrayList<Bill> billList = new ArrayList<>();
        for (int i = 0; i < unameList.size(); i++) {
            ArrayList<Bill> indexBill = billService.findAllRecordService(unameList.get(i));
            billList.addAll(indexBill);
        }

        for (int i = 0; i < billList.size(); i++) {
            System.out.println(billList.get(i).toString());
        }
        return Result.success(billList,"enjoy the data");
    }

    @RequestMapping(value = "/searchPieChartData", method = RequestMethod.GET)
    public Result<HashMap<String,Integer>> searchPieChartController(@RequestParam Long uid){
        //get user by uid
        User user = userService.findUserbyUid(uid);
        //get room id by user
        int roomId = user.getRoom();
        //get uname by user
        String uname = user.getUname();
        //find all tenants' uname by certain room id
        ArrayList<User> userList = billService.findAllUnameByRoom(roomId);
        //get all uname in the userlist
        ArrayList<String> unameList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            unameList.add(userList.get(i).getUname());
        }
        System.out.println(unameList.toString());

        //put all the bill of those user in a arraylist
        ArrayList<Bill> billList = new ArrayList<>();
        for (int i = 0; i < unameList.size(); i++) {
            ArrayList<Bill> indexBill = billService.findAllRecordService(unameList.get(i));
            billList.addAll(indexBill);
        }

        for (int i = 0; i < billList.size(); i++) {
            System.out.println(billList.get(i).toString());
        }

        //create a hashmap,  key: uname value: all the expense
        HashMap<String,Integer> expenseMap = new HashMap<>();
        for (int i = 0; i < unameList.size(); i++) {
            String name = unameList.get(i);
            int count = 0;
            for (int j = 0; j < billList.size(); j++) {
                //calculate every bill belong to the certain uname
                if (billList.get(j).getUname().equals(name)){
                    count += billList.get(j).getExpense();
                }
            }
            expenseMap.put(name,count);
        }
        System.out.println(expenseMap.toString());

        //create a hashmap,  key:uname value:percentage
        //calculate the total expense
        int total = 0;
        for (Integer value : expenseMap.values()) {
            total += value;
        }

        HashMap<String,Integer> percentageMap = new HashMap<>();
        //original last
        int last = 100;
        //original omit
        int omitPercent = 0;
        for (String name : expenseMap.keySet()) {
//            int a = expenseMap.get(name);
//            double b = (double)a / (double)total;
//            double c = b * 100;
//            int d = (int)c;
            last -= omitPercent;
            double percent = (double)expenseMap.get(name) / (double)total * 100;
            omitPercent = (int)percent;
            if (name.equals(unameList.get(unameList.size() - 1))){
                percentageMap.put(name,last);
            }
            else {
                percentageMap.put(name,omitPercent);
            }
        }
        System.out.println(percentageMap.toString());

        //delate the null value
        HashMap<String,Integer> finalMap = new HashMap<>();
        for (String s : percentageMap.keySet()) {
            if (percentageMap.get(s) != 0){
                finalMap.put(s,percentageMap.get(s));
            }
        }
        System.out.println(finalMap);
        return Result.success(finalMap,"enjoy the data");
    }





    public String[] generatePastDays(int numberOfDays) {
        String[] output = new String[numberOfDays];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < numberOfDays; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            output[i] = formatter.format(cal.getTime());
        }
        return output;
    }
}