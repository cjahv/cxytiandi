package com.cxytiandi.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import com.cxytiandi.mongo.result.ArticleResult;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import java.util.List;
/**
 * aggreate操作示列
 * @author yinjihuan
 *
 */
public class AggreateTest {
private static MongoTemplate mongoTemplate;
	
	static {
		//加载spring
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		mongoTemplate = (MongoTemplate) ac.getBean("mongoTemplate");
	}
	
	public static void main(String[] args) {
		aggregation();
	}
	/**
	 * 聚合使用
	 * 统计每个用户的文章数量
	 */
	private static void aggregation() {
		Aggregation agg = newAggregation(
			    group("author").count().as("count").first("author").as("name"),
			    project("name","count"),
			    sort(Direction.DESC, "count"),
			    match(Criteria.where("count").gt(0))
		);
		AggregationResults<ArticleResult> results = mongoTemplate.aggregate(agg, "article_info", ArticleResult.class);
		List<ArticleResult> tagCount = results.getMappedResults();
		for (ArticleResult studentResult : tagCount) {
			System.out.println(studentResult.getName() + "\t" + studentResult.getCount());
		}
	}
}
