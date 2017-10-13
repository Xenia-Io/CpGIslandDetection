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
  /*     
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
   */     
    //------------------VECTOR ANALYSIS ENDS HERE---------------------------------------
        
        
        
        
        
   
        
                                             
       
        
        //----------------------------HMM CLASSIFIER STARTS HERE----------------------------------
                                            /*
        // Init classifier
        ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();

        FASTAFileReader rPOS = new FASTAFileReader();
        FASTAObfuscatorReader rNEG = new FASTAObfuscatorReader();
        
        List<BaseSequence> baseS = rPOS.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\NETBEANS\\CpGIslandDetection\\data\\testForPosCPG_Fasta.txtSMALL");
        List<BaseSequence> baseSNonCpG = rNEG.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\NETBEANS\\CpGIslandDetection\\data\\testForPosCPG_Fasta.txtSMALL");

        System.out.println("baseS size=" + baseS.size());

//        ArrayList<BaseSequence> baseS_test = rPOS.getSequencesFromFile("data/testForPosCPG_Fasta.txt");
//        ArrayList<BaseSequence> baseSNonCpG_test = rNEG.getSequencesFromFile("data/testForPosCPG_Fasta.txt");
        
        // Extract test instances
        List<BaseSequence> baseS_test = baseS.subList(baseS.size() - 30, baseS.size());
        List<BaseSequence> baseSNonCpG_test = baseSNonCpG.subList(baseSNonCpG.size() - 30, baseSNonCpG.size());
        
        // Update training instances (by removing the selected test instances)
//        baseS = baseS.subList(0, baseS.size() - 100);
//        baseSNonCpG = baseSNonCpG.subList(0, baseS.size() - 100);
//        baseS = baseS.subList(0, 200);
//        baseSNonCpG = baseSNonCpG.subList(0, 200);
        
        System.out.println("TRAIN: sizeOfPos = " + baseS.size() + " " + "sizeOfNeg = " + baseSNonCpG.size());
        System.out.println("TEST: sizeOfPos = " + baseS_test.size() + " " 
                + "sizeOfNeg = " + baseSNonCpG_test.size());
        
        
 
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

//        classifier.train(lRes, "isCpG");
//        classifier.train(lResNon, "isNotCpG");

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
       
                                                */
//         HMMFeatureVector fv = new HMMFeatureVector();
//        //for (int i = 0; i < 30; i++) {
//            System.out.println("XENIA11111111");
//            System.out.println("cpg testing vector: <" + cpgTestingVectors.get(0).getProbArrayAtIndex(0) +
//                    ", " + cpgTestingVectors.get(0).getProbArrayAtIndex(1) + ">");
//            
//            System.out.println("cpg testing vector label = " + cpgTestingVectors.get(0).getLabel());
//       // }
//        //for (int i = 0; i < 30; i++) {
//            System.out.println("XENIA22222222");
//
//            System.out.println("NOTcpg testing vector: <" + notCpgTestingVectors.get(0).getProbArrayAtIndex(0) +
//                    ", " + notCpgTestingVectors.get(0).getProbArrayAtIndex(1) + ">");
//            System.out.println("NOTcpg testing vector label = " + notCpgTestingVectors.get(0).getLabel());
//        //}
        
        // 1st ALTERNATIVE
        // Classify an instance to the label of the HMM it is most similar to (i.e. HMM which return higher likelihood)
        
        // 2nd ALTERNATIVE
        // TODO: From vectors convert to WEKA instances
        // TODO: Train classifier based on training instances
        // TOOD: Test classifier (using the trained model) on the TEST instances
        
        //Get the Weka feature vectors
        
                            /*
        WekaHMMFeatureVector HMMfv= new WekaHMMFeatureVector();
        Instances Training_Instances = HMMfv.fillInstanceSet(NFRTrainingVectors, NBSTrainingVectors);
        Instances Testing_Instances = HMMfv.fillInstanceSet(NFRTestingVectors, NBSTestingVectors);

        try {
            saveFoldFiles(Training_Instances, i, Testing_Instances);
        } catch (IOException ioe) {
            ioe.printStackTrace(System.err);
            System.out.println("Could not output fold ARFF files "
                    + "(perhaps ARFF directory is missing?). "
                    + "Skipping...");
        }

        tic = System.nanoTime();

        // Perform classification and get Confusion Matrix
        BinaryStatisticsEvaluator ev = new BinaryStatisticsEvaluator();
        double[][] ConfMatrix = ev.getConfusionMatrix(Training_Instances, Testing_Instances, classifier_type);

        tac = System.nanoTime();
        elapsedTime = tac - tic;
        seconds = (double)elapsedTime / 1000000000.0;
        System.out.println("time elapsed for HMM classification : " + seconds);
        // Print results
        System.out.println("Precision of model :" + ev.getPrecision(ConfMatrix));
        System.out.println("Accuracy of model :" + ev.getAccuracy(ConfMatrix));
        System.out.println("AUC of model :" + ev.getAUC(ConfMatrix));
        System.out.println("Recall of model :" + ev.getRecall(ConfMatrix));
        System.out.println("Specificity of model :" + ev.getSpecificity(ConfMatrix));
        System.out.println("F-score of model :" + ev.getfScore(ConfMatrix));

        rotate(NFR_Seqs, NFRpartitionSize);
        rotate(NBS_Seqs, NBSpartitionSize);

        clearSeqs(NFR_trainingSeqs, NBS_trainingSeqs, NFR_testingSeqs, NBS_testingSeqs);

                                */
        //-------------------------------HMM CLASSIFIER ENDS HERE-----------------------------------------

   
        
       
        
        
        //----------------------------RULE-BASED EVALUATION STARTS-----------------------------------------------
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
        double totalAccuracy = (0.0 + successForNotCpG + successForCpG) / (0.0 + successForCpG + failForCpG + failForNotCpG + successForNotCpG);

        //missclassification rate for CpG class
        double rate1 = (failForCpG + successForCpG) != 0
                ? (0.0 + failForCpG) / (failForCpG + successForCpG)
                : 0.0;

        //missclassification rate for Not CpG class
        double rate2 = (failForNotCpG + successForNotCpG) != 0
                ? (0.0 + failForNotCpG) / (failForNotCpG + successForNotCpG)
                : 0.0;

        System.out.println(totalAccuracy + " " + rate1 + " " + rate2);
                                              */
//---------------------------------------RULE-BASED EVALUATION ENDS-----------------------------------------------
        



//---------------------------------------NGG STARTS-----------------------------------------------

                                            

        NGramGraphClassifier nGramGraphClassifier = new NGramGraphClassifier();
        SequenceListFileReader reader = new SequenceListFileReader();
        FASTAFileReader fReader = new FASTAFileReader();
        
        List<List<DocumentNGramGraph>> representationForCpG;
        List<List<DocumentNGramGraph>> representationForNonCpG;
        
        ArrayList<BaseSequence> listOfBaseSeqForCpG = new ArrayList<>();
        ArrayList<BaseSequence> listOfBaseSeqForNonCpG = new ArrayList<>();
        
        //listOfBaseSeqForCpG = reader.getSequencesFromFile("data/posSamples.txt");
        listOfBaseSeqForNonCpG = reader.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\17112016\\CpGIslandDetection\\data\\negSamples.txt");
        
        listOfBaseSeqForCpG = fReader.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\17112016\\CpGIslandDetection\\data\\testForPosCPG_Fasta.txtSMALL");
        
        NGramGraphAnalyzer myAnalyst = new NGramGraphAnalyzer();
        representationForCpG = myAnalyst.analyze(listOfBaseSeqForCpG);
        representationForNonCpG = myAnalyst.analyze(listOfBaseSeqForNonCpG);
        
        //System.out.println("representation = "+representationForCpG.toString());
        //System.out.println("representation = "+representationForNonCpG.toString());
        
        // TODO: Train with negatives and positive samples
        nGramGraphClassifier.train(representationForCpG, "isCpG");
        nGramGraphClassifier.train(representationForNonCpG, "notCpG");
        int i=0;
        System.out.println("representationForCpG size = " + representationForCpG.size());
        for (List<DocumentNGramGraph> representation1 : representationForCpG) {
            System.out.println("representation1 SIZEEE = " + representation1.size());
            String result = nGramGraphClassifier.classify(representation1);
            i++;
            System.out.println("i = " + i);
            System.out.println("RESULT = " + result);
        }
        
                                                
        
  
//---------------------------------------NGG ENDS-----------------------------------------------

        
    }
}