package com.zwp;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.zwp.dao.ContentDao;
import com.zwp.dao.impl.ContentDaoImpl;
import com.zwp.entity.Content;
import com.zwp.entity.ContentVo;
import com.zwp.mapper.ContentMapper;
import com.zwp.utils.DBUtil;

public class MyBatisTest {
	private static SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void setUp() throws Exception {
		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
//	@Test
//	public void findAll() throws Exception {
//		String resource = "SqlMapConfig.xml";
//		InputStream inputStream = Resources.getResourceAsStream(resource);
//		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
//		SqlSession session = sessionFactory.openSession();
//		Content content = session.selectOne("findAll");
//		System.out.println(content);
//		session.close();
//	}
	
	
	
	@Test
	public void testFind() throws Exception {
		// 创建dao对象
		ContentDao dao = new ContentDaoImpl(sqlSessionFactory);
		Content content = dao.findAll();
		System.out.println(content);
	}
	
	// dao方式
	@Test
	public void testInsert() throws Exception {
		Content content = new Content();
		content.setId(1);
		content.setName("1");
		content.setChapter("1");
		content.setContent("1");
		content.setNumber(1);
		ContentDao dao = new ContentDaoImpl(sqlSessionFactory);
		dao.insertContent(content);
	}
	// mapper方式
	@Test
	public void testFindMapper() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建Mapper对象，mybatis自动生成mapper代理对象
		ContentMapper mapper = sqlSession.getMapper(ContentMapper.class);
		// 调用Mapper的方法
		Content content = mapper.findAll();
		System.out.println(content);
	}
	
	@Test
	public void testFlex() throws Exception {
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
	}


	
	public static void main(String[] args) throws Exception {
		DBUtil dbUtil = new DBUtil();
//		Content content = DBUtil.findAContentllContent();
//		System.out.println(content);
//		Content content2 = new Content();
//		content2.setId(2);
//		content2.setName("2");
//		content2.setChapter("2");
//		content2.setContent("2");
//		content2.setNumber(2);
//		dbUtil.insertConent(content2);
		String bookName = "demo";
//		int row = dbUtil.addBookName(bookName);
		int row = dbUtil.delBookName(bookName);
		System.out.println(row);
	}
}
