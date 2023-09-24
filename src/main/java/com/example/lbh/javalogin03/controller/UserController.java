package com.example.lbh.javalogin03.controller;


import com.baidu.aip.face.AipFace;
import com.example.lbh.javalogin03.domain.Bill;
import com.example.lbh.javalogin03.domain.User;
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
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private BillService billService;

    final private String groupListId = "user_03";

    @Resource
    private UserDao userDao;

    @Autowired
    private AipFace aipFace;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<User> loginController(@RequestParam String uname, @RequestParam String password){
        User user = userService.loginService(uname, password);
        if(user!=null){
            System.out.println("already login in");
            return Result.success(user,"登录成功！");
        }else{
            return Result.error("-1","账号或密码错误！");
        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public void readImageController(){
//        Session firewallSession = jsch.getSession("firewall_username", "firewall", 22);
//// ...
//        firewallSession.connect();
//
//        int forwardedPort = 2222; // any port number which is not in use on the local machine
//        firewallSession.setPortForwardingL(forwardedPort, "server", 22);
//
//        Session session = jsch.getSession("server_usernam", "localhost", forwardedPort);
//// ...
//        session.connect();
//
//        Channel channel = session.openChannel("sftp");
//        channel.connect();
//        ChannelSftp channelSftp = (ChannelSftp)channel;
//
//        channelSftp.get("/remote/path/on/server/file.txt", "C:\\local\\path\\file.txt");
    }

    @RequestMapping(value = "/postImage", method = RequestMethod.POST)
    public void getImage(@RequestParam String packet) throws IOException {
        byte[] image = Base64.getDecoder().decode(packet);
        FileOutputStream fos = new FileOutputStream("image.jpg");
        fos.write(image);
//        System.out.println();
//        System.out.println(packet);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public void testSample(@RequestBody String packet) throws IOException {
        System.out.println(packet);
//        System.out.println();
//        System.out.println(packet);
    }

    @RequestMapping(value = "/getUid", method = RequestMethod.GET)
    public Result<Long> getIdController(@RequestParam String uname){
        User user = userService.getIdService(uname);
        if (user != null){
            return Result.success(user.getUid(),"find it!");
        }
        else {
            return Result.error("-1","failed");
        }
    }

    @RequestMapping(value = "/getPassword", method = RequestMethod.GET)
    public Result<String> getPasswordController(@RequestParam String uname){
        User user = userService.getPasswordService(uname);
        if (user != null){
            return Result.success(user.getPassword(),"find it!");
        }
        else {
            return Result.error("-1","failed");
        }
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Result<User> getUserController(@RequestParam Long uid){
        User user = userService.findUserbyUid(uid);
        if (user != null){
            return Result.success(user,"find it!");
        }
        else {
            return Result.error("-1","failed");
        }
    }

    @RequestMapping(value = "/testApi", method = RequestMethod.GET)
    public void testController(){
        JSONObject res = facesetAddUserGroup(aipFace);
        System.out.println(res.toString());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<User> registController(@RequestBody User newUser){
//        System.out.println(newUser.toString());
        //upload user information
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject result = aipFace.search(newUser.getSourceImage(), "BASE64", groupListId, options);
        if(result.get("result").toString().equals("null")){
            return Result.error("-1",result.getString("error_msg"));
        }
        JSONObject tempResult = (JSONObject) result.getJSONObject("result").getJSONArray("user_list").get(0);
        if (!tempResult.toString().equals("null") && tempResult.getDouble("score") > 70.0) {
            System.out.println(tempResult.getDouble("score"));
            System.out.println("error!");
            return Result.error("-1","face already exist！");
        }
        User user = userService.registService(newUser);
//        System.out.println(newUser.toString());
        // 向百度云人脸库插入一张人脸
        JSONObject res = faceSetAddUser(aipFace, newUser.getSourceImage(), newUser.getUname());
        if(user!=null){
            if (res.get("result").toString().equals("null")){
                String error_msg = (String)res.get("error_msg");
                if (error_msg.equals("face already exist")){
                    return Result.error("-1","face already exist！");
                }else {
                    return Result.error("-1","pic has no face！");
                }
            }
            else {
                System.out.println("successful sign up");
                return Result.success(user,"sign up successful！");
            }

        }else{
            return Result.error("-1","username already exist！");
        }
    }

    @RequestMapping(value = "/recordExpense", method = RequestMethod.POST)
    public Result<Bill> recordExpenseController(@RequestParam String image){
        //verify user's face
        JSONObject result = verifyUser(image , aipFace);
        //receive the score
        System.out.println(result.get("score"));
        if ( (Double)result.get("score") < 70) {
            System.out.println((Double) result.get("score"));
            System.out.println("error!");
            return Result.error("-1", "no match！");
        }

        String uname = (String)result.get("user_id");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = formatter.format(getCurrentDate());
        Bill bill = billService.findRecordService(uname,currentDate);
        if(bill == null){
            System.out.println("hello");
            Bill insertBill = new Bill(uname,currentDate,1);
            Bill output = billService.insertService(insertBill);
            return Result.success(output,"inserted new record");
        }else{
            bill.setExpense(bill.getExpense() + 1);
            Bill output = billService.insertService(bill);
            return Result.success(output,"updated record");
        }
    }

    @RequestMapping(value = "/updateImage", method = RequestMethod.POST)
    public void updateController(@RequestParam String uname,@RequestParam String packet){
        //upload user information
        User user = userService.getIdService(uname);
        user.setSourceImage(packet);
        userDao.save(user);
    }

    @RequestMapping(value = "/compare", method = RequestMethod.POST)
    public void compareController(@RequestParam String image){
        verifyUser(image,aipFace);
//        System.out.println(num);
    }

    @DeleteMapping("/delete")
    public Result<User>deleteController(@RequestParam String uname,@RequestParam String password){
        User user =  userService.deleteService(uname,password);
        if(user!=null){
            return Result.success(user,"删除成功！");
        }else{
            return Result.error("-1","删除失败");
        }
    }

    @GetMapping("/update")
    public Result<User>updateController(@RequestParam String uname,@RequestParam String password,@RequestParam String uUname,@RequestParam(required = false) String uPassword ){
        User user = userService.updateService(uname,password,uUname,uPassword);
        if (user != null){
            return Result.success(user,"更新成功");
        }else{
            return Result.success(user,"更新失败");
        }

    }



    /**
     * @Title: facesetAddUser
     * @Description: 该方法的主要作用：人脸注册,给人脸库中注册一个人脸
     * @param  @param client 设定文件
     * @return  返回类型：void
     * @throws
     */
    public JSONObject faceSetAddUser(AipFace client, String faceBase, String username) {
        // 参数为数据库中注册的人脸
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        JSONObject res = client.addUser(faceBase, "BASE64", groupListId, username, options);
        System.out.println(res.toString(2));
//        String error_msg = (String)res.get("error_msg");
        return res;
    }

    /**
     * @Title: facesetAddUserGroup
     * @Description: 该方法的主要作用：注册人脸group
     * @param  @param client 设定文件
     * @return  返回类型：void
     * @throws
     */
    public JSONObject facesetAddUserGroup(AipFace client) {
        // 参数为数据库中注册的人脸
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        JSONObject res = client.groupAdd(groupListId, options);
        System.out.println(res.toString(2));
//        String error_msg = (String)res.get("error_msg");
        return res;
    }

    /**
     * 人脸比对
     * @param imgBash64 照片转bash64格式
     * @return
     */
    public JSONObject verifyUser(String imgBash64,AipFace client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        JSONObject res = client.search(imgBash64, "BASE64", groupListId, options);
        if(res.get("result").toString().equals("null")){
            return null;
        }
        JSONObject user = (JSONObject) res.getJSONObject("result").getJSONArray("user_list").get(0);
        System.out.println(user.toString());
//        Double score = (Double) ;
        return user;
    }

    public Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
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