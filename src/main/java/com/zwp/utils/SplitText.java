package com.zwp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zwp.entity.Content;

public class SplitText {
	/**
	 * 获取图书全路径
	 * @return
	 */
	public static  String getBookPath() {
        String basePath="F:\\电子书\\网络小说\\";
        System.out.println("请输入您想要阅读的电子书名称: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.next();
        String bookPath = basePath + fileName + ".txt";
//        scanner.close();
        return bookPath;
    }

	/**
	 * 截取图书名称
     * v2: 为了方便查询书目，切分书籍表，在获取的同时，将书籍名存入书籍表
	 * @param bookPath
	 * @return
	 */
    public static String getRegBookName(String bookPath) {
        int index = bookPath.lastIndexOf("\\");
        String before = bookPath.substring(0,index+1);
        String after = bookPath.substring(index+1);
        System.out.println("before: " + before + ";after: " + after);
        // 由于这里固定知道我们的文件类型为txt，所以可以固定去4位
        int fileType = 4;// 包括点
        // 在不确定情况下
        int typeIndex = after.lastIndexOf(".");
        String bookName = after.substring(0,typeIndex);
        System.out.println(bookName);
        // 顺序执行应该在这里插入的，但是，后来和人讨论，我又钻牛角尖了
        // 将这里移动至分割结束
//        try {
//            DBUtil.addBookName(bookName);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        return bookName;
    }
    
    /**
     * 切割章节，然后一章章存入数据库
     * @return
     */
    public void splitXS() {
        // 记录影响行数
        int insertCount = 0;
    	// 保存小说章节的工具类
    	DBUtil dbUtil = new DBUtil();
//         定义一个Content列表保存图书信息
//        List<Content> book = new ArrayList<Content>();
//        String fileNamedirs="F:\\电子书\\《诡神冢》.txt";
        String fileNamedirs = getBookPath();
//        String fileNamedirs="F:\\电子书\\诡神冢.txt";
        String bookName = getRegBookName(fileNamedirs);
        try {
            // 编码格式
            String encoding = "GBK";
//             String encoding = "UTF-8";
            // 文件路径
            File file = new File(fileNamedirs);
            if (file.isFile() && file.exists()) { // 判断文件是否存在
                // 输入流
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                Long count = (long) 0;
                boolean bflag=false;
                int n=0;
                String newStr=null;
                String titleName=null;
                String newChapterName = null;//新章节名称
                String substring=null;
                int indexOf=0;
                int indexOf1=0;
                int line=0;
                //小说内容类
                Content content;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    content=new Content();
                    //小说名称
                    content.setName(bookName);

                    count++;
                    // 正则表达式
                    Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s{1})(.*)($\\s*)");

                    Matcher matcher = p.matcher(lineTxt);
                    Matcher matcher1 = p.matcher(lineTxt);

                    newStr=newStr+lineTxt;

                    while (matcher.find()) {
                        titleName = matcher.group();
                        //章节去空
                        newChapterName = titleName.trim();
                        //获取章节
                        //System.out.println(newChapterName);
                        content.setChapter(newChapterName);
                        indexOf1=indexOf;
                        //System.out.println(indexOf);
                        indexOf = newStr.indexOf(newChapterName);
                        // System.out.println(newChapterName + ":" + "第" + count + "行"); // 得到返回的章
                        if(bflag) {
                            bflag=false;
                            break;
                        }
                        if(n==0) {
                            indexOf1 = newStr.indexOf(newChapterName);
                        }
                        n=1;
                        bflag=true;
                        //System.out.println(chapter);
                    }
                    while(matcher1.find()) {
                        if(indexOf!=indexOf1) {
                            //根据章节数量生成
                            content.setNumber(++line);
                            content.setId(line);
                            substring = newStr.substring(indexOf1, indexOf);
                            //System.out.println(substring);
                            content.setContent(substring);
//                            book.add(content);
//                            System.out.println(content.toString());
                            // 将章节转存进MySQL，如果发生问题，顺道删除书籍
                            try {
                                dbUtil.insertConent(content);
                                insertCount ++;
                            }catch (Exception e){
                                System.out.println("插入出现问题，请仔细查看以下报错信息：");
                                // 这里就不用再判断了
//                                try {
//                                    dbUtil.delBookName(bookName);
//                                }catch (Exception e1) {
//                                    System.out.println("删除出现问题，请手动删除本书目录");
//                                    e1.printStackTrace();
//                                }
                                e.printStackTrace();
                            }
                        }
                    }
                }
                bufferedReader.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        // 判断影响行数，如果插入行数为零那么，切割是有问题的，不可以执行插入书目操作
        if (insertCount > 0) {
            System.out.println("插入成功，开始同步书名！");
            try {
                DBUtil.addBookName(bookName);
                System.out.println("同步书名结束，恭喜您录入书籍成功！");
            }catch (Exception e) {
                System.out.println("同步书名失败，请移除书籍，重新导入！");
                e.printStackTrace();
            }
        }else {
            System.out.println("如果没有报错信息，就是分割出现问题，请检查章节名格式，推荐统一将数字转化为中文！");
        }
        //        return book;
    }
    
    public static void main(String[] args) throws Exception {
    	SplitText splitText = new SplitText();
//    	List<Content> contents = new ArrayList<Content>();
//    	Content content1 = new Content();
//    	content1.setId(1);
//    	content1.setName("1");
//    	content1.setChapter("1");
//    	content1.setContent("1");
//    	content1.setNumber(1);
//    	Content content2 = new Content();
//    	content2.setId(2);
//    	content2.setName("2");
//    	content2.setChapter("2");
//    	content2.setContent("2");
//    	content2.setNumber(2);
//    	List<Content> contents = splitText.splitXS();
    	splitText.splitXS();
//    	DBUtil dbUtil = new DBUtil();
//    	contents.add(content1);
//    	contents.add(content2);
//    	dbUtil.batchAddContents(contents);
//    	splitText.splitXS();
	}
}
