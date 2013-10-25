package org.knoesis.locationdetection.preprocessing.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.knoesis.locationdetection.preprocessing.config.Config;
import org.knoesis.locationdetection.preprocessing.data.DataManager;
import org.knoesis.locationdetection.preprocessing.model.LatLong;
import org.knoesis.locationdetection.preprocessing.model.Location;
import org.knoesis.locationdetection.preprocessing.model.Tweet;
import org.knoesis.locationdetection.preprocessing.reversegeocoding.BingRG;
import org.knoesis.locationdetection.preprocessing.reversegeocoding.GoogleRG;
import org.knoesis.locationdetection.preprocessing.reversegeocoding.JSONReader;
import org.knoesis.locationdetection.preprocessing.reversegeocoding.ReverseGeocoding;

public class GetMetaData {
	
	/**
	 * Create a csv file for the distinct latitude longitude whose location needs to be determined
	 * The format of the file should be TWITTER_ID, LATITUDE, LONGITUDE
	 */
	static Logger log = Logger.getLogger(GetMetaData.class.getName());
	static MultiKeyMap addressMap = new MultiKeyMap();

	
	public static void getDistinctLatLong() {
			File f = new File("data/LatLong.csv");
			FileWriter fw = null;
			try {
					fw = new FileWriter(f);
					fw.write("\"UNIQUEID\",\"Latitude\",\"Longitude\"");
					DataManager dm = new DataManager();
					List<LatLong> latlong = dm.getLatLong();
					int count = 1;
					for(LatLong ll: latlong) {
							String str = "\n" + count++ + "," + ll.getLatitude() + "," + ll.getLongitude();
							fw.write(str);
					}
					
			} catch(Exception e) {
					log.error("Error in creating csv file", e);
			} finally {
					try {
						fw.close();
					} catch (Exception e) {
						log.error("Error in creating csv file",e);
					}
			}
			
	}
	
	/**
	 * Write Location Information from Bing (or other API) to disk
	 */
	public static void writeLocationToDisk () {
			File f = new File("data/LatLong.csv");			
			BufferedReader br = null;			
			
			ReverseGeocoding brg = new BingRG();
			ReverseGeocoding grg = new GoogleRG();
			try {
					br = new BufferedReader(new FileReader(f));
					String line = null;
					while((line=br.readLine()) != null) {
							String[] fields = line.split(",");					
							double latitude = Double.parseDouble(fields[1]);
							double longitude = Double.parseDouble(fields[2]);						
							String locationInfo = brg.getLocationInfo(latitude, longitude);
							
							/**
							 * If Bing API cannot give us the correct result then call google api
							 */
							if(!JSONReader.verifyBingOutputStatus(locationInfo)) {
									locationInfo = grg.getLocationInfo(latitude, longitude);
									if(JSONReader.verifyGoogleOutputStatus(locationInfo))
										JSONReader.saveRGOutputToDisk("Google", locationInfo, latitude, longitude);
							}	else {
									JSONReader.saveRGOutputToDisk("Bing", locationInfo, latitude, longitude);
							}
														
					}
			} catch (Exception e) {
					log.error("Error in reading csv file", e);
			} finally {
					try {
						br.close();					
					} catch (Exception e) {
						log.error("Error in closing filewriter");
					}
			}
	}
	
	
	/**
	 * Update tweets with city and state information
	 */
	public static void updateTweets() {
		
			readLocationInfo();
	
			DataManager dm = new DataManager();
			List<String> tweetIDs = dm.getTweetIDFromMetaData();
			
			List<String> tweetIDsTrace = new ArrayList<String>(tweetIDs);
			
			String subsetTweetIDs="";
			int count = 0;
			for(String tweetID : tweetIDs) {		
					tweetIDsTrace.remove(tweetID);
					subsetTweetIDs += ",'" + tweetID + "'";
					count++;						
					if(count == Config.BATCH_SIZE) {
							subsetTweetIDs = subsetTweetIDs.replaceFirst(",","");								
							getNewInfo(subsetTweetIDs);	
							subsetTweetIDs = "";
							count = 0;
					}
			}
			
			if(!subsetTweetIDs.isEmpty()) {
					subsetTweetIDs = subsetTweetIDs.replaceFirst(",","");
					getNewInfo(subsetTweetIDs);					
			}
	}
	
	/**
	 * Get city and state information for a tweet
	 */
	public static void getNewInfo(String subsetTweetIDs) {	
			
			DataManager dm = new DataManager();
			List<Tweet> tweets = dm.getLatLongForTwitterID(subsetTweetIDs);
					
			for(Tweet t : tweets) {
					
					double latitude = t.getLatitude();
					double longitude = t.getLongitude();
					Location loc = (Location)addressMap.get(latitude, longitude);		
					if(loc != null) {
							t.setCity(loc.getCity());
							t.setState(loc.getState());
					}
						
			}
			
			dm = new DataManager();
			dm.updateTwitterDataMetaData(tweets);
	}
	
	
	/**
	 * Create a map for the location information
	 */
	public static void readLocationInfo() {
			File file = new File(Config.JSON_OUTPUT_DIR);
			File[] allFiles = file.listFiles();
			addressMap = new MultiKeyMap();
						
			for(File f : allFiles) {
					String line = null;
					String content = "";
					Location loc = null;
					BufferedReader br = null;
					try {
							br = new BufferedReader(new FileReader(f));
							while((line=br.readLine())!=null) {
									if(line!=null)
										content += line;								
							}
							
							String[] fileName = f.getName().split("_");
							if(fileName[0].equals("Bing"))
								loc = JSONReader.getBingInfo(content,f.getName());
							else if(fileName[0].equals("Google"))
								loc = JSONReader.getGoogleLocationInfo(content);							
							
							addressMap.put(Double.parseDouble(fileName[1]),Double.parseDouble(fileName[2].replace(".txt","")),loc);
					} catch (Exception e) {
							log.error("Error in reading json output from disk", e);
					} finally {
							try {
								br.close();
							} catch (Exception e) {
								log.error("Error in closing file",e);
							}
					}	
			}			
			
	}
}
