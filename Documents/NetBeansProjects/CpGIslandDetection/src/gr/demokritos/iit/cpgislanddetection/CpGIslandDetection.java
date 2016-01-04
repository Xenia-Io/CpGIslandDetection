/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import gr.demokritos.iit.cpgislanddetection.analysis.ICpGSequenceDetector;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorSequenceDetector;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorAnalyzer;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import gr.demokritos.iit.cpgislanddetection.io.ARSSFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Xenia
 */
public class CpGIslandDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String sFileName = args[0];
        
        // Read training file
        IGenomicSequenceFileReader reader = new ARSSFileReader();
        ArrayList<BaseSequence> lSeqs = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\negSamples.txt");
        
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
        
        VectorAnalyzer v = new VectorAnalyzer();
        
        Vector<Vector<Integer>> myVec = v.analyze(lSeqs);
        int count=0;
        for(Vector vec: myVec){
        count++;
            System.out.println(count+":"+vec+" ");
        }
         
    }
    
}
