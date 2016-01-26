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
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class CpGIslandDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String sFileNameArgs = args[0];

        String[] fileNames = null;
        // Read  file
        //IGenomicSequenceFileReader reader = new SequenceListFileReader();
        IGenomicSequenceFileReader reader = new FASTAFileReader();
        ArrayList<BaseSequence> lSeqs;
        // If no input file has been given
        if (args.length == 0) {
            // Use default
            fileNames[0] = "C:\\Users\\Xenia\\Desktop\\posSamples.txt";
            fileNames[1] = "C:\\Users\\Xenia\\Desktop\\negSamples.txt";
            fileNames[2] = "C:\\Users\\Xenia\\Desktop\\newsamples.txt";

        } else // else use the provided one
        {
            fileNames = sFileNameArgs.split(";");
        }
        // Init classifier
        ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();

        // WARNING: Remember to change when you have normal data!!!
        // Obfuscation in negative training file?
        final boolean bObfuscateNeg = true;
        FASTAObfuscatorReader r = new FASTAObfuscatorReader();
        //for each file do the same work: train
        for (int i = 0; i < 3; i++) {
            // Read the sequences

            // If obfuscation is on and we are dealing with the negative
            // training file
            if ((i == 2) && (bObfuscateNeg)) {
                //FASTAObfuscatorReader r = new FASTAObfuscatorReader();
                lSeqs = r.getSequencesFromFile(fileNames[i]);
                fileNames[1] = "Not" + fileNames[1]; // Update to indicate different class
            }
            else
                // else read normally
                lSeqs = reader.getSequencesFromFile(fileNames[i]);

            System.out.println("lSeqs size="+lSeqs.size());

            // Create HMM sequences
            ISequenceAnalyst<List<ObservationDiscrete<HmmSequence.Packet>>> analyst
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

        //System.out.println("size of lHmmSeqs="+ lHmmSeqs.size());
        String str = null;
        String[] savedResults = new String[lHmmSeqs.size()];

        //create a 2x2 array to store successes and failures for each class
        int[][] matrix = new int[2][2];
        int successForCpG = 0, failForCpG = 0, successForNotCpG = 0, failForNotCpG = 0;
        
        // Init identifier
//        CpGIslandIdentification identifier = new CpGIslandIdentification();
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
            
                System.out.println(i);
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
    }
}
