package com.zwp.entity;

/**
 * 查询条件的封装
 * @author Kakonose
 */
public class ContentVo {
	// 这里可以添加一些复杂查询条件，比如有一个内部类,Custom等，
	// 另外，这样的好处就是可以直接封装一个对象进去
	Content content = new Content();

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
	
}
