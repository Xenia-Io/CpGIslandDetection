/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import gr.demokritos.iit.cpgislanddetection.analysis.VectorPreProcessor;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorSequenceDetector;
import java.text.ParseException;
import weka.core.Instances;

/**
 *
 * @author Xenia
 */
public class VClassifier {
    
    public static void classify() throws ParseException, Exception{
    
        String filePathForPositivesamples = "data/posSamples.txt";
        String filePathForNegativeSamples = "data/negSamples.txt";

        VectorPreProcessor vectorPreProcessor = new VectorPreProcessor();
        Instances trainingInstances = vectorPreProcessor.createTrainingInstances(filePathForPositivesamples, filePathForNegativeSamples);

        VectorSequenceDetector vectorSequenceDetector = new VectorSequenceDetector();

//    NaiveBayes nb = vectorSequenceDetector.trainClassifier(trainingInstances);  
//    List<Vector<Integer>> listVectorForTestingSamples = vectorPreProcessor.createTestingSet(
//            "./newsamples.txt");
        // vectorSequenceDetector.classify(trainingInstances, nb);
        vectorSequenceDetector.crossValidate(trainingInstances);
    }
    
}
