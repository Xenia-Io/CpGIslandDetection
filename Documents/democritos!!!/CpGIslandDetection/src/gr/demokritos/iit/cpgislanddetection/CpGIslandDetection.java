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
package gr.demokritos.iit.cpgislanddetection;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.ISequenceAnalyst;
import gr.demokritos.iit.cpgislanddetection.analysis.ISequenceClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorPreProcessor;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorSequenceDetector;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import gr.demokritos.iit.cpgislanddetection.io.FileCreatorARFF;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import gr.demokritos.iit.jinsect.documentModel.representations.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Xenia
 */
public class CpGIslandDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, Exception {
    

    //-----------------VECTOR ANALYSIS STARTS HERE--------------------------------------
        
    String filePathForPositivesamples = "C:\\Users\\Xenia\\Documents\\democritos!!!\\CpGIslandDetection\\posSamples.txt";
    String filePathForNegativeSamples = "C:\\Users\\Xenia\\Documents\\democritos!!!\\CpGIslandDetection\\negSamples.txt";
    
    VectorPreProcessor vectorPreProcessor = new VectorPreProcessor();
    Instances trainingInstances = vectorPreProcessor.createTrainingInstances(filePathForPositivesamples, filePathForNegativeSamples);

    VectorSequenceDetector vectorSequenceDetector = new VectorSequenceDetector();
    
    NaiveBayes nb = vectorSequenceDetector.trainClassifier(trainingInstances);
    
    vectorSequenceDetector.classify(trainingInstances, nb);
        
    //------------------VECTOR ANALYSIS ENDS HERE---------------------------------------
        
        
        
        
        
   
        
        
        
        
        //----------------------------HMM CLASSIFIER STARTS HERE----------------------------------
     /*   
        // Init classifier
        ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();

        FASTAFileReader r2 = new FASTAFileReader();
        FASTAObfuscatorReader r3 = new FASTAObfuscatorReader();
        ArrayList<BaseSequence> baseS = r2.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\fasta\\test.txt");
        ArrayList<BaseSequence> baseSNonCpG = r3.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\fasta\\test.txt");

        System.out.println("baseS size=" + baseS.size());

        ArrayList<BaseSequence> baseS_test = r2.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\fasta\\testForPosCPG_Fasta.txt");
        ArrayList<BaseSequence> baseSNonCpG_test = r3.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\fasta\\testForPosCPG_Fasta.txt");
        //System.out.println("sizeOfPos = " + baseS.size() + " " + "sizeOfNeg = " + baseSNonCpG.size());

        //System.out.println("array of base sequence size = " + baseS.size());
        HmmAnalyzer v = new HmmAnalyzer();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lRes = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lResNon = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lRes_test = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lResNon_test = new ArrayList<>();
        lRes = v.analyze(baseS);
        lResNon = v.analyze(baseSNonCpG);
        lRes_test = v.analyze(baseS_test);
        lResNon_test = v.analyze(baseSNonCpG_test);

        System.out.println("size of baseS_test = " + lRes_test.size());
        System.out.println("size of baseSNonCpG_test = " + lResNon_test.size());
        //for(int i=0; i<baseS.size(); i++)
        //System.out.println(lRes.size());

        classifier.train(lRes, "isCpG");
        classifier.train(lResNon, "isNotCpG");

        ArrayList<HMMFeatureVector> cpgTrainingVectors = new ArrayList<>();
        ArrayList<HMMFeatureVector> notCpgTrainingVectors = new ArrayList<>();

        ArrayList<HMMFeatureVector> cpgTestingVectors = new ArrayList<>();
        ArrayList<HMMFeatureVector> notCpgTestingVectors = new ArrayList<>();

        // Getting the feature vectors for each of our sequence lists 
        for (List<ObservationDiscrete<HmmSequence.Packet>> CpGinstanceRepresentation : lRes) {
            cpgTrainingVectors.add((HMMFeatureVector) classifier.getFeatureVector(CpGinstanceRepresentation, "positive cpg"));
        }
        for (List<ObservationDiscrete<HmmSequence.Packet>> NonCpGinstanceRepresentation : lResNon) {
            notCpgTrainingVectors.add((HMMFeatureVector) classifier.getFeatureVector(NonCpGinstanceRepresentation, "negative cpg"));
        }
        for (List<ObservationDiscrete<HmmSequence.Packet>> CpGinstanceRepresentation : lRes_test) {
            cpgTestingVectors.add((HMMFeatureVector) classifier.getFeatureVector(CpGinstanceRepresentation, "positive cpg"));
        }
        for (List<ObservationDiscrete<HmmSequence.Packet>> NoNCpGinstanceRepresentation : lResNon_test) {
            notCpgTestingVectors.add((HMMFeatureVector) classifier.getFeatureVector(NoNCpGinstanceRepresentation, "negative cpg"));
        }

//         HMMFeatureVector fv = new HMMFeatureVector();
        for (int i = 0; i < lRes_test.size(); i++) {

            System.out.println("cpg testing vector = " + cpgTestingVectors.get(i).getProbArrayAtIndex(i));
            System.out.println("cpg testing vector label = " + cpgTestingVectors.get(i).getLabel());
        }
        for (int i = 0; i < lResNon_test.size(); i++) {

            System.out.println("NOTcpg testing vector = " + notCpgTestingVectors.get(i).getProbArrayAtIndex(i));
            System.out.println("NOTcpg testing vector label = " + notCpgTestingVectors.get(i).getLabel());
        }
        
        //-------------------------------HMM CLASSIFIER ENDS HERE-----------------------------------------

   
        
       
        
        
        //----------------------------HMM EVALUATION STARTS-----------------------------------------------
      /*
        CpGIslandIdentification i = new CpGIslandIdentification();

        //create variables to store successes and failures for each class
        int successForCpG = 0, failForCpG = 0, successForNotCpG = 0, failForNotCpG = 0;
        int size = baseS_test.size() + baseSNonCpG_test.size();
        boolean[] resultArrayForBaseS_test = new boolean[baseS_test.size()];
        boolean[] resultArrayForBaseSNonCpG_test = new boolean[baseSNonCpG_test.size()];

        for (int j = 0; j < baseS_test.size(); j++) {

            resultArrayForBaseS_test[j] = i.identify(baseS_test.get(j).getSymbolSequence());

            if (resultArrayForBaseS_test[j] == true && cpgTestingVectors.get(j).getLabel() == "positive cpg") {
                successForCpG++;
            } else {
                failForCpG++;
            }
        }

        for (int j = 0; j < baseSNonCpG_test.size(); j++) {

            resultArrayForBaseSNonCpG_test[j] = i.identify(baseSNonCpG_test.get(j).getSymbolSequence());

            if (resultArrayForBaseSNonCpG_test[j] == true && notCpgTestingVectors.get(j).getLabel() == "negative cpg") {
                successForNotCpG++;
            } else {
                failForNotCpG++;
            }

        }

        //Evaluation: calculation of classification rate and accuracy
        double totalAccuracy = (successForNotCpG + successForCpG) / (successForCpG + failForCpG + failForNotCpG + successForNotCpG);

        //missclassification rate for CpG class
        double rate1 = (failForCpG + successForCpG) != 0
                ? failForCpG / (failForCpG + successForCpG)
                : 0.0;

        //missclassification rate for Not CpG class
        double rate2 = (failForNotCpG + successForNotCpG) != 0
                ? failForNotCpG / (failForNotCpG + successForNotCpG)
                : 0.0;

        System.out.println(totalAccuracy + " " + rate1 + " " + rate2);
*/
//---------------------------------------HMM EVALUATION ENDS-----------------------------------------------
        



//---------------------------------------NGG STARTS-----------------------------------------------

/*
        NGramGraphClassifier nGramGraphClassifier = new NGramGraphClassifier();
        List<List<DocumentNGramGraph>> representation;
        NGramGraphAnalyzer myAnalyst = new NGramGraphAnalyzer();
        ArrayList<BaseSequence> listOfBaseSeq = new ArrayList<>();
        SequenceListFileReader reader = new SequenceListFileReader();
        
        listOfBaseSeq = reader.getSequencesFromFile("C:\\\\Users\\\\Xenia\\\\Documents\\\\NETBEANS\\\\CpGIslandDetection\\\\posSamples.txt");
        
        representation = myAnalyst.analyze(listOfBaseSeq);
        System.out.println("repre= "+representation.toString());
        nGramGraphClassifier.train(representation, "isCpG");
        
        for (List<DocumentNGramGraph> representation1 : representation) {
            nGramGraphClassifier.classify(representation1);
        } */
//---------------------------------------NGG ENDS-----------------------------------------------

        
    }
}