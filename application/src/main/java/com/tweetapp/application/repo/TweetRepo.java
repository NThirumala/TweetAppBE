package com.tweetapp.application.repo;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.application.model.Tweet;

public interface TweetRepo extends MongoRepository<Tweet, Long>{
	List<Tweet> findByEmail(String email);
//	Tweet save(Stweet tweet);
	Tweet findById(String id);
	void deleteById(String id);
}
