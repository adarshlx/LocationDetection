package org.knoesis.locationdetection.preprocessing.config;

public class Config {
			/* Database configurations
			 * */
			public static final String DRIVER = "com.mysql.jdbc.Driver";
			public static final String DB_CONNECTION = "jdbc:mysql://twarql.knoesis.org:3306/twitris?user=root&password=admin";
			//public static final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:3306/twitris?user=root&password=admin";
		
			public static final String PROFILE_DIRECTORY = "profile/";
			public static final String MODI_EVENT = "narendramodi";
			
			public static final int BATCH_SIZE = 50000;
			
			/**
			 * Location on the disk where JSON output from Bing is copied			  
			 */
			public static final String JSON_OUTPUT_DIR = "jsonoutput/";
}
