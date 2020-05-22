package com.zwp.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.zwp.dao.ContentDao;
import com.zwp.entity.Content;

public class ContentDaoImpl implements ContentDao {
	// 需要向dao实现类中注入SqlSessionFactory
	// 这里通过构造方法注入
	private SqlSessionFactory sqlSessionFactory;

	public ContentDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public Content findAll() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Content content = sqlSession.selectOne("findAll");
		sqlSession.close();
		return content;
	}

	public void insertContent(Content content) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//执行插入操作
		sqlSession.insert("insertContent", content);
		// 提交事务
		sqlSession.commit();
		// 释放资源
		sqlSession.close();
	}	
}
