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
@RequestMapping("") //请求的路径
public class MainController {
 
    /**
    * 直接返回字符串
    * @param request
    * @return
    */
    //请求的路径，方式
    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody public Document Login(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 

        //获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        System.out.println(username);
        System.out.println(password);

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  

        //创建 collection
        // db.createCollection("layor");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("user");
         System.out.println("集合 user 连接成功");

        

        FindIterable findIterable = collection.find(Filters.eq("username", username));
        MongoCursor<Document> mongoCursor = findIterable.iterator();  

        if (mongoCursor.hasNext()) {
            Document user  = mongoCursor.next();
            System.out.println(user.get("password"));
            if (user.get("password").equals(password)&&user.get("type").equals(type)){
                return user;
            } else {
                return new Document("error", "登录失败");
            }

        } else {
            return new Document("error", "用户不存在");
        }
    }
    @RequestMapping(value = "register", method = RequestMethod.GET)
    @ResponseBody public String registeruser(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 
        //获取请求的参数
        String userid = CommonUtils.getUUID();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String position = request.getParameter("position");
        String age = request.getParameter("age");
        String sex  = request.getParameter("sex");
        String idcard = request.getParameter("idcard");
        String tel  = request.getParameter("tel");
        MongoDatabase db = MongoUtils.getDatabase();  

        //创建 collection
        // db.createCollection("user");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("user");
         System.out.println("集合 user 连接成功");


        //插入User
        Document document = new Document("username", username).append("password", password).append("type", 1).append("position", position).append("age", age).append("sex", sex).append("idcard", idcard).append("tel",tel).append("userid",userid);

        //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
        collection.insertOne(document);
        System.out.println("用户插入成功");  
        return("success");
}
}
