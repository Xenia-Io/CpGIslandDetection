/* 
 * Copyright 2016 NCSR Demokritos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import gr.demokritos.iit.cpgislanddetection.io.FileCreatorARFF;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
//import static gr.demokritos.iit.jinsect.algorithms.statistics.ChiSquareDistributionBase.data;
import java.util.List;
import java.util.Vector;
import weka.classifiers.*;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Xenia
 */
public class VectorSequenceDetector implements ICpGSequenceDetector {

    // Initialize classifier
    // Train classifier
    public NaiveBayes trainClassifier(Instances trainingInstances) throws ParseException, FileNotFoundException, IOException, Exception {
        //setting class attribute
        trainingInstances.setClassIndex(trainingInstances.numAttributes() - 1);

        //initialize, build and train classifier
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(trainingInstances);
        
        return nb;
    }
    
    public void crossValidate(Instances allInstances) throws ParseException, Exception {

        // Test the model
        Evaluation eTest = new Evaluation(allInstances);
        eTest.crossValidateModel(new NaiveBayes(), allInstances, 10, new Random(1));
        
        System.out.println(eTest);
        System.out.println(eTest.toSummaryString("\nResults\n=======\n", false));

    }

    //test classifier and evaluate the model
    public void classify(
            Instances testInstances,
            NaiveBayes nb) throws ParseException, Exception {

//        VectorPreProcessor vectorPreProcessor = new VectorPreProcessor();
//        List<Vector<Integer>> listVectorForTestingSamples = vectorPreProcessor.createTestingSet(
//                "./newsamples.txt");

        // Test the model
        Evaluation eTest = new Evaluation(testInstances);
        eTest.evaluateModel(nb, testInstances);
        
        FileCreatorARFF fc = new FileCreatorARFF();
//        Instances isTestingSet = fc.createARFFforTest(listVectorForTestingSamples, "no");
//        eTest.evaluateModel(nb, testInstances);

        System.out.println(eTest);
        System.out.println(eTest.toSummaryString("\nResults\n======\n", false));

    }

    // Classify one sequence using your existing classifier
    // return true if the label indicates a CpG
    @Override
    public boolean detect(BaseSequence seq) {

        return true;
    }

}
