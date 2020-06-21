package com.zwp.read;

import java.util.List;
import java.util.Scanner;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.zwp.entity.Content;
import com.zwp.utils.DBUtil;
import com.zwp.utils.SplitText;
import com.zwp.utils.WindowsCmdUtil;

public class ReadText {
	public static void main(String[] args) {  
        // 一次调用所有章节，作为全局变量
//        List<Content> book =  myBook.getBookContent();
        // 定义全局变量保存章节内容
        String content = "";
        // 定义一个int型变量控制一直向下读
        int chapterCount = 0;
        // 记录当前使用次数，主要用于区别是否加载听取模式
        int useCount = 0;
        // 记录模式三的使用次数，因为有可能其它模式转入模式3
        int method3Count = 0;
        // 记录用户输入
        int userInput = 0;
        // 函数作用域问题
        Scanner sc = new Scanner(System.in);
        // 保存书名
        String name = "";
        // 初始化content容器
        Content queryContent = new Content();
//        Content currentContent = new Content();

        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        Dispatch sapo = sap.getObject();
        try {
            // 音量 0-100
            sap.setProperty("Volume", new Variant(100));
            // 语音朗读速度 -10 到 +10
            sap.setProperty("Rate", new Variant(2));
            if (useCount == 0) {
                // 第一次的时候需要确定书名(注意：这里是为了防止用户就是不执行指令1)
//                System.out.println("请输入要朗读的书名：");
//                name = sc.next()；
                name = setBookName();
                queryContent.setName(name);
                // （这步欠考虑，貌似去掉也行，如果出问题就开启中间件保存）
                // 这里做了一个中间件转化，主要为了避免后面查询参数的
//                queryContent.setName(currentContent.getName());
                // 听取模式的选择，只有 模式0和1，已经2的第一次，才用得到
                printScreen();
                // 记录用户输入值
                int myInput = sc.nextInt();
                // 尝试固有化
                userInput = myInput;
            }
//            else {
//                // 第二次使用(这里不涉及二次使用,之前理解错了)
//                if (userInput == 0|| userInput == 1 || userInput == 2 || userInput == 4) {
//                    // 听取模式的选择，只有 模式（0+1）和1以及3需要使用
//                    printScreen();
//                    // 记录用户输入值
//                    int myInput = sc.nextInt();
//                    // 尝试固有化
//                    userInput = myInput;
//                }
//            }

            while (userInput < 12) {
                // 第一次进入无需加载菜单项，且运行模式不等于3(自起始开始累计的，所以无需重复触发)
                if (useCount != 0 && userInput != 3) {
                    printScreen();
                    userInput = sc.nextInt();
                }
                // 这里要拿到正确的userInput
                switch (userInput) {
                    case 0:
                        // 这里就是修改本方法的全局变量 name
                        name = setBookName();
                        queryContent.setName(name);
                        System.out.println("书名修改成功，现在系统操作书籍为：" + queryContent.getName());
                        useCount++;
                        break;
                    case 1:
//                        System.out.println("请输入要朗读的书名：");
//                        name = sc.next();
                        System.out.println("请输入要朗读的章节号：");
//                        Scanner scan = new Scanner(System.in);
//                        String str = sc.next();
                        int myInput = sc.nextInt();
//                        queryContent.setName(name);
                        queryContent.setNumber(myInput);
                        // 测试用数据
                        // content = "测试结果，好凄凉啊！";
                        content = DBUtil.findContentByNameAndNumber(queryContent);
                        // 执行朗读
                        Dispatch.call(sapo, "Speak", new Variant(content));
                        useCount++;
                        System.out.println("系统即将退出，感谢您的使用！");
                        sc.close();
                        System.exit(0);
                        break;
                    case 2:
//                        System.out.println("请输入要朗读的书名：");
//                        name = sc.next();
                        System.out.println("请输入要朗读的章节号：");
                        myInput = sc.nextInt();
//                        queryContent.setName(name);
                        queryContent.setNumber(myInput);
//                        for (Content content : book) {
//                            if (myInput == content.getId()) {
//                                str += content.getContent();
//                                continue;
//                            }
//                        }
                        // 测试用数据
                        // content = "测试结果，好凄凉啊！";
                        content = DBUtil.findContentByNameAndNumber(queryContent);
                        // 执行朗读
                        Dispatch.call(sapo, "Speak", new Variant(content));
                        useCount++;
                        break;
                    case 3:
                        // 如果第一次执行输入
                        if (method3Count == 0) {
//                            System.out.println("请输入要朗读的书名：");
//                            name = sc.next();
                            System.out.println("请输入要朗读的起始章节号：");
                            myInput = sc.nextInt();
                            chapterCount = myInput;
                            // 之前写是为了保险
//                            queryContent.setName(name);
                            queryContent.setNumber(chapterCount);
                        }
//                        content = DBUtil.findByNumber(chapterCount);
                        queryContent.setNumber(chapterCount);
                        // 测试用数据
                        // content = "测试结果，好凄凉啊！";
                        content = DBUtil.findContentByNameAndNumber(queryContent);
                        // 执行朗读
                        Dispatch.call(sapo, "Speak", new Variant(content));
                        System.out.println("number :" + chapterCount + " 朗读结束……");
                        chapterCount++;
                        useCount++;
                        method3Count++;
                        userInput = 3;
                        break;
                    case 4:
                        SplitText splitText = new SplitText();
                        System.out.println("开始分割章节，请耐心等待………………");
                        splitText.splitXS();
                        System.out.println("章节分割成功，请确认您最新要听取的书名：");
                        name = setBookName();
                        queryContent.setName(name);
                        useCount++;
                        break;
                    case 5:
                        List<String> booklist = DBUtil.findAllBook();
                        System.out.println("系统目前收录以下书籍：");
                        // 打印书名
                        booklist.forEach(System.out::println);
                        // 有数据显示的，为了防止数据耦合在一起不好观看，打印行
                        System.out.println();
                        useCount++;
                        break;
                    case 6:
//                        System.out.println("请输入您要移除的书籍名：");
//                        name = sc.next();
//                        queryContent.setName(name);
                        name = setBookName();
                        queryContent.setName(name);
                        try {
                            Integer res = DBUtil.delBookByName(queryContent);
                            // 成功时顺道移除书籍表信息
                            try {
                                Integer sucRes = DBUtil.delBookName(queryContent.getName());
                            }catch (Exception e) {
                                System.out.println("书籍表移除失败，请联系管理员进行手动移除！");
                            }
                        }catch (Exception e) {
                            System.out.println("移除失败，请重试！");
                        }
                        // 有数据显示的，为了防止数据耦合在一起不好观看，打印行
                        System.out.println();
                        useCount++;
                        break;
                    case 7:
                        System.out.println("请输入要查询的章节的精准内容（注意：尽量不一致，不然后续无法确认章节）：");
                        content = sc.next();
                        List<Integer> res = DBUtil.findNumerByContent(content);
                        System.out.println("包含改内容的章节号为（在为列表的情况下，最接近预期章节号的为准确值，可通过功能8确认）：");
                        // 打印查找到包含改内容的章节号
                        res.forEach(System.out::println);
                        // 有数据显示的，为了防止数据耦合在一起不好观看，打印行
                        System.out.println();
                        useCount++;
                        break;
                    case 8:
                        System.out.println("请输入要查询的章节号：");
                        myInput = sc.nextInt();
                        queryContent.setNumber(myInput);
                        content = DBUtil.findContentByNumber(queryContent);
                        // 打印所找到的文章内容，确定是否为本章节
                        System.out.println(content);
                        // 有数据显示的，为了防止数据耦合在一起不好观看，打印行
                        System.out.println();
                        useCount++;
                        break;
                    case 9:
                        System.out.println("请输入要增量添加的书籍名：");
                        String addBook = sc.next();
                        System.out.println("请输入该书籍的作者：");
                        String bookAuthor = sc.next();
                        System.out.println("请输入要添加的章节名（注意：要与该章节标题完全一致）：");
                        String addChapterName = sc.next();
                        WindowsCmdUtil.setupPythonToGetContent(addBook, bookAuthor, addChapterName);
                        System.out.println("可以通过功能8完成内容增加的确认");
                        System.out.println();
                        useCount++;
                        break;
                    case 10:
                        System.out.println("请输入要全量添加的书籍名：");
                        String addFullBook = sc.next();
                        WindowsCmdUtil.setupPythonToGetText(addFullBook);
                        System.out.println("通过11查看对应目录文件，是否添加成功，这里有可能该网站未收录您想要的书籍。");
                        System.out.println();
                        useCount++;
                        break;
                    case 11:
                        System.out.println();
                        WindowsCmdUtil.findFile();
                        System.out.println();
                        useCount++;
                        break;
                    default:
                        System.out.println("您的输入有误，请按提示输入！");
                        useCount++;
                        continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sc.close();
            sapo.safeRelease();
            sap.safeRelease();
        }
    }

    public static void printScreen() {
        System.out.println();
        System.out.println("请输入要听取的模式：");
        // （此项的结果值必须存在，不然无法使用）
        System.out.println("0: 请确定想要的书籍名；");
        System.out.println("1: 只读一次就结束；");
        System.out.println("2: 重复读；");
        System.out.println("3: 自起始位置开始读取；");
        System.out.println("4: 为本系统添加新的书籍（源文件路径为：F:\\电子书\\网络小说\\）；");
        System.out.println("5: 查询系统已有书目；");
        System.out.println("6: 移除您想移除的书目；");
        System.out.println("7: 根据您已知的章节内容查询该书的章节号；");
        System.out.println("8: 根据章节号返回文本内容，确定精确章节内容；");
        System.out.println("9: 根据书名、作者名、章节名，对已有书目进行增量添加；");
        System.out.println("10: 根据书名，从网站获得整本小说TXT文件，放入指定文件夹；");
        System.out.println("11: 遍历指定文件夹，查询是否增加成功，如果失败，请自行下载，或等待管理员后续补充可用链接；");
        System.out.println();
    }

    public static String setBookName() {
        System.out.println("请输入要操作的书名：");
        Scanner setSc = new Scanner(System.in);
        String name = "";
        name = setSc.next();
//        Content content = new Content();
//        content.setName(name);
        // 使用结束后回收setSC资源
//        setSc.close();
        return  name;
    }
}
