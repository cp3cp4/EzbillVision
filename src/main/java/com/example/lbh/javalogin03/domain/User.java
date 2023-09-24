
//domain中的User.java
package com.example.lbh.javalogin03.domain;

import javax.persistence.*;

@Table(name = "user")
@Entity
public class User {
    // 注意属性名要与数据表中的字段名一致
    // 主键自增int(10)对应long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    // 用户名属性varchar对应String
    private String uname;

    // 密码属性varchar对应String
    private String password;

    //原始图片
    private String source_image;

    //特征图片
    private String feature_image;

    //是否是房东
    private int landlord;

    public int getLandlord() {
        return landlord;
    }

    //房间号
    private int room;

    public int getRoom() {
        return room;
    }

    //email
    private String email;

    //phone number
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(){};

    public User(String uname, String password, String packet){
        this.uname = uname;
        this.password = password;
        source_image = packet;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setSourceImage(String sourceImage){
        this.source_image = sourceImage;
    }
    public String getSourceImage(){
        return source_image;
    }

    public String getUname() {
        return uname;
    }

    public void setFeatureImage(String featureImage){
        this.feature_image = featureImage;
    }

    public String getFeatureImage()
    {
        return feature_image;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", source_image='" + source_image + '\'' +
                ", feature_image='" + feature_image + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
