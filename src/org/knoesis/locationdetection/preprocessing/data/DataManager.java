package org.knoesis.locationdetection.preprocessing.data;

import org.apache.log4j.Logger;
import org.knoesis.locationdetection.preprocessing.config.Config;
import org.knoesis.locationdetection.preprocessing.model.LatLong;
import org.knoesis.locationdetection.preprocessing.model.Tweet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
		private Connection connection = null;	
	
		static Logger log = Logger.getLogger(DataManager.class.getName());
		public DataManager() {
				try {
					Class.forName(Config.DRIVER);
					connection = DriverManager.getConnection(Config.DB_CONNECTION);		
					log.info("Opening db connection");
				} catch(Exception e) {
					log.error("Error in intializing database: ",e);
				}
		}
		
		public List<Tweet> getLatLongForTwitterID(String twitterIDs) {
				String sql = "SELECT twitter_ID, latitude, longitude  from twitterdata WHERE twitter_ID IN (" + twitterIDs + ") AND eventID like 'NarendraModi'";
				Statement stmt = null;
				List<Tweet> tweets = new ArrayList<Tweet>();
				try {
						stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
								double latitude = rs.getDouble("latitude");
								double longitude = rs.getDouble("longitude");
								String twitterID = rs.getString("twitter_ID");
								Tweet t = new Tweet(twitterID, "",latitude, longitude);
								tweets.add(t);
						}
				} catch (Exception e) {
						log.error("Error in SELECT from twitris.twitterdata");
				} finally {
						cleanUpDatabase(stmt);
				}
				
				return tweets;
		}
		
		/**
		 * Get Lat Long
		 */
		public List<LatLong> getLatLong() {
				String sql = "SELECT DISTINCT latitude, longitude from twitterdata WHERE latitude < 90 AND latitude > -90 " +
						"AND longitude < 180 AND longitude > -180 AND latitude <> 10000 AND longitude <> 10000";
				Statement stmt = null;
				List<LatLong> latlong = new ArrayList<LatLong>();
				try {
						stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {								
								double latitude = rs.getDouble("latitude");
								double longitude = rs.getDouble("longitude");
								latlong.add(new LatLong(latitude, longitude));								
						}
				} catch (Exception e) {
						e.printStackTrace();
				} finally {
						cleanUpDatabase(stmt);
				}
				return latlong;
		}
		
		
		/**
		 * Select distinct twitterIDs from database
		 */
		public List<String> getTweetIDs() {
				String sql = "SELECT twitter_ID from twitterdata";
				Statement stmt = null;
				List<String> twitterIDs = new ArrayList<String>();
				try {
					stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()) {
							twitterIDs.add(rs.getString("twitter_ID"));							
					}
				} catch (Exception e) {
					log.error("Error in selecting tweetIDs");
				} finally {
						cleanUpDatabase(stmt);
				}
				
				return twitterIDs;
		}
		
		/**
		 * Select distinct twitterIDs from database
		 */
		public List<String> getTweetIDFromMetaData() {
				String sql = "SELECT twitter_ID from twitterdata_metadata";
				Statement stmt = null;
				List<String> twitterIDs = new ArrayList<String>();
				try {
						stmt = connection.createStatement();
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
								twitterIDs.add(rs.getString("twitter_ID"));
						}
				} catch (Exception e) {
						log.error("Error in selecting tweetIDs");
				} finally {
						cleanUpDatabase(stmt);
				}
				return twitterIDs;
		}
		
		/**
		 * Update twitterdata_metadata database
		 */
		public void updateTwitterDataMetaData(List<Tweet> tweets) {
				String sql = "UPDATE twitterdata_metadata SET city = ?, state = ? WHERE twitter_ID = ? AND eventID like 'narendramodi'";
				PreparedStatement ps = null;
				
				try {
						connection.setAutoCommit(false);
						ps = connection.prepareStatement(sql);
						
						for(Tweet t : tweets) {
								ps.setString(1, t.getCity());
								ps.setString(2, t.getState());
								ps.setString(3, t.getTwitterID());
								ps.addBatch();			
						}
						ps.executeBatch();
						
				} catch (Exception e) {
						log.error("Error in updating twitterdata_metadata", e);
				} finally {
						cleanUpDatabase(ps);
				}
		}
		
		/**
		 * Return all the latitude-longitudes from twitris.twitterdata
		 */
		public List<Tweet> getTweet(String twitterIDs) {
				
				String sql = "SELECT twitter_ID, latitude, longitude, tweet FROM twitterdata where eventID like 'narendramodi'" +
						" AND twitter_ID IN (" + twitterIDs + ")";							 
							
				Statement stmt = null;
				List<Tweet> tweets = new ArrayList<Tweet>();				
				try {
						stmt = connection.createStatement();						
						ResultSet rs = stmt.executeQuery(sql);						
						while(rs.next()) {
								String twitterID = rs.getString("twitter_ID");
								double latitude = rs.getDouble("latitude");
								double longitude = rs.getDouble("longitude");
								String text = rs.getString("tweet");
								tweets.add(new Tweet(twitterID, text, latitude, longitude));
						}						
						
				} catch (Exception e) {
						log.error("Error in selecting lat long info", e);
				} finally {
						cleanUpDatabase(stmt);
				}
				
				return tweets;
		}	
		
		
		public void insertTwitterMetaData(List<Tweet> tweets) {
				String sql = "INSERT INTO twitterdata_metadata(twitter_ID, eventID, country, language) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = null;
				try {
						connection.setAutoCommit(false);
						ps = connection.prepareStatement(sql);
						for(Tweet t : tweets) {
								ps.setString(1, t.getTwitterID());
								ps.setString(2, Config.MODI_EVENT);
								ps.setString(3, t.getCountry());
								ps.setString(4, t.getLanguage());
								ps.addBatch();								
						}
						ps.executeBatch();
				} catch (Exception e) {
						log.error("Error in inserting twitter metadata", e);
				} finally {
						cleanUpDatabase(ps);
				}
		}
		
		

		/**
		 * Insert the tweets downloaded from twitter
		 * @param tweets
		 */
		public void insertTweets(List<Tweet> tweets) {
			String sql = "INSERT IGNORE INTO tweets (tweet_id, created_date, tweet_text, user_id, user_name, screen_name, location, geo_enabled, loading_date) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = null;
			
			try {
					connection.setAutoCommit(false);
					ps = connection.prepareStatement(sql);
					
					System.out.println("Inserting " + tweets.size() + " tweets in the database");
					for(Tweet t : tweets) {
						 	java.sql.Date createdDate = new java.sql.Date(t.getTweetCreatedDate().getTime());
						 	java.sql.Date loadingDate = new java.sql.Date(t.getLoadingDate().getTime());
							ps.setString(1, t.getTwitterID());
							ps.setDate(2, createdDate);
							ps.setString(3, t.getTweet());
							ps.setString(4, t.getUserId());
							ps.setString(5, t.getUserName());
							ps.setString(6, t.getScreenName());
							ps.setString(7, t.getLocation());
							ps.setBoolean(8, t.getGeoEnabledFlag());
							ps.setDate(9, loadingDate);
							ps.addBatch();								
					}
					ps.executeBatch();
					
			} catch (Exception e) {
					e.printStackTrace();
			} finally {
					cleanUpDatabase(ps);
			}
		}
		
		public void cleanUpDatabase(Statement stmt) {			
				try {
					connection.close();
					stmt.close();
					log.info("Closing db connection");
				} catch (Exception e) {
					log.error("Error in cleaning up database after select", e);
				}			
		}
		
		
		public void cleanUpDatabase(PreparedStatement ps) {
				try {
						ps.close();
						connection.commit();
						connection.setAutoCommit(true);
						log.info("Closing db connection");
				} catch (Exception e) {
					log.error("Error in cleaning up database after update", e);
				}
		}
}
	