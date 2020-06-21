package com.zwp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zwp
 * @create 2020-06-12 8:21
 * @deprecated Java执行Windows脚本文件
 */
public class WindowsCmdUtil {
    public static String setupPythonToGetContent(String bookName, String author, String chapterName) {
        // 基础命令
        String exce = "python E:\\PyChramProject\\PyTest\\GetNovelText\\GetNovelAsConent-la.py";
        try {
            exce += " " + bookName;
            exce += " " + author;
            exce += " " + chapterName;
            System.out.println(exce);
            Process proc = Runtime.getRuntime().exec(exce);
//            Process proc = Runtime.getRuntime().exec("E:\\Python\\python.exe E:\\PyChramProject\\PyTest\\GetNovelText\\GetNovelAsConent-la.py 转生眼中的火影世界 空想之龙 我斑愿称你为最强！");

            proc.waitFor();
        } catch (Exception e) {
            System.out.println("执行失败！");
        }
        return "success";
    }

    public static String setupPythonToGetText(String bookName) {
        // 基础命令
        String exce = "python E:\\PyChramProject\\PyTest\\GetNovelText\\GetNovelAsFile.py";
        try {
            exce += " " + bookName;
            System.out.println(exce);
            Process proc = Runtime.getRuntime().exec(exce);
//            Process proc = Runtime.getRuntime().exec("E:\\Python\\python.exe E:\\PyChramProject\\PyTest\\GetNovelText\\GetNovelAsConent-la.py 转生眼中的火影世界 空想之龙 我斑愿称你为最强！");

            proc.waitFor();
        } catch (Exception e) {
            System.out.println("执行失败！");
        }
        return "success";
    }

    public static void findFile() {
        File files = new File("F:/电子书/网络小说");//创建文件对象，url是目标目录
        if (files.exists()) { //目录是否存在
            File[] files2 = files.listFiles();// 读取文件夹下的所有文件
            int m = 0, n = 0;
            for (int i = 0; i < files2.length; i++) {
                if (files2[i].isFile()) { //判断是否是文件
                    m++;
                    System.out.println("第" + m + "个文件的名字是：" + files2[i].getName());
                } else {
                    n++;
                    System.out.println("第" + n + "个目录的名字是：" + files2[i].getName());
                }
            }
        } else {
            System.out.println("文件不存在");
        }
    }

    public static void doContent() {
        String bookName = "转生眼中的火影世界";
        String author = "空想之龙";
        String chapterName = "我斑愿称你为最强！";
        String s = WindowsCmdUtil.setupPythonToGetContent(bookName, author, chapterName);
        System.out.println(s);
    }

    public static void doText() {
        String bookName = "火影之王";
        String s = WindowsCmdUtil.setupPythonToGetText(bookName);
        System.out.println(s);
    }

    public static void main(String[] args) {
        findFile();
    }
}
