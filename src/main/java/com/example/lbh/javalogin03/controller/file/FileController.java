package com.example.lbh.javalogin03.controller.file;

import com.example.lbh.javalogin03.repository.BillDao;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("file")
public class FileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;

    @Value("${file.download.url}")
    private String downloadFilePath;

    @Resource
    private BillService billService;

    @Resource
    private BillDao billDao;

    //landlord can implement upload function
    @RequestMapping("/upload")
    public String httpUpload(@RequestParam("files") MultipartFile files[],@RequestParam("uid") Long uid){
        JSONObject object = new JSONObject();
        for(int i=0;i<files.length;i++){
            //get uname by uid
            String uname = billService.getUnameByUid(uid);
            /*String fileName = files[i].getOriginalFilename();  // 文件名*/
            File dest = new File(uploadFilePath +'/'+ uname);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                object.put("success",2);
                object.put("result","Something wrong, please try again");
                return object.toString();
            }
        }
        object.put("success",1);
        object.put("result","successfully uploaded");
        return object.toString();
    }

    //tenant can download their own bill
    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("uid") Long uid){
        //get uname by uid
        //filename is uname
        String fileName = billService.getUnameByUid(uid);
        File file = new File(downloadFilePath +'/'+ fileName);

        //check if this file is in the database
        if(!file.exists()){
            return "bill not exist!";
        }

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            return "download file failed";
        }
        return "successfully downloaded";
    }
}
