package com.zwp.utils;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.zwp.entity.Content;
import com.zwp.entity.ContentVo;
import com.zwp.mapper.ContentMapper;

/**
 * 数据操纵工具，实现方法，对分割好的章节进行保存
 * @author Kakonose
 */
public class DBUtil {
	
	// 初始化数据库连接
	public void setUp() throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	// 本类一旦初始化就执行mybatis的初始化
//	public DBUtil() throws Exception {
//		setUp();
//	}
	
	public static Content findAContentllContent() throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		// 调用Mapper的方法
		Content content = mapper.findAll();
		System.out.println(content);
		return content;
	}
	
	public static List<Content> findByFlex() throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		// 创建包装对象，设置查询条件
		ContentVo vo = new ContentVo();
		Content content = new Content();
		content.setChapter("1");
		vo.setContent(content);
		List<Content> contents = mapper.findContentByFlex(vo);
		System.out.println(contents);
		return contents;
	}
	
	public static void insertConent(Content content) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		mapper.insertContent(content);
		sqlSession.commit();
		sqlSession.close();
	}
	
	public static void batchAddContents(List<Content> contents) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		mapper.batchAddContent(contents);
		sqlSession.commit();
		sqlSession.close();
	}
	
	public static String findByNumber(int number) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		String result = mapper.findByNumber(number);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static String findContentByNameAndNumber(Content content) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		String result = mapper.findContentByNameAndNumber(content);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static List<String> findAllBook() throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		List<String> result = mapper.findAllBook();
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static Integer delBookByName(Content content) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		Integer result = mapper.delBookByName(content);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static Integer addBookName(String bookName) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		Integer result = mapper.addBookName(bookName);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static Integer delBookName(String bookName) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		Integer result = mapper.delbookName(bookName);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static List<Integer> findNumerByContent(String content) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		List<Integer> result = mapper.findNumerByContent(content);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	public static String findContentByNumber(Content content) throws Exception {
		// 定义工厂类
		SqlSessionFactory sqlSessionFactory;
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		String result = mapper.findContentByNumber(content);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}
}
