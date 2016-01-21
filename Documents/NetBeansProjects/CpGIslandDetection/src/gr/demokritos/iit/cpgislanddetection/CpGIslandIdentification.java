/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Xenia
 */
public class CpGIslandIdentification {
    
    private static boolean identify(List<ObservationDiscrete<HmmSequence.Packet>> sequence){
    
        int numOfC = 0, numOfG = 0, numOfCpG = 0;
        double percentCG = 0;
        int window = 10;
        int i, j, k, m, n;
        double score = 0;
        int begin = 0;
        double totalScore = 0, totalPercentCG = 0;
        double totalAdjScore=0;
        double totalAdjPercentCG=0;
        
        // compute the number of C and G inside a window
        int startWindow = 0;
        int endWindow = 199;
        int originalWindow = 200;
        int rows_counter=0;
        window = originalWindow;
        ArrayList<Double> percentCGList = new ArrayList<>();
        ArrayList<Double> scoreCGList = new ArrayList<>();
            
        //array where each row is a sub-sequence that satisfies the CpG criteria
        double arrayForAdjacentCpG[][] = new double[(sequence.size()/window)][4];
        //allocate space for my array - max number of rows are str.length()/window (!!!)
        for (m = 0; m < (sequence.size()/window); m++) {
            for (n = 0; n < 4; n++) {
                arrayForAdjacentCpG[m] = new double[n];                
            }
        }
        
        numOfCpG = findCG(sequence);
        System.out.println("In the first test number of CpG= " + numOfCpG);
        System.out.println("length of str= "+sequence.size());
        
        while (startWindow < sequence.size()) {

            //compute cytosines and gouanines
            numOfC = computeCytosine(startWindow, endWindow, sequence, window);
            //System.out.println("C= "+numOfC);
            numOfG = computeGouanine(startWindow, endWindow, sequence, window);
            //System.out.println("G= "+numOfG);
            
            // compute the criteria of a CpG island inside the first window
            percentCG = (double) (numOfC + numOfG) / (sequence.size());

            if (numOfC > 0 && numOfG > 0) {
                score = (double) ((numOfCpG * sequence.size()) / (numOfC * numOfG));

            }
            //System.out.println("first score= "+score);
            totalPercentCG += percentCG;
            totalScore += score;
            
            percentCGList.add(percentCG);
            scoreCGList.add(score);
            
            //the criteria are satisfied 
            if (percentCG >= 0.5 && score >= 0.6) {
               
                //insert the subsequence in that array where each row is a sub-sequence that satisfies the CpG criteria
                for (m = 0; m < (sequence.size() / window); m++) {

                    arrayForAdjacentCpG[m][0] = startWindow; //1st column where my sub-sequence starts
                    arrayForAdjacentCpG[m][1] = endWindow;   //2nd column where it ends 
                    arrayForAdjacentCpG[m][2] = percentCG;   //3rd column its %GC
                    arrayForAdjacentCpG[m][3] = score;       //4rt column its o/e score
                    
                    rows_counter++;
                    //System.out.println("rows counter="+rows_counter);
                }
                
                startWindow = endWindow + 1;
                endWindow = endWindow + window;
                
            } else {
                startWindow++;
                endWindow++;
            }
            if (endWindow >= sequence.size()) {
                //an exw length =10 ara 0..9    kai endWindow=10, dhladh sto 11o stoixeio
                window = originalWindow - (endWindow - sequence.size() + 1);
                endWindow = sequence.size() - 1;
            }
            
        } //end of while
        
         System.out.println("score= " + totalScore + " percentCG= " + totalPercentCG);
        
         
         //TODOS: CHECK IF ITS TRUE OR FALSE
        return true;
    }
    
    public static int findCG( List<ObservationDiscrete<HmmSequence.Packet>> str) {

        int i, j = 0;

        for (i = 0; i < str.size() - 1; i++) {
            //int begin = Integer.parseInt(str.charAt(i));
            //test = str.substring(begin, begin + 1);

            if (str.charAt(i) == 'c' && str.charAt(i + 1) == 'g') {

                j++;
            }
        }
        return j;
    }
    
    
    public static int computeCytosine(int begin, int end, List<ObservationDiscrete<HmmSequence.Packet>> str, int window) {

        int i = 0;
        int numOfC = 0;

        for (i = 0; i < window; i++) {

            if (str.contains("c")) {

                if (str.charAt(i) == 'c') {
                    numOfC++;

                }
            }

        }

        return numOfC;
    }

    public static int computeGouanine(int begin, int end, List<ObservationDiscrete<HmmSequence.Packet>> str, int window) {

        int i = 0;
        int numOfG = 0;

        for (i = 0; i < window; i++) {

            if (str.contains("c")) {

                if (str.charAt(i) == 'g') {
                    numOfG++;

                }

            }

        }

        return numOfG;
    }
    
}
