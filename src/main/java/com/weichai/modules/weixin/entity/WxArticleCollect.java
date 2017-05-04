/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.weichai.modules.weixin.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.weichai.common.persistence.DataEntity;
import com.weichai.common.utils.StringUtils;

/**
 * 构建多图文消息列表Entity
 * @author wwhui
 * @version 2016-03-28
 */
public class WxArticleCollect extends DataEntity<WxArticleCollect> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 消息
	private String issend;		// 0未发送 1发送
	private String mediaId;		// 素材id
	private String articleList;		// 文章集合
	private String articleTitles;		// 标题集合
	
	public WxArticleCollect() {
		super();
	}

	public WxArticleCollect(String id){
		super(id);
	}

	@Length(min=0, max=255, message="消息长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=11, message="0未发送 1发送长度必须介于 0 和 11 之间")
	public String getIssend() {
		return issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}
	
	@Length(min=0, max=64, message="素材id长度必须介于 0 和 64 之间")
	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	@Length(min=0, max=1024, message="文章集合长度必须介于 0 和 1024 之间")
	public String getArticleList() {
		return articleList;
	}

	public void setArticleList(String articleList) {
		this.articleList = articleList;
	}
	
	@Length(min=0, max=1024, message="标题集合长度必须介于 0 和 1024 之间")
	public String getArticleTitles() {
		return articleTitles;
	}

	public void setArticleTitles(String articleTitles) {
		this.articleTitles = articleTitles;
	}

	public List<Map> getArtcleList(){
		List<Map> mapList= Lists.newArrayList();
		if(StringUtils.isNotBlank(articleList)){
			String[] ids=articleList.split(",");
			String[] titles=articleTitles.split(",");
			for(int i=0;i<ids.length;i++){
				Map map=new HashMap();
				map.put("id",ids[i]);
				map.put("title",titles[i]);
				mapList.add(map);
			}
		}

		return  mapList;
	}
}