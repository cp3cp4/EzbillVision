
//domain中的User.java
package com.example.lbh.javalogin03.domain;

import javax.persistence.*;
import java.sql.Date;
//import java.util.Date;

@Table(name = "bill")
@Entity
public class Bill {
    // 注意属性名要与数据表中的字段名一致
    // 主键自增int(10)对应long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bid;

    //用户名属性varchar对应String
    private String uname;

    //Dates are only accurate to the day
    private String time;

    //Usage of each user per day
    private int expense;

    //default constructor
    public Bill() {
    }

    public long getBid() {
        return bid;
    }

    public void setBid(long bid) {
        this.bid = bid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    //full parameters constructor
    public Bill(String uname, String time, int expense) {
        this.uname = uname;
        this.time = time;
        this.expense = expense;
    }

    //getter and setter

    @Override
    public String toString() {
        return "Bill{" +
                "bid=" + bid +
                ", uname='" + uname + '\'' +
                ", time='" + time + '\'' +
                ", expense=" + expense +
                '}';
    }
}
