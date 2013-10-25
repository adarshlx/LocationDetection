package org.knoesis.locationdetection.preprocessing.test;

/**
 * Invoke all the methods from this class
 * @author revathy
 *
 */
public class MainProcess {
	
	public static void main(String[] args) {	
			/**
			 * Write the JSON Output to Disk
			 */
			//GetMetaData.writeLocationToDisk();
			
			/**
			 * Read the city and state information from the disk
			 */
			GetMetaData.updateTweets();
			
	}
}
