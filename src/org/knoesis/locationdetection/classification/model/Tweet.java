package org.knoesis.locationdetection.classification.model;

public class Tweet {

			String tweet;
			String Class;
			
			public Tweet(String tweet, String Class) {
					this.tweet = tweet;
					this.Class = Class;
			}
			
			public String getTweet() {
					return this.tweet;
			}
			
			public String getClassName() {
					return this.Class;
			}
}
