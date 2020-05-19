//package com.ibm.nutritium.pf.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//
//@Configuration
//public class AppConfig {
//
//    
//
//    private MongoClient mongoClient;
//
//    @Bean
//    public MongoClient mongoClient()
//    {
//
//        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://cc_exception_dev:nutritium@physical-features-dev-kujot.mongodb.net/test?retryWrites=true&w=majority");
//        if (this.mongoClient == null) {
//            this.mongoClient =  new MongoClient(connectionString);
//        }
//
//        return mongoClient;
//    }
// }
