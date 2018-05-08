package com.springlawyermanagement.liufan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.bson.Document;  
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.springlawyermanagement.liufan.MongoUtils;
import com.mongodb.MongoClient;
import com.mongodb.BasicDBObject;

import com.springlawyermanagement.liufan.*;



@RestController
@RequestMapping("user") //请求的路径
public class UserController {
     

 
    /**
    * 直接返回字符串
    * @param request
    * @return
    */
    //请求的路径，方式
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody public String createuser(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 
        //获取请求的参数
        String userid = CommonUtils.getUUID();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        String position = request.getParameter("position");
        String age = request.getParameter("age");
        String sex  = request.getParameter("sex");
        String idcard = request.getParameter("idcard");
        String tel  = request.getParameter("tel");
        System.out.print(username);
        System.out.print(password);

        //连接数据库

        MongoDatabase db = MongoUtils.getDatabase();  

        //创建 collection
        // db.createCollection("user");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("user");
         System.out.println("集合 user 连接成功");


        //插入User
        Document document = new Document("username", username).append("password", password).append("type", type).append("position", position).append("age", age).append("sex", sex).append("idcard", idcard).append("tel",tel).append("userid",userid);

        //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
        
        
        //插入一条数据到数据库对应的表
        collection.insertOne(document);

        //collection.deleteOne(new Document("age", "22"));

         //collection.updateOne(Filters.eq("age", "23"), new Document("$set",new Document("age",100)));


        System.out.println("用户插入成功");  
      

 
        //Layor hr = new Layor(name, age);
 
        return "success";
    }
    //请求的路径，方式
    @RequestMapping(value = "update", method = RequestMethod.GET)
    @ResponseBody public String updateuser(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 
        //获取请求的参数
        String userid = request.getParameter("userid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        String position = request.getParameter("position");
        String age = request.getParameter("age");
        String sex  = request.getParameter("sex");
        String idcard = request.getParameter("idcard");
        String tel  = request.getParameter("tel");

        //连接数据库
       

        //创建 collection
        // db.createCollection("user");
        // System.out.println("集合创建成功");

        //连接collection
        MongoDatabase db = MongoUtils.getDatabase(); 
        MongoCollection<Document> collection = db.getCollection("user");
         System.out.println("集合 user 连接成功");


        //插入Layor
        //db.user.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}});

        //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
        
        
        //插入一条数据到数据库对应的表

        //collection.deleteOne(new Document("age", "22"));
        //collection.deleteOne(filter)

         collection.updateOne(Filters.eq("userid", userid), new Document("$set",new Document("username",username)
         .append("password",password)
         .append("type",type)
         .append("position", position)
         .append("age", age)
         .append("sex", sex)
         .append("idcard", idcard)
         .append("tel",tel)));


        System.out.println("用户信息修改成功");  
      

 
        //Layor hr = new Layor(name, age);
 
        return "success";
    }
    @RequestMapping(value = "delete", method = RequestMethod.GET)
        @ResponseBody public String deletelawyer(HttpServletRequest request) {
            String userid = request.getParameter("userid");
            MongoDatabase db = MongoUtils.getDatabase();  

        //创建 collection
        // db.createCollection("user");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("user");
        collection.deleteOne(Filters.eq("userid", userid));
        return "success";}

        @RequestMapping(value = "select", method = RequestMethod.GET)
        @ResponseBody public Document selectlawyer(HttpServletRequest request) {
     
            //可以使用teamname获取url路径分隔
     
    
            //获取请求的参数
            String username = request.getParameter("username");
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            //创建 collection
            // db.createCollection("layor");
            // System.out.println("集合创建成功");
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("user");
             System.out.println("集合 username 连接成功");
    
            
    
            FindIterable findIterable = collection.find(Filters.eq("username", username));
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
    
            if (mongoCursor.hasNext()) {
                Document user  = mongoCursor.next();
                return user;
    
            } else {
                return new Document("error", "查询律师不存在");
            }
        }
        @RequestMapping(value = "admin", method = RequestMethod.GET)
        @ResponseBody public List adminselect(HttpServletRequest request) {
     
            //可以使用teamname获取url路径分隔
     
    
            //获取请求的参数
            //String username = request.getParameter("username");
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            //创建 collection
            // db.createCollection("layor");
            // System.out.println("集合创建成功");
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("user");
             System.out.println("集合 username 连接成功");
     //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
            
    
            FindIterable findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list1=new ArrayList<Document>(); 
            while (mongoCursor.hasNext()) {
                Document user  = mongoCursor.next(); 
                list1.add(user);
            } return list1;
            //else {
               // return new Document("error", "查询律师不存在");
           // }
        }
 
 //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 

    
}