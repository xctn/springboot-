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
@RequestMapping("customer") //请求的路径
public class CustomerController {
 
    /**
    * 直接返回字符串
    * @param request
    * @return
    */
    //请求的路径，方式
    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody public String createcustomer(HttpServletRequest request) {
 
        //新增一条客户信息
 
        //获取请求的参数
        
        String customername = request.getParameter("customername");
        String caseid = request.getParameter("caseid");
        String userid = request.getParameter("userid");
        String tel  = request.getParameter("tel");
        String address = request.getParameter("address");
        String customerid = CommonUtils.getUUID();


        //连接数据库

        MongoDatabase db = MongoUtils.getDatabase();  

        

        //连接collection
        MongoCollection<Document> collection = db.getCollection("customer");


        
        Document document = new Document("customername", customername).append("caseid", caseid).append("userid", userid).append("tel", tel).append("address", address).append("customerid", customerid);

     
        
        
        //插入一条数据到数据库对应的表
        collection.insertOne(document);

       


        System.out.println("客户信息插入成功");  
      

 
        
 
        return "success";
    }
     //请求的路径，方式
     @RequestMapping(value = "update", method = RequestMethod.GET)
     @ResponseBody public String updatecustomer(HttpServletRequest request) {
  
         //更改一条律师信息
  
         //获取请求的参数
         String customerid =request.getParameter("customerid");
         String customername = request.getParameter("customername");
         String caseid = request.getParameter("caseid");
         String userid = request.getParameter("userid");
         String tel  = request.getParameter("tel");
         String address = request.getParameter("address");
         
 
         //连接数据库
        
 
        
 
         //连接collection
         MongoDatabase db = MongoUtils.getDatabase(); 
         MongoCollection<Document> collection = db.getCollection("customer");
          System.out.println("集合 customer 连接成功");
 
 
        //根据客户id来更改客户信息
 
          collection.updateOne(Filters.eq("customerid", customerid), new Document("$set",new Document("customername", customername)
          .append("caseid", caseid)
          .append("userid", userid)
          .append("tel", tel)
          .append("address", address)));
 
 
         System.out.println("客户信息修改成功");  
       
 
  
         //Layor hr = new Layor(name, age);
  
         return "success";
     }
     @RequestMapping(value = "select", method = RequestMethod.GET)
    @ResponseBody public List selectcustomer(HttpServletRequest request) {
 
       
        //按客户姓名查找

        //获取请求的参数
        String customername = request.getParameter("customername");

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  

        

        //连接collection
        MongoCollection<Document> collection = db.getCollection("customer");
         System.out.println("集合 customer 连接成功");

        

        FindIterable findIterable = collection.find(Filters.eq("customername", customername));
        MongoCursor<Document> mongoCursor = findIterable.iterator();  
        List<Document> list1=new ArrayList<Document>();

        while(mongoCursor.hasNext()) {
            Document customer  = mongoCursor.next(); 
                list1.add(customer);

        }
        return list1;
    }

        @RequestMapping(value = "delete", method = RequestMethod.GET)
        @ResponseBody public String deletecustomer(HttpServletRequest request) {
            //删除一条客户信息
            //获取参数
            String customerid = request.getParameter("customerid");


            MongoDatabase db = MongoUtils.getDatabase();  

        

        //连接collection
        MongoCollection<Document> collection = db.getCollection("customer");
        collection.deleteOne(Filters.eq("customerid", customerid));
        return "success";

    }
    @RequestMapping(value = "showlawyerown", method = RequestMethod.GET)
        @ResponseBody public List showlawyerown(HttpServletRequest request) {
     
            
            //律师登录时返回的是自己承接的客户的信息
    
            //获取请求的参数
            String userid = request.getParameter("userid");
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
           
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("customer");
             System.out.println("集合 customer 连接成功");
    
            
            //通过用户id查找
            FindIterable findIterable = collection.find(Filters.eq("userid", userid));
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list2=new ArrayList<Document>();
    
            while (mongoCursor.hasNext()) {
                Document customer  = mongoCursor.next();
                list2.add(customer);
            } 
            return list2;
        }
            @RequestMapping(value = "showadminown", method = RequestMethod.GET)
        @ResponseBody public List showadminown(HttpServletRequest request) {
     
            
            //管理员登录返回所有客户信息
    
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("customer");
             System.out.println("集合 customer 连接成功");
     
            
    
            FindIterable findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list3=new ArrayList<Document>(); 
            while (mongoCursor.hasNext()) {
                Document customer  = mongoCursor.next(); 
                list3.add(customer);
            } return list3;
            //else {
               // return new Document("error", "查询律师不存在");
           // }
        }
        @RequestMapping(value = "showexistlawyer", method = RequestMethod.GET)
        @ResponseBody public List showexistlawyer(HttpServletRequest request) {
     
            
            //返回已存在的律师
    
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            //创建 collection
            // db.createCollection("layor");
            // System.out.println("集合创建成功");
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("user");
             System.out.println("集合 user 连接成功");
    
            
    
            FindIterable findIterable = collection.find(Filters.eq("type","1"));
            MongoCursor<Document> mongoCursor = findIterable.iterator();  
            List<Document> list4=new ArrayList<Document>();
    
            while (mongoCursor.hasNext()) {
                Document user  = mongoCursor.next();
                list4.add(user);
            } 
            return list4;
        }

    }
