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
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.ARSSFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import java.io.IOException;
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
        //String sFileName = args[0];
        
        // Read training file
        IGenomicSequenceFileReader reader = new ARSSFileReader();
        ArrayList<BaseSequence> lSeqs;
        // If no input file has been given
        if (args.length == 0) 
            // Use default
            lSeqs = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\negSamples.txt");
        else
            // else use the provided one
            lSeqs = reader.getSequencesFromFile(args[0]);
        
//        int counter =0;
//        for (BaseSequence elem : lSeqs) {
//            counter++;
//            System.out.println(counter+":"+elem + "  ");
//            
//        }
        
        // Initialize sequence detector
        //ICpGSequenceDetector detector = new VectorSequenceDetector(lSeqs, null);
        // Read test file
        // For every test instance
            // Get result
            // Count successes and failures
        
        // Output results
//        
//        VectorAnalyzer v = new VectorAnalyzer();
//        
//        Vector<Vector<Integer>> myVec = v.analyze(lSeqs);
//        int count=0;
//        for(Vector vec: myVec){
//        count++;
//            System.out.println(count+":"+vec+" ");
//        }
        
        HmmSequence hmm = new HmmSequence();
        String str = hmm.getSymbolSequence();
        //System.out.println(str);
        List<Double> doubleList = new ArrayList<Double>();
        HmmClassifier hmmC = new HmmClassifier();
//      doubleList = hmmC.computeProbability(lSeqs);
//        int count=0;
//        for(Double b:doubleList){
//        
//            count++;
//            System.out.println(count+":"+b+"");
//        }
      
        
        
//        
        HmmAnalyzer h = new HmmAnalyzer();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> p = h.analyze(lSeqs);
        //int count=0;
        for(List<ObservationDiscrete<HmmSequence.Packet>> k:p){
        
            //count++;
            System.out.println(k);
            //System.out.println(count+":"+k+"");
        }
         
    }
    
}
