package org.knoesis.locationdetection.classification.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.knoesis.locationdetection.classification.config.Config;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * Contains functions common to all weka classifiers
 * @author revathy
 */
public class WekaOperations {

			/**
			 * Filter input file using StringToWordVector Filter
			 * @param Instances
			 * @throws Exception
			 */
			public static Instances applyStringToWordFilter(Instances dataFileInstance) throws Exception {
					
						StringToWordVector stringtoword = new StringToWordVector();
						Standardize filter = new Standardize();
						filter.setInputFormat(dataFileInstance);		
						stringtoword.setOptions(Config.filteredClassifierOptions);
						stringtoword.setInputFormat(dataFileInstance);
						Instances newDataFile = Filter.useFilter(dataFileInstance, stringtoword);
						return newDataFile;
				
			}
			
			
			/**
			 * read input arff file
			 * @param fileName - ARFF file
			 * @return Instances 
			 * @throws IOException
			 */
			public static Instances readArffFile(File file) throws FileNotFoundException, IOException {
						BufferedReader reader = new BufferedReader(new FileReader(file));
						Instances data = new Instances(reader);
						reader.close();		 
						// setting the last column as class attribute 
						data.setClassIndex(data.numAttributes() - 1);		
						return data;
			}
			

			/**
			 * Read the labeled ARFF file and convert it to CSV 
			 * @throws Exception
			 * @param arffFile  - input file 
			 * @param csvFile - file where the csv output is saved
			 */
			public static void convertArffToCsv(String arffFile, String csvFile) throws Exception {
						
						String[] options = new String[6];
						options[0] = "-F";
						options[1] = ";";
						options[2] = "-i";
						options[3] = arffFile;
						options[4] = "-o";
						options[5] = csvFile;		
						CSVSaver.main(options);		
			}

			
}
