package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class SpringMongoClient {
	private static MongoTemplate mongoTemplate;
	
	static {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args)  throws Exception {
		//读取所有的集合名称
		mongoTemplate.getCollectionNames().forEach(System.out::println);
	}
}
