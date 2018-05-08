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
 
        //新增一条案件信息
        
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

       

        //连接collection
        MongoCollection<Document> collection = db.getCollection("case");


        //插入案件信息
        Document document = new Document("caseid",caseid).append("userid", userid).append("writeintime", writeintime).append("casetype", casetype).append("cost", cost).append("finish", finish).append("info", info).append("id", id);

        
        
        //插入一条数据到数据库对应的表
        collection.insertOne(document);


        System.out.println("案件信息插入成功");  
      
 
        return "success";
    }
     //请求的路径，方式
     @RequestMapping(value = "update", method = RequestMethod.GET)
     @ResponseBody public String updatecase(HttpServletRequest request) {
  
         //更改一条案件信息
  
         //获取请求的参数
         String id =request.getParameter("id");
         String caseid = request.getParameter("caseid");
         String userid = request.getParameter("userid");
         String writeintime = request.getParameter("writeintime");
         String casetype = request.getParameter("casetype");
         String cost  = request.getParameter("cost");
         String finish = request.getParameter("finish");
         String info = request.getParameter("info");
         

         MongoDatabase db = MongoUtils.getDatabase(); 
         MongoCollection<Document> collection = db.getCollection("case");
          System.out.println("集合 case 连接成功");
 
 
         //通过id来更改案件信息
 
          collection.updateOne(Filters.eq("id", id), new Document("$set",new Document("caseid",caseid)
          .append("userid",userid)
          .append("writeintime", writeintime)
          .append("casetype", casetype)
          .append("cost", cost)
          .append("finish", finish)
          .append("info", info)));
 
 
         System.out.println("案件信息修改成功");  
       
 
  
  
         return "success";
     }
     @RequestMapping(value = "select", method = RequestMethod.GET)
    @ResponseBody public Document selectcase(HttpServletRequest request) {
 
        
        //按caseid查找

       
        String caseid = request.getParameter("caseid");

        //连接数据库
        MongoDatabase db = MongoUtils.getDatabase();  
        System.out.println("Connect to mongodb database successfully");  


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

        //按id删除一条案件信息
        MongoCollection<Document> collection = db.getCollection("id");
        collection.deleteOne(Filters.eq("id", id));
        return "success";

    }
    @RequestMapping(value = "showlawyerown", method = RequestMethod.GET)
        @ResponseBody public List showlawyerown(HttpServletRequest request) {
     
            
            //律师登录返回承接律师是自己的案件信息
    
            //获取请求的参数
            String userid = request.getParameter("userid");
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            
    
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
     
            //管理员登录返回所有的案件信息
    
       
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            
    
            //连接collection
            MongoCollection<Document> collection = db.getCollection("case");
             System.out.println("集合 case 连接成功");
     
            
    
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
     
        
            //返回已存在的案件id和律师id
    
    
            //连接数据库
            MongoDatabase db = MongoUtils.getDatabase();  
            System.out.println("Connect to mongodb database successfully");  
    
            
    
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