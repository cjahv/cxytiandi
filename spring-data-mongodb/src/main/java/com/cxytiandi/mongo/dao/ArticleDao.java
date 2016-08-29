package com.cxytiandi.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.cxytiandi.mongo.document.Article;

public class ArticleDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * 添加文章
	 * @author yinjihuan
	 * @param article
	 */
	public void addArticle(Article article) {
		mongoTemplate.save(article);
	}
}
