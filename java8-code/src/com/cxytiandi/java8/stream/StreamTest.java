package com.cxytiandi.java8.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		//test();
		streamUse();
	}
	
	private static void streamUse() {
		List<String> contents = Arrays.asList("猿天地 IT互联网技术网站", "张三", "张三丰", "张大佛爷", "张");
		Optional<String> maxValue = contents.stream().max(String::compareToIgnoreCase);
	    if (maxValue.isPresent()) {
	         System.out.println("最大值: " + maxValue.get());
	    }
	    
	    Boolean isStartResult = contents.stream().anyMatch( s -> s.startsWith("张"));
	    System.out.println("是否有以张开头的数据:" + isStartResult);
	    
	    Optional<String> firstValue = contents.stream().filter( s -> s.startsWith("张")).findFirst();
	    firstValue.ifPresent(s -> System.out.println("第一个以张开头的数据: " + firstValue.get()));
	    
	    Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
	    while (iter.hasNext()) System.out.println(iter.next());
	    
	    List<Integer> numList = new ArrayList<>();
	    for (int i = 0; i < 10; i++) {
	    	numList.add(i);
		}
	    
	    Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
	    System.out.println(numbers);
	    
	    Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10).toArray(Integer[]::new);
	    System.out.println(numbers3); 
	    
	    Set<Integer> set = Stream.iterate(0, n -> n + 1).limit(10).collect(Collectors.toSet());
	    
	    List<Integer> list = Stream.iterate(0, n -> n + 1).limit(10).collect(Collectors.toList());
	}
	
	private static void test() {
		Stream<List<Integer>> number_stream = 
				Stream.of(
					 Arrays.asList(10),
					 Arrays.asList(22, 33),
					 Arrays.asList(44, 55, 66)
				 );
		number_stream.flatMap((c) -> c.stream()).forEach(System.out::println);
				
		//统计字符串长度大于1的数量
		List<String> contents = Arrays.asList("张三", "张三丰", "张大佛爷", "张");
		//老的做法
		int count = 0;
		for (String con : contents) {
			if(con.length() > 1) {
				count++;
			}
		}
		System.out.println(count);
		
		//用stream流的方式实现
		long new_count = contents.stream().filter(con -> con.length() > 1).count();
		System.out.println(new_count);
		
		//用并行的stream流的方式实现
		new_count = contents.parallelStream().filter(con -> con.length() > 1).count();
		System.out.println(new_count);
		
		//定义一个stream
		Stream<String> stream = Stream.of("java", "css", "php");
		
		//将stream中的值转成大写
		stream.map(String::toUpperCase).forEach(System.out::println);
		
		//将集合转成stream
		stream = Arrays.asList("张三", "张三丰", "张大佛爷", "张").stream();
		
		//generate产生无限的stream，当然可以用limit方法限制
		Stream<Double> dsStream = Stream.generate(Math::random);
		dsStream.forEach(System.out::println);
	}
}
