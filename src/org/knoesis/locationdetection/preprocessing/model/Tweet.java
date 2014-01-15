package org.knoesis.locationdetection.preprocessing.model;

import java.util.Date;

public class Tweet {

			String twitter_ID = "";
			String tweet = "";
			double latitude = 0;
			double longitude = 0;
			String country = "";
			String language = "";
			String city = "";
			String state = "";
			
			Date createdDate = null;
			Date loadingDate = null;
			String screenName = "";
			String userName = "";
			/**
			 * Location - information from the user profile. Country, city, state are derived from the latitude and longitude
			 */
			String location ="";
			boolean geoEnabled = false;
			String userId = "";
								
			public Tweet(String twitter_ID, String tweet, double latitude, double longitude) {
					this.twitter_ID = twitter_ID;
					this.tweet = tweet;
					this.latitude = latitude;
					this.longitude = longitude;						
			}
			
			public Tweet(String twitter_ID, Date createdDate, Date loadingDate, String tweet, String userId, 
					String userName, String screenName, String location, boolean geoEnabled) {
					this.twitter_ID = twitter_ID;
					this.createdDate = createdDate;
					this.loadingDate = loadingDate;
					this.tweet = tweet;
					this.userId = userId;
					this.userName = userName;
					this.screenName = screenName;
					this.location = location;
					this.geoEnabled = geoEnabled;
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
			
			public Date getTweetCreatedDate() {
				return this.createdDate;
			}
			
			public Date getLoadingDate() {
					return this.loadingDate;
			}
			
			public String getUserId() {
				return this.userId;
			}
			
			public String getUserName() {
					return this.userName;
			}
			
			public String getScreenName() {
					return this.screenName;
			}
			
			public String getLocation() {
					return this.location;
			}
			
			public boolean getGeoEnabledFlag() {
					return this.geoEnabled;
			}

}