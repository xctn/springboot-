package com.springlawyermanagement.liufan;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @Description mongodb数据库工具
 * @Author Tcc
 * @Date 2018年1月22日下午4:21:41
 */
public class MongoUtils {
	/**
	 * mongodb数据库ip地址
	 */
	public final static String MONGO_IP_PATH = "192.168.157.129";
	/**
	 * 端口号
	 */
	public final static Integer MONGO_PORT = 27017;
	/**
	 * 数据库名称
	 */
	public final static String MONGO_NAME = "spring";
	/**
	 * 集合名称
	 */
	public final static String COLLECTION_NAME = "test";
	/**
	 * @MethodDesc 获取collection
	 * @Return MongoCollection<Document>
	 * @Author Tcc
	 * @Date 2018年1月22日下午4:40:21
	 */
	public static MongoDatabase getDatabase(){
		System.out.println(1);
		MongoClient mongoClient = new MongoClient("192.168.157.129", 27017);
		System.out.println(2);
		System.out.println(mongoClient);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(MONGO_NAME);  
        System.out.println("Connect to mongodb database successfully");
        return mongoDatabase;
	}
}
