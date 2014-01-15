package org.knoesis.locationdetection.preprocessing.downloaddata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.knoesis.locationdetection.preprocessing.data.DataManager;
import org.knoesis.locationdetection.preprocessing.model.Tweet;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Download tweets using twitter4j
 * @author revathy
 *
 */
public class DownloadTweets {

			static Logger log = Logger.getLogger(DownloadTweets.class.getName());
			private static void callAPI(double latitude, double longitude) {
			
				 	Twitter twitter = new TwitterFactory().getInstance();
				    
				    try {
				    		String queryString = "-RT";
				    		Query query = new Query(queryString);			        
				    		GeoLocation city = new GeoLocation(latitude, longitude);
					        query.geoCode(city, 20, "mi");
					        //query.count(10);
					        QueryResult result;
					        List<Tweet> tweetsToInsert = new ArrayList<Tweet>();
					        do {
					            result = twitter.search(query);
					            List<Status> tweets = result.getTweets();					            
					            Date loadingDate = new Date();
					            for (Status tweet : tweets) {
					            			
					            			String tweetID = String.valueOf(tweet.getId());
					            			String tweetText = tweet.getText();
					            			User user = tweet.getUser();
					            			String userId = String.valueOf(user.getId());
					            			Date date = user.getCreatedAt();
					            			String userName =  user.getName();
					            			String screenName = user.getScreenName();
					            			String location = user.getLocation();
					            			boolean geoEnabled = user.isGeoEnabled();			            		
					            			Tweet t = new Tweet(tweetID, date, loadingDate, tweetText, userId, userName, screenName, location, geoEnabled);			            			
					            			tweetsToInsert.add(t);
					            			
					            }
					            
					            DataManager dm = new DataManager();
					            dm.insertTweets(tweetsToInsert);
					            tweetsToInsert.clear();
					            
					        } while ((query = result.nextQuery()) != null);
					     
				    } catch (TwitterException te) {
					        te.printStackTrace();
					        System.out.println("Failed to search tweets: " + te.getMessage());
						            System.exit(-1);
						    }			    
				}
			
			
			public static void downloadTweets(double latitude, double longitude) {
					int count = 0;
					System.out.println("Starting the download of tweets for " + latitude + "," + longitude);
					while(count < 100) {
							callAPI(latitude, longitude);
							count++;							
							try {
								Thread.sleep(1000000);
							} catch (Exception e) {
								e.printStackTrace();
							}
					}
			}
			
}
