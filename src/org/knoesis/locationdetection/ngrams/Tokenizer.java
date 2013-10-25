package org.knoesis.locationdetection.ngrams;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Temporary class while we're cleaning up the code.
 * Breaks textual content from tweet into a list of tokens
 *  
 *  
 * @author PabloMendes
 * @author pkapani
 *
 *	TODO: The tokenizing should be done in a better way 
 *	1. Remove all the stop words
 *	2. Remove the URLs from the tweet
 *	3. Check if the hashtags are a necessary component to be spotted 
 *
 */
public class Tokenizer {

	public static String PUNCTUATION = "[\"'()<>{}\\[\\]:;,.!?\\\\/&%$*#@\\+\\-=\\s]+";
	
	//TODO This is duplicate code. 
	// The same appears in URLExtractor. 
	// formerly checkurls
	public static String cleanurls(String tweet){
		Pattern p = Pattern.compile("((http|https|ftp)://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[~#&\\n\\-=?\\+\\%/\\.\\w]+)?");
		Matcher m = p.matcher(tweet);
		while (m.find()) {
			String href = m.group();
			tweet = tweet.replace(href, "");
		}
		return tweet;
		
	}
	/**
	 * Cleans the stopwords from the tweet content to be checked
	 * @param tweet
	 * @return
	 */
	public static String[] cleanStopWords(String[] tweet){
		StopWords stopWords = StopWords.getInstance();
		List<String> tweetContents = Arrays.asList(tweet);
		for(String content: tweetContents){
			if(stopWords.isStopword(content))
				tweetContents.remove(content);
		}
		tweet = new String[tweetContents.size()];
		tweetContents.toArray(tweet);
		return tweet;
	}
	/**
	 * Tokenizes the tweetcontent excluding 
	 * 1. Urls 
	 * 2. Stopwords
	 * 
	 * @param tweetContent
	 * @return
	 */
	public static String[] tokenize(String tweetContent) {
		tweetContent = cleanurls(tweetContent);
		tweetContent = " "+tweetContent+" ";
		return tweetContent.split(PUNCTUATION);
	}
	
	public static String[] tokenizeIgnoreURLCleaning(String tweetContent){
		return tweetContent.split(PUNCTUATION);
	}
}
