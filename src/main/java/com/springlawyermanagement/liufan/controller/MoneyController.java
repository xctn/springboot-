package com.springlawyermanagement.liufan.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.bson.Document;  
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.springlawyermanagement.liufan.*;
import com.mongodb.MongoClient;

@RestController
@RequestMapping("money") //请求的路径
public class MoneyController {
 
    /**
    * 直接返回字符串
    * @param request
    * @return
    */
    //请求的路径，方式
    @RequestMapping(value = "showlawyermoney", method = RequestMethod.GET)
    @ResponseBody public List showlawyermoney(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 

        //获取请求的参数
        String userid = request.getParameter("userid");
        

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  

        //创建 collection
        // db.createCollection("layor");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("case");
         System.out.println("集合 user 连接成功");

        

        FindIterable findIterable = collection.find(Filters.eq("userid", userid));
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        List<Document> list1=new ArrayList<Document>(); 

        while (mongoCursor.hasNext()) {
            Document user  = mongoCursor.next();
           // System.out.println(user.get("password"));
            if (user.get("finish").equals("是")){
                list1.add(user);
            } 
        } 
        return list1;
    }
    @RequestMapping(value = "showallmoney", method = RequestMethod.GET)
    @ResponseBody public List showallmoney(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 

        //获取请求的参数
        

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  

        //创建 collection
        // db.createCollection("layor");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("case");
         System.out.println("集合 user 连接成功");

        

        FindIterable findIterable = collection.find();
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        List<Document> list2=new ArrayList<Document>();

        while (mongoCursor.hasNext()) {
            Document user  = mongoCursor.next();
           // System.out.println(user.get("password"));
            if (user.get("finish").equals("是")){
                list2.add(user);
    }
}
return list2;
}
}