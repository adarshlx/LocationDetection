package org.knoesis.locationdetection.preprocessing.model;

public class Tweet {

			String twitter_ID;
			String tweet;
			double latitude;
			double longitude;
			String country;
			String language;
			String city;
			String state;
			
								
			public Tweet(String twitter_ID, String tweet, double latitude, double longitude) {
					this.twitter_ID = twitter_ID;
					this.tweet = tweet;
					this.latitude = latitude;
					this.longitude = longitude;
					this.country = "";
					this.language = "";
					this.city = "";
					this.state = "";					
			}
			
			public String getTwitterID() {
					return this.twitter_ID;
			}
			
			public String getTweet() {
					return this.tweet;
			}
			
			public double getLatitude() {
					return this.latitude;
			}
			
			public double getLongitude() {
					return this.longitude;							
			}
			
			public void setCountry(String country) {
					this.country = country;
			}
			
			public String getCountry() {
					return this.country;
			}
			
			public void setLanguage(String language) {
					this.language = language;
			}	
			
			public String getLanguage() {
					return this.language;
			}
			
			public void setState(String state) {
					this.state = state;
			}
			
			public String getState() {
					return this.state;
			}
			
			public void setCity(String city) {
					this.city = city;
			}
			
			public String getCity() {
					return this.city;
			}
}