package org.knoesis.locationdetection.preprocessing.language;

import org.apache.log4j.Logger;
import org.knoesis.locationdetection.preprocessing.config.Config;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;

/**
 * Detect language of a given text using "language-detection" library
 * Reference: https://code.google.com/p/language-detection/downloads/list
 * @author revathy
 *
 */
public class DetectLanguage {
			
			static Logger log = Logger.getLogger(DetectLanguage.class.getName());
			
			/**
			 * Load the profile
			 */
			public DetectLanguage() {
					try {
						DetectorFactory.loadProfile(Config.PROFILE_DIRECTORY);
					} catch (Exception e) {
						log.error("Error in loading Language Detection Profiles", e);
					}
			}
			
			/**
			 * Return the language of the string
			 * @param text
			 * @return
			 */
			public String detect(String text) {
					String language = "";
					try {							
							Detector detector = DetectorFactory.create();
							detector.append(text);
							language = detector.detect();
							System.out.println("Language detected: " + language);
					} catch (Exception e) {
							log.error("Error in translating text",e);
					}
					return language;
		    }
}