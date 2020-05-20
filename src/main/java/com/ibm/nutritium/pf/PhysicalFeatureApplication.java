package com.ibm.nutritium.pf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@SpringBootApplication
public class PhysicalFeatureApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhysicalFeatureApplication.class, args);
	}
    
	@Bean
	public MongoClient getMongoClient() {
     MongoClientURI uri = new MongoClientURI(
     "mongodb+srv://cc-exception:noob%401234@cluster0-cpm7z.azure.mongodb.net/physical-features?retryWrites=false&w=majority");

     MongoClient mongoClient = new MongoClient(uri);
     return mongoClient;

	}
	
}
