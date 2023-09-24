package com.example.lbh.javalogin03.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

public class Deserialize {
    public void deserialize(byte[] packet)
    {
        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(packet);

            ObjectInputStream ois = new ObjectInputStream(bin);
            File newFile = (File)ois.readObject();
            ois.close();

            // write
            FileOutputStream fos = new FileOutputStream("C:\\Users\\12087\\Desktop\\MWS\\androidJava\\javaLogin03\\javaLogin03\\src\\main\\java\\com\\example\\lbh\\javalogin03\\fileStorage\\outputImage.jpg");

            System.out.println(" Recover content length: "+packet.length);

            fos.write(packet);
            fos.flush();
            fos.close();


        }
        catch(Exception e) {
            System.out.println("Exception during deserialization: " +
                    e);
            System.exit(0);
        }

    }
}
