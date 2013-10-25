package org.knoesis.locationdetection.preprocessing.reversegeocoding;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.knoesis.locationdetection.preprocessing.config.Config;
import org.knoesis.locationdetection.preprocessing.model.Location;


/**
 * Parse JSON output of geocoding APIs
 * @author revathy 
 */

public class JSONReader {
	
			static Logger log = Logger.getLogger(JSONReader.class.getName());
			
			/**
			 * Get the country name from the json output of Bing API
			 * @param content
			 * @return countryName
			 */
			public static Location getBingInfo(String content,String fileName) {
						String countryName = "";
						String cityName = "";
						String stateName = "";
						String postalCode = "";
						Location loc = null;
						try {								
								JSONObject results = new JSONObject(content.toString());
								JSONArray arrResults = (JSONArray)results.get("resourceSets");					
								JSONObject components = new JSONObject(arrResults.get(0).toString());
								JSONArray resourceArray = (JSONArray)components.get("resources");					
								JSONObject type = new JSONObject(resourceArray.get(0).toString());							
								
								if(type.has("address")) {
										JSONObject addr = new JSONObject(type.get("address").toString());
										if(addr.has("countryRegion"))
											countryName = addr.get("countryRegion").toString();
										if(addr.has("adminDistrict2"))
											cityName = addr.getString("adminDistrict2").toString();
										if(addr.has("adminDistrict"))
											stateName = addr.getString("adminDistrict").toString();
										if(addr.has("postalCode"))
											postalCode = addr.getString("postalCode").toString();
									
										loc = new Location(countryName, stateName, cityName, postalCode);
								}
												
						} catch (Exception e) {
								log.error("Error in reading JSON output from Bing from fileName: " + fileName, e);
						}
						return loc;
			}		
			
			/**
			 * Verify if the Bing output is empty or contains the location information
			 */
			public static boolean verifyBingOutputStatus(String content) {
						try {
								JSONObject results = new JSONObject(content.toString());
								JSONArray arrResults = (JSONArray)results.get("resourceSets");
								JSONObject components = new JSONObject(arrResults.get(0).toString());
								if(components.get("estimatedTotal").toString().equals("0")) 
									return false;
								
						} catch (Exception e) {
								log.error("Error in verifying Bing output", e);
						}
						
						return true;
						
			}
			
			/**
			 * Verify if the Google output is empty or contains the location information
			 */
			public static boolean verifyGoogleOutputStatus(String content) {
					try {
							JSONObject results = new JSONObject(content.toString());
							JSONArray arrResults = (JSONArray)results.get("results");					
							if(arrResults.length() != 0) 															
								return true;
					} catch (Exception e) {
							log.error("Error in verifying google output", e);
					}
					return false;
			}
			
			/**
			 * Save the output of Bing (or other API) to the disk
			 */
			public static void saveRGOutputToDisk(String apiName, String content, double latitude, double longitude) {
						String fileName = apiName + "_" + latitude + "_" + longitude + ".txt";
						
						try {
								BufferedWriter writer = new BufferedWriter(new FileWriter(Config.JSON_OUTPUT_DIR + fileName));
								writer.write(content);
								writer.flush();
								writer.close();
								log.info("Output of lat: " + latitude + " and long: " + longitude + " written to: " + content);
						} catch (Exception e) {
								log.error("Error in writing json output to disk for lat: " + latitude + " and Long: " + longitude);
					}
			}			
			
			/**
			 * Get country name from google's json output
			 */
			public static Location getGoogleLocationInfo(String content) {
						String countryName = "";
						String cityName = "";
						String stateName = "";
						String postalCode = "";
						Location loc = null;
						try {								
								JSONObject results = new JSONObject(content.toString());
								JSONArray arrResults = (JSONArray)results.get("results");								
								
								/**
								 * For certain invalid latitudes, the url returns empty JSON object
								 * Example: tweetID: 314683949249077249 latitude: -157.921
								 */
								if(arrResults.length() != 0) {
										JSONObject addrComponents = new JSONObject(arrResults.get(0).toString());
										JSONArray addrArray = (JSONArray)addrComponents.get("address_components");							
										countryName = loopThroughAddressComponents(addrArray,"country");
										cityName = loopThroughAddressComponents(addrArray, "locality");
										stateName = loopThroughAddressComponents(addrArray, "administrative_area_level_1");
										postalCode = loopThroughAddressComponents(addrArray, "postal_code");
										loc = new Location(countryName, stateName, cityName, postalCode);
								}
							   							
						} catch (Exception e) {
								log.error("Error in getting country name for : " + content, e);
						}
						
						return loc; 
			}
			
			/**
			 * Loop through the variable length array "address_components" of the Google API's json output 
			 * @param addrArray
			 * @return
			 */
			private static String loopThroughAddressComponents(JSONArray addrArray, String type) {						
				int length = addrArray.length();
				String countryName = "";
				
				try {
						for(int i=0; i<length; i++) {
							JSONObject eachComponent = new JSONObject(addrArray.get(i).toString());									
							JSONArray propertyArray = (JSONArray)eachComponent.get("types");
							if((propertyArray.length() != 0) && (propertyArray.get(0).toString().equalsIgnoreCase(type))) {
									countryName = eachComponent.get("long_name").toString();
									return countryName;
							}
						}
				} catch (Exception e) {
						log.error("Error in looping through address for: " + addrArray.toString(),e);
				}
				return countryName;
			}
}
