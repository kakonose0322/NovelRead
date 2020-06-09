package com.zwp.mapper;

import java.util.List;

import com.zwp.entity.Content;
import com.zwp.entity.ContentVo;

public interface ContentMapper {
	// findAll
	public Content findAll();
	// 添加用户
	public void insertContent(Content content);
	// 复杂查询
	public List<Content> findContentByFlex(ContentVo contentVo);
	// 批量插入
	public void batchAddContent(List<Content> contents);
	// 根据number查章节
	public String findByNumber(int number);
	// 根据name和章节查询内容
	public String findContentByNameAndNumber(Content content);
	// 查询表中已拥有的书目
	public List<String> findAllBook();
	// 根据name删除书籍
	public Integer delBookByName(Content content);
	// 分割时添加书籍信息
    Integer addBookName(String bookName);
    // 当分割出现问题的时候，控制书籍表删除未成功导入的书籍信息
	Integer delbookName(String bookName);
	// 根据内容查找章节号
	List<Integer> findNumerByContent(String content);
	// 根据章节号返回小说内容
	String findContentByNumber(Content content);
}