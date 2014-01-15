package org.knoesis.locationdetection.preprocessing.test;

import org.knoesis.locationdetection.classification.util.CreateClassifier;
import org.knoesis.locationdetection.classification.util.RunClassifier;
import org.knoesis.locationdetection.preprocessing.downloaddata.DownloadTweets;

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
			//GetMetaData.updateTweets();
		
			/**
			 * Classify the tweets
			 */
//			try {
//					CreateClassifier.createNBClassifier();
//					RunClassifier.runClassifier();
//			} catch (Exception e) {
//					e.printStackTrace();
//			}
		
			/**
			 * Download tweets using twitter4j
			 */
			DownloadTweets.downloadTweets(19.08176, 72.87538);
	}
}
