package com.cxytiandi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.cxytiandi.mongo.document.Article;
/**
 * 测试类
 * @author yinjihuan
 *
 */
public class SpringMongoClient {
	private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args)  throws Exception {
		initArticle();
	}
	
	/**
	 * 初始化文章信息
	 * @author yinjihuan
	 */
	public static void initArticle() {
		//循环添加
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setTitle("MongoTemplate的基本使用");
			article.setAuthor("yinjihuan");
			article.setUrl("http://cxytiandi.com/blog/detail/" + i);
			article.setTags(Arrays.asList("java", "mongodb", "spring"));
			article.setVisitCount(0L);
			article.setAddTime(new Date());
			mongoTemplate.save(article);
		}
		
		//批量添加
		List<Article> articles = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			Article article = new Article();
			article.setTitle("MongoTemplate的基本使用");
			article.setAuthor("yinjihuan");
			article.setUrl("http://cxytiandi.com/blog/detail/" + i);
			article.setTags(Arrays.asList("java", "mongodb", "spring"));
			article.setVisitCount(0L);
			article.setAddTime(new Date());
			articles.add(article);
		}
		mongoTemplate.insert(articles, Article.class);
	}
	
	//读取所有的集合名称
	public static void showCollectionNames() {
		mongoTemplate.getCollectionNames().forEach(System.out::println);
	}
}
