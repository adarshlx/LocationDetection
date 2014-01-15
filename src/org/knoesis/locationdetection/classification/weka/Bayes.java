package org.knoesis.locationdetection.classification.weka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;
import org.knoesis.locationdetection.classification.config.Config;
import org.knoesis.locationdetection.classification.util.WekaOperations;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 * use weka implementation of NaiveBayes
 * @author revathy
 */

public class Bayes implements Classifier {
			
			NaiveBayes bayesModel;
			FilteredClassifier filteredClassifier;
			StringToWordVector stringToWordVector;
			static Logger log = Logger.getLogger(Bayes.class.getName());
			
			
			public Bayes() {				
						bayesModel = new NaiveBayes();
						filteredClassifier = new FilteredClassifier();
						stringToWordVector = new StringToWordVector();						
			}
			
			/**
			 * @desc Classify unlabelled data
			 * @param testData: File containing the unlabeled data
			 *          labeledFile: Name of the file where the labeled data is to be saved
			 */
			public void classifyData(File testData, File labeledFile) throws IOException, Exception {				
						Instances unlabeledData = WekaOperations.readArffFile(testData);
						// create copy
						Instances labeledData = new Instances(unlabeledData);
						// label instances
						for (int i = 0; i < unlabeledData.numInstances(); i++) {
							double classLabel = filteredClassifier.classifyInstance(unlabeledData.instance(i));
							labeledData.instance(i).setClassValue(classLabel);
						}		
						// save labeled data
						BufferedWriter writer = new BufferedWriter(new FileWriter(labeledFile));
						writer.write(labeledData.toString());
						writer.newLine();
						writer.flush();
						writer.close();
						log.info("Unlabelled data at " + testData + " classified and saved at " + labeledFile);				
			}
			
			/**
			 * Create a StringToWordVector Filter
			 */
			private void createStringToWordVectorFilter() throws Exception {
						stringToWordVector.setOptions(Config.filteredClassifierOptions);				
			}
			
			/**
			 * Train a NaiveBayes Classifier
			 * @param trainingData: arff File containing the labeled training data 
			 */
			public void trainClassifier(File trainingData) throws Exception {		
						System.out.println("Training NB Classifier using " + trainingData.getName());
						Instances trainingDataInstance = WekaOperations.readArffFile(trainingData);
						createStringToWordVectorFilter();
						System.out.println("Applying filter");
						filteredClassifier.setFilter(stringToWordVector);						
						filteredClassifier.setClassifier(bayesModel);
						System.out.println("Building Classifier");
						filteredClassifier.buildClassifier(trainingDataInstance);	
						System.out.println("finished building classifier");
			}
			
			/**
			 * @desc Write the classifier to the disk
			 */
			public void writeClassifierToDisk() throws IOException {
						ObjectOutputStream outputStream = null;
						try {
								outputStream = new ObjectOutputStream(new FileOutputStream(Config.NB_CLASSIFIER_PATH));
								outputStream.writeObject(filteredClassifier);
						} catch(IOException e) {
								throw(new IOException());
						} finally {
								outputStream.flush();
								outputStream.close();								
						}
						log.info("Classifier written to disk at " + Config.NB_CLASSIFIER_PATH);
			}
			
			/**
			 * @desc Load a classifier from the disk
			 * @param classifier: The file where the classifier is stored on the disk
			 */
			public void loadClassifierFromDisk() throws Exception {
						log.info("Loading NB Classifier from disk");
						filteredClassifier = (FilteredClassifier) weka.core.SerializationHelper.read(Config.NB_CLASSIFIER_PATH);
			}
			
}
