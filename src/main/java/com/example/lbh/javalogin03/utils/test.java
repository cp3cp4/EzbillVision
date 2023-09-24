package com.example.lbh.javalogin03.utils;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static String serialize(String filename) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filename));
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        return encodedString;
    }

    public Date getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public String[] generatePastDays(int numberOfDays,Date currentDate){
        String[] output = new String[numberOfDays];
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        System.out.println("Date = "+ cal.getTime());
        return output;

    }

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/java/com/example/lbh/javalogin03/fileStorage/fourthFace.jpg";
        String input = serialize(fileName);
        String outputPath = "src/main/java/com/example/lbh/javalogin03/fileStorage/fourthOutput.txt";
        byte[] arr = input.getBytes();
        Files.write(Paths.get(outputPath),arr);
        System.out.println(arr.length);
    }


}
