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
import gr.demokritos.iit.cpgislanddetection.io.ARSSFileReader;
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
        IGenomicSequenceFileReader reader = new ARSSFileReader();
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
        
        //for each file do the same work: train
        for (int i = 0; i < 3; i++) {
            // Read the sequences
            
            lSeqs = reader.getSequencesFromFile(fileNames[i]);

            // Create HMM sequences
            ISequenceAnalyst<List<ObservationDiscrete<HmmSequence.Packet>>> analyst
                    = new HmmAnalyzer();
            List<List<ObservationDiscrete<HmmSequence.Packet>>> lHmmSeqs = analyst.analyze(lSeqs);

            // Train classifier with the observations
             classifier.train(lHmmSeqs, new File(fileNames[i]).getName());
        }
        
        //Classify the test file        
        //First: Read the sequences
        lSeqs = reader.getSequencesFromFile(fileNames[1]);
        //Then: Create HMM sequences
        ISequenceAnalyst<List<ObservationDiscrete<HmmSequence.Packet>>> analyst
                = new HmmAnalyzer();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lHmmSeqs = analyst.analyze(lSeqs);
        
        String str = null;
        String[] savedResults = new String[lHmmSeqs.size()];
        
        //create a 2x2 array to store successes and failures for each class
        int[][] matrix = new int[2][2];
        
        for (int i = 0; i < lHmmSeqs.size(); i++) {
            str = classifier.classify(lHmmSeqs.get(i));
            System.out.println(str);
            savedResults[i] = str;
            
            
            //kalw sunarthsh pou exetazei an to sequence ikanopoiei ta CpG criterias
            //sugkrinw to apotelesma ayths ths sunarthshs me to str 
            //kai kanw to apotelesma save ston pinaka
            
        }
        
        //Evaluation: calculation of classification rate and accuracy
        
    }
}
