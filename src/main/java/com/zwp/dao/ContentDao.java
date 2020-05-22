package com.zwp.dao;

import com.zwp.entity.Content;

public interface ContentDao {
	// findAll
	public Content findAll();
	// 添加用户
	public void insertContent(Content content);
}
