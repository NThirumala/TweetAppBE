package com.tweetapp.application.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.application.model.Tweet;
import com.tweetapp.application.model.User;
import com.tweetapp.application.service.TweetService;

@RestController
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	
	@GetMapping("/tweet/all")
	public ResponseEntity<List<Tweet>> getAllTweets(){
		return ResponseEntity.ok().body(tweetService.getAllTweets());
	}
	
	@GetMapping("/tweet/user")
	public ResponseEntity<List<Tweet>> getUserTweets(@RequestBody User user){
		return ResponseEntity.ok().body(tweetService.getUserTweets(user.getEmail()));
	}
	
	@PostMapping("/tweet/add")
	public ResponseEntity <Tweet> postTweet(@RequestBody Tweet tweet){
		System.out.println("reacged add tweet");
		System.out.println(tweet);
		return ResponseEntity.ok().body(tweetService.postTweet(tweet));
	}
	
	@PostMapping("/tweet/update")
	public ResponseEntity<?> updateTweet(@RequestBody Tweet tweet, Principal principal){
		String loggedInUser = principal.getName();
		if(loggedInUser.equalsIgnoreCase(tweet.getEmail())){
			return ResponseEntity.ok().body(tweetService.updateTweet(tweet));
		}else {
			Map<String , String> error = new HashMap<>();
			error.put("StatusCode", "2");
			error.put("errorMsg" , "User not allowed to modify another user tweet message");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}
	}
	
	@DeleteMapping("/tweet/delete")
	public void deleteTweet(@RequestBody Tweet tweet){
		tweetService.deleteTweet(tweet);
	}
	
	@GetMapping("/tweet/like")
	public ResponseEntity <Tweet> likeTweet(@RequestHeader(value="id", required= false) String id ){
		System.out.println(id);
		return ResponseEntity.ok().body(tweetService.likeTweet(id));
	}
	
	@PostMapping("/tweet/reply")
	public ResponseEntity <Tweet> replyTweet(@RequestBody ReTweet reTweet){
		Tweet tweet = reTweet.getRetweet();
		String parentTweetId = reTweet.getParentTweetId();
		System.out.println(tweet + " " + parentTweetId);
		return ResponseEntity.ok().body(tweetService.replyTweet(tweet, parentTweetId));
	}
	
}

class ReTweet {
	private Tweet retweet;
	private String parentTweetId;
	public Tweet getRetweet() {
		return retweet;
	}
	public void setRetweet(Tweet retweet) {
		this.retweet = retweet;
	}
	public String getParentTweetId() {
		return parentTweetId;
	}
	public void setParentTweetId(String parentTweetId) {
		this.parentTweetId = parentTweetId;
	}
	
}
