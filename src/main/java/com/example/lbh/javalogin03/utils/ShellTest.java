//package com.example.lbh.javalogin03.utils;
//
//import com.jcraft.jsch.*;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
//
//import javax.swing.*;
//import java.io.*;
//import java.util.stream.Collectors;
//
//public class ShellTest {
//    public static void main(String[] args) throws JSchException, SftpException {
//        JSch jsch = new JSch();
//        //jsch.setKnownHosts("C:\\Users\\XXX\\.ssh\\known_hosts");
//        int port = 22;
//        String user = "root";
//        Session session = jsch.getSession(user, "47.96.180.68", 22);
//        session.setPassword("Adminroot1211");
//        session.setUserInfo(new MyUserInfo());
//        session.connect(30000);
//        Channel channel = session.openChannel("sftp");
//        //((ChannelShell)channel).setAgentForwarding(true);
//        //使用Window的问题
////        channel.setInputStream(new FilterInputStream(System.in) {
////            @Override
////            public int read(byte[] b, int off, int len) throws IOException {
////                return in.read(b, off, (len > 1024 ? 1024 : len));
////            }
////        });
////        String goToRoot = "cd /hwq\n";
////        InputStream goToRootStream = new ByteArrayInputStream(goToRoot.getBytes());
////        channel.setInputStream(goToRootStream);
////        channel.setInputStream(System.in);
//        channel.setOutputStream(System.out);
//        //去除控制台彩色输出
////        ((ChannelShell) channel).setPtyType("vt102");
////        ((ChannelShell) channel).setEnv("LANG", "zh_CN");
//        channel.connect();
//        ChannelSftp channelSftp = (ChannelSftp) channel;
//        InputStream input= channelSftp.get("/hwq/datatransfer/test.json");
//        String convert = new BufferedReader(new InputStreamReader(input)).lines().collect(Collectors.joining("\n"));
//        System.out.println(convert);
//    }
//
//    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
//        @Override
//        public String getPassword() {
//            return null;
//        }
//
//        @Override
//        public boolean promptYesNo(String message) {
//            return true;
//        }
//
//        @Override
//        public String getPassphrase() {
//            return null;
//        }
//
//        @Override
//        public boolean promptPassphrase(String message) {
//            return false;
//        }
//
//        @Override
//        public boolean promptPassword(String message) {
//            return false;
//        }
//
//        @Override
//        public void showMessage(String message) {
//            JOptionPane.showMessageDialog(null, message);
//        }
//
//        @Override
//        public String[] promptKeyboardInteractive(String destination,
//                                                  String name,
//                                                  String instruction,
//                                                  String[] prompt,
//                                                  boolean[] echo) {
//            return null;
//        }
//    }
//}
