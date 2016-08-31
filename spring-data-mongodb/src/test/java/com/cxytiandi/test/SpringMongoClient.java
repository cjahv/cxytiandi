package com.cxytiandi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Update.Position;

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
		//initArticle();
		updateArticle();
	}
	
	public static void updateArticle() {
		//修改第一条author为yinjihuan的数据中的title和visitCount
		Query query = Query.query(Criteria.where("author").is("yinjihuan"));
		Update update = Update.update("title", "MongoTemplate").set("visitCount", 10);
		mongoTemplate.updateFirst(query, update, Article.class);
		
		//修改全部符合条件的
		mongoTemplate.updateMulti(query, update, Article.class);
		
		//特殊更新，更新author为jason的数据，如果没有author为jason的数据则以此条件创建一条新的数据
		//当没有符合条件的文档，就以这个条件和更新文档为基础创建一个新的文档，如果找到匹配的文档就正常的更新。
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").set("visitCount", 10);
		mongoTemplate.upsert(query, update, Article.class);
		
		//更新条件不变，更新字段改成了一个我们集合中不存在的，用set方法如果更新的key不存在则创建一个新的key
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").set("money", 100);
		mongoTemplate.updateMulti(query, update, Article.class);
		
		//update的inc方法用于做累加操作，将money在之前的基础上加上100
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").inc("money", 100);
		mongoTemplate.updateMulti(query, update, Article.class);
		
		//update的rename方法用于修改key的名称
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").rename("visitCount", "vc");
		mongoTemplate.updateMulti(query, update, Article.class);
		
		//update的unset方法用于删除key
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").unset("vc");
		mongoTemplate.updateMulti(query, update, Article.class);
		
		//update的pull方法用于删除tags数组中的java
		query = Query.query(Criteria.where("author").is("jason"));
		update = Update.update("title", "MongoTemplate").pull("tags", "java");
		mongoTemplate.updateMulti(query, update, Article.class);
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
