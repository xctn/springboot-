package com.springlawyermanagement.liufan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.springlawyermanagement.liufan.*;
import com.mongodb.MongoClient;


@RestController
@RequestMapping("case") //请求的路径
public class CaseController {
 
    /**
    * 直接返回字符串
    * @param request
    * @return
    */
    //请求的路径，方式
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody public String createcase(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
 
        //获取请求的参数
        
        String caseid = request.getParameter("caseid");
        String userid = request.getParameter("userid");
        String writeintime = request.getParameter("writeintime");
        String casetype = request.getParameter("casetype");
        String cost  = request.getParameter("cost");
        String finish = request.getParameter("finish");
        String info = request.getParameter("info");
        String id = CommonUtils.getUUID();


        //连接数据库

        MongoDatabase db = MongoUtils.getDatabase();  

        //创建 collection
        // db.createCollection("user");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("case");


        //插入Layor
        Document document = new Document("caseid",caseid).append("userid", userid).append("writeintime", writeintime).append("casetype", casetype).append("cost", cost).append("finish", finish).append("info", info).append("id", id);

        //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
        
        
        //插入一条数据到数据库对应的表
        collection.insertOne(document);

        //collection.deleteOne(new Document("age", "22"));

         //collection.updateOne(Filters.eq("age", "23"), new Document("$set",new Document("age",100)));


        System.out.println("案件信息插入成功");  
      

 
        //Layor hr = new Layor(name, age);
 
        return "success";
    }
     //请求的路径，方式
     @RequestMapping(value = "update", method = RequestMethod.GET)
     @ResponseBody public String updatecase(HttpServletRequest request) {
  
         //可以使用teamname获取url路径分隔
  
         //获取请求的参数
         String id =request.getParameter("id");
         String caseid = request.getParameter("caseid");
         String userid = request.getParameter("userid");
         String writeintime = request.getParameter("writeintime");
         String casetype = request.getParameter("casetype");
         String cost  = request.getParameter("cost");
         String finish = request.getParameter("finish");
         String info = request.getParameter("info");
         
 
         //连接数据库
        
 
         //创建 collection
         // db.createCollection("user");
         // System.out.println("集合创建成功");
 
         //连接collection
         MongoDatabase db = MongoUtils.getDatabase(); 
         MongoCollection<Document> collection = db.getCollection("case");
          System.out.println("集合 case 连接成功");
 
 
         //插入Layor
         //db.user.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}});
 
         //  List<Document> documents = new ArrayList<Document>();  
         //  documents.add(document);  
         //  collection.insertMany(documents); 
         
         
         //插入一条数据到数据库对应的表
 
         //collection.deleteOne(new Document("age", "22"));
         //collection.deleteOne(filter)
 
          collection.updateOne(Filters.eq("id", id), new Document("$set",new Document("caseid",caseid)
          .append("userid",userid)
          .append("writeintime", writeintime)
          .append("casetype", casetype)
          .append("cost", cost)
          .append("finish", finish)
          .append("info", info)));
 
 
         System.out.println("案件信息修改成功");  
       
 
  
         //Layor hr = new Layor(name, age);
  
         return "success";
     }
     @RequestMapping(value = "select", method = RequestMethod.GET)
    @ResponseBody public Document selectcase(HttpServletRequest request) {
 
        //可以使用teamname获取url路径分隔
        //按客户姓名查找

        //获取请求的参数
        String caseid = request.getParameter("caseid");

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  

        //创建 collection
        // db.createCollection("layor");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("case");
         System.out.println("集合 case 连接成功");

        

        FindIterable findIterable = collection.find(Filters.eq("caseid", caseid));
        MongoCursor<Document> mongoCursor = findIterable.iterator();  

        if(mongoCursor.hasNext()) {
            Document a  = mongoCursor.next(); 
                return a ;

        }
        else {
            return new Document("error", "用户不存在");
        }
        
    }

        @RequestMapping(value = "delete", method = RequestMethod.GET)
        @ResponseBody public String deletecase(HttpServletRequest request) {
            String id = request.getParameter("id");
            MongoDatabase db = MongoUtils.getDatabase();  

        //创建 collection
        // db.createCollection("case");
        // System.out.println("集合创建成功");

        //连接collection
        MongoCollection<Document> collection = db.getCollection("id");
        collection.deleteOne(Filters.eq("id", id));
        return "success";

    }
    @RequestMapping(value = "showlawyerown", method = RequestMethod.GET)
        @ResponseBody public List showlawyerown(HttpServletRequest request) {
     
            //可以使用teamname获取url路径分隔
            //律师登录返回承接律师是自己的案件信息
    
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
             System.out.println("集合 case 连接成功");
    
            
    
            FindIterable findIterable = collection.find(Filters.eq("userid", userid));
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list2=new ArrayList<Document>();
    
            while (mongoCursor.hasNext()) {
                Document b  = mongoCursor.next();
                list2.add(b);
            } 
            return list2;
        }
            @RequestMapping(value = "showadminown", method = RequestMethod.GET)
        @ResponseBody public List showadminown(HttpServletRequest request) {
     
            //可以使用teamname获取url路径分隔
            //管理员登录返回所有案件信息
    
            //获取请求的参数
            //String username = request.getParameter("username");
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            //创建 collection
            // db.createCollection("layor");
            // System.out.println("集合创建成功");
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("case");
             System.out.println("集合 case 连接成功");
     //  List<Document> documents = new ArrayList<Document>();  
        //  documents.add(document);  
        //  collection.insertMany(documents); 
            
    
            FindIterable findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list3=new ArrayList<Document>(); 
            while (mongoCursor.hasNext()) {
                Document c  = mongoCursor.next(); 
                list3.add(c);
            } return list3;
            //else {
               // return new Document("error", "查询律师不存在");
           // }
        }
        @RequestMapping(value = "showexistcaseidanduserid", method = RequestMethod.GET)
        @ResponseBody public List showexistcaseidanduserid(HttpServletRequest request) {
     
            //可以使用teamname获取url路径分隔
            //返回已存在的案件id和律师id
    
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            //创建 collection
            // db.createCollection("layor");
            // System.out.println("集合创建成功");
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("customer");
             System.out.println("集合 customer 连接成功");
    
            
    
            FindIterable findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list4=new ArrayList<Document>();
    
            while (mongoCursor.hasNext()) {
                Document d  = mongoCursor.next();
                list4.add(d);
            } 
            return list4;
        }

    }