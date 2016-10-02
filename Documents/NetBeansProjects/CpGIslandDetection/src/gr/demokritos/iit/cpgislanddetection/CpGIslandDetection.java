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
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import gr.demokritos.iit.cpgislanddetection.io.FileCreatorARFF;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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
        
       // String sFileNameArgs = args[0];

       // String[] fileNames = null;
        // Read  file
        //IGenomicSequenceFileReader reader = new SequenceListFileReader();
        
//        String seq ="GCTCTTGACTTTCAGACTTCCTGAAAACAACGTTCTGGTAAGGACAAGGGTT";
//
//        CpGIslandIdentification iClass = new CpGIslandIdentification();
//        boolean b = iClass.identify(seq);
//        System.out.println("This sequence is a CpG island: " + b);
//        SequenceListFileReader s = new SequenceListFileReader();
//        ArrayList<BaseSequence> alRes = new ArrayList<>();
//        
//        alRes = s.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\files\\posSamples.txt");

//        for(int i=0; i<alRes.size(); i++)
//        System.out.println("alRes = " + i + alRes.get(i));
//        VectorAnalyzer vA = new VectorAnalyzer();
//        List<Vector<Integer>> listVector = new ArrayList<>();
        //Vector<Vector<Integer>> list = 
//        listVector = vA.analyze(alRes);
//        for(int i=0; i<listVector.size();i++)
//        System.out.println(i + " " +listVector.get(i));
        //IGenomicSequenceFileReader reader = new FASTAFileReader();
        
        
        
        // If no input file has been given
/*        if (args.length == 0) {
            // Use default
            fileNames[0] = "C:\\Users\\Xenia\\Desktop\\files\\posSamples.txt";
            fileNames[1] = "C:\\Users\\Xenia\\Desktop\\files\\negSamples.txt";
            fileNames[2] = "C:\\Users\\Xenia\\Desktop\\files\\newsamples.txt";

        } else // else use the provided one
        {
            fileNames = sFileNameArgs.split(";");
        }
*/  
        
        
        //-----------------VECTOR ANALYSIS STARTS HERE--------------------------------------
        
        //read sequences from txt files
        SequenceListFileReader reader = new SequenceListFileReader();
        ArrayList<BaseSequence> lSeqs1 = new ArrayList<>();
        ArrayList<BaseSequence> lSeqs2 = new ArrayList<>();
        
        lSeqs1 = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\files\\posSamples.txt");
        lSeqs2 = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\files\\negSamples.txt");
       
        //create vectors for every sequence
        List<Vector<Integer>> listVectorForPositiveSamples = new ArrayList<>();
        List<Vector<Integer>> listVectorForNegativeSamples = new ArrayList<>();
        VectorAnalyzer v = new VectorAnalyzer();
        listVectorForPositiveSamples = v.analyze(lSeqs1);
        listVectorForNegativeSamples = v.analyze(lSeqs2);
        
        //create ARFF files for positive and negative samples
        FileCreatorARFF fc = new FileCreatorARFF();
        Instances positiveInstances = fc.createARFF(listVectorForPositiveSamples,"yes");
        Instances negativeInstances = fc.createARFF(listVectorForNegativeSamples,"no");
        //System.out.println(positiveInstances);
        
        //build and train classifier
        // setting class attribute
        positiveInstances.setClassIndex(positiveInstances.numAttributes() - 1);
        negativeInstances.setClassIndex(negativeInstances.numAttributes() - 1);
        // train NaiveBayes
        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(positiveInstances);
        nb.buildClassifier(negativeInstances);
        Instance current;
        for(int i=0; i<positiveInstances.numInstances(); i++){
            current = positiveInstances.instance(i);
            nb.updateClassifier(current);
        }
        
        // Test the model
        Evaluation eTest = new Evaluation(positiveInstances);
        Instances isTestingSet = fc.createARFF(listVectorForNegativeSamples,"?");
        isTestingSet.setClassIndex(isTestingSet.numAttributes() - 1);
        eTest.evaluateModel(nb, isTestingSet);
        
        //------------------VECTOR ANALYSIS ENDS HERE---------------------------------------
        
        
        
        
        
        //----------------------------HMM CLASSIFIER STARTS HERE----------------------------------
        // Init classifier
 /*       ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();
*/
        // WARNING: Remember to change when you have normal data!!!
        // Obfuscation in negative training file?
//       final boolean bObfuscateNeg = true;
//        FASTAObfuscatorReader r = new FASTAObfuscatorReader();
        //for each file do the same work: train
//        for (int i = 0; i < 3; i++) {
            // Read the sequences

            // If obfuscation is on and we are dealing with the negative
            // training file
/*            if ((i == 2) && (bObfuscateNeg)) {
                //FASTAObfuscatorReader r = new FASTAObfuscatorReader();
                lSeqs = r.getSequencesFromFile(fileNames[i]);
                fileNames[1] = "Not" + fileNames[1]; // Update to indicate different class
            }
            else
                // else read normally
                lSeqs = reader.getSequencesFromFile(fileNames[i]);

            System.out.println("lSeqs size="+lSeqs.size());
*/
            // Create HMM sequences
/*            ISequenceAnalyst<List<ObservationDiscrete<HmmSequence.Packet>>> analyst
                    = new HmmAnalyzer();
            List<List<ObservationDiscrete<HmmSequence.Packet>>> lHmmSeqs = analyst.analyze(lSeqs);

            // Train classifier with the observations
            classifier.train(lHmmSeqs, new File(fileNames[i]).getName());
        }

        //Classify the test file        
        //First: Read the sequences
        lSeqs = r.getSequencesFromFile(fileNames[2]);
        //System.out.println("file name= "+fileNames[2]);
        //Then: Create HMM sequences
        ISequenceAnalyst<List<ObservationDiscrete<HmmSequence.Packet>>> analyst
                = new HmmAnalyzer();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lHmmSeqs = analyst.analyze(lSeqs);
*/
        
        //-------------------------------HMM CLASSIFIER ENDS HERE-----------------------------------------

        
        /*
        
        //----------------------------HMM EVALUATION STARTS-----------------------------------------------
        //System.out.println("size of lHmmSeqs="+ lHmmSeqs.size());
        String str = null;
        String[] savedResults = new String[lHmmSeqs.size()];

        //create a 2x2 array to store successes and failures for each class
        int[][] matrix = new int[2][2];
        int successForCpG = 0, failForCpG = 0, successForNotCpG = 0, failForNotCpG = 0;
        
        // Init identifier
        // CpGIslandIdentification identifier = new CpGIslandIdentification();
        CpGIslandIdentification identifier = new CpGIslandIdentificationByList("CpG_hg18.fa");
        
        for (int i = 0; i < lHmmSeqs.size(); i++) {
            // DEBUG
            System.err.print(".");
            if (i % 10 == 0)
                System.err.println();
            ////////
            str = classifier.classify(lHmmSeqs.get(i));
                    //  System.out.println(  "i="+i);    

            System.out.println("Determined class:" + str);
//            savedResults[i] = str;

            //kalw sunarthsh pou exetazei an to sequence ikanopoiei ta CpG criterias
            if (identifier.identify(lSeqs.get(i).getSymbolSequence()) && str.equals(fileNames[0])) {

                //Success for CpG class
                successForCpG++;
                System.out.println("successForCpG"  + successForCpG);
            } else if (identifier.identify(lSeqs.get(i).getSymbolSequence()) && str.equals(fileNames[1])) {
                //fail for CpG class
                failForCpG++;
                System.out.println("failForCpG" + failForCpG);
            } else if (identifier.identify(lSeqs.get(i).getSymbolSequence()) == false && str.equals(fileNames[1])) {
            
                //System.out.println(i);
                //Success for Not CpG class
                successForNotCpG++;
                System.out.println("successForNotCpG" + successForNotCpG);
            } else if (identifier.identify(lSeqs.get(i).getSymbolSequence()) == false && str.equals(fileNames[0])) {
          
                //fail for Not CpG class
                failForNotCpG++;
                System.out.println("failForNotCpG" + failForNotCpG);
            }
          
        }

        //Evaluation: calculation of classification rate and accuracy
        double totalAccuracy = (successForNotCpG + successForCpG)/(successForCpG + failForCpG + failForNotCpG + successForNotCpG);
        
        //missclassification rate for CpG class
        double rate1 = ( failForCpG + successForCpG ) != 0 ?
                failForCpG / ( failForCpG + successForCpG ) :
                0.0;
        
        //missclassification rate for Not CpG class
        double rate2 = ( failForNotCpG + successForNotCpG ) != 0 ? 
                failForNotCpG / ( failForNotCpG + successForNotCpG ) :
                0.0;
        
        System.out.println(totalAccuracy +" "+ rate1 + " "+ rate2);
        
        NGramGraphClassifier nGramGraphClassifier = new NGramGraphClassifier();
        List<List<DocumentNGramGraph>> representation;
        NGramGraphAnalyzer myAnalyst = new NGramGraphAnalyzer();
        representation = myAnalyst.analyze(lSeqs);
        for(int i=0; i<representation.size();i++)
        nGramGraphClassifier.classify(representation.get(i));
        
        
        */
    }
}
