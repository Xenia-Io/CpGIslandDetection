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

    public boolean identify(String sequence) {
        if (sequence.length() < 200) {
            return false;
        }
        int numOfC = 0, numOfG = 0, numOfCpG = 0;
        double percentCG = 0;
        double score = 0;
        double totalScore = 0.0, totalPercentCG = 0.0;
        int startWindow = 0;
        int endWindow = 200;
        int windowLength = 200;
        int eval = 0;
        boolean last = false;

        while (endWindow <= sequence.length()) {
            eval++;
            numOfC = computeCytosine(startWindow, endWindow, sequence);
            numOfG = computeGouanine(startWindow, endWindow, sequence);
            numOfCpG = findCG(startWindow, endWindow, sequence);

            // compute the criteria of a CpG island inside the first window
            percentCG = (double) (numOfC + numOfG) / (double) (windowLength);

            //System.out.println("percentCG = "+ percentCG);
            score = 0.0;
            if (numOfC > 0 && numOfG > 0) {
                //score is o/e ratio
                score = (double) ((numOfCpG * windowLength) / (numOfC * numOfG));
            }
            //the criteria are satisfied 
            if (percentCG >= 0.5 && score >= 0.6) {
                totalPercentCG += percentCG;
                totalScore += score;
                startWindow += windowLength;
                endWindow = startWindow + windowLength;
//                System.out.println("AT startWindow= " + startWindow + " endWindow=" + endWindow);
//                System.out.println("numOfC=" + numOfC + " numOfG=" + numOfG);
//                System.out.println("score=" + score);
//                System.out.println("totalPercentCG=" + percentCG);
//                System.out.println("totalScore=" + score);
            } else {
                startWindow++;
                endWindow++;
            }
            if (last) {
                break;
            }
            if (endWindow >= sequence.length()) {
                //gia cases opws ayto-> an exw length =10 ara 0..9 kai endWindow=10, dld sto 11o stoixeio
                //window = originalWindow - (endWindow - sequence.length() + 1);
                endWindow = sequence.length() - 1;
                windowLength = endWindow - startWindow;
                last = true;
                //so in the next loop the detection will be ended
            }

        } //end of while

        totalScore /= eval;
        totalPercentCG /= eval;
        if (totalScore >= 0.6 && totalPercentCG >= 0.5) {
            return true;
        } else {
            return false;
        }
    }

    //check when a cytocine is followed by a gouanine
    public static int findCG(int startWindow, int endWindow, String str) {
        int count = 0;
        int idx = 0;
        String sub = str.substring(startWindow, endWindow);
        String substring = "CG";
        while ((idx = str.indexOf(substring, idx)) != -1) {
            idx++;
            count++;
        }
        return count;
    }

    public static int computeCytosine(int begin, int end, String str) {
        int numOfC = 0;
        for (int i = begin; i < end; i++) {
            if (i >= str.length()) {
                break;
            }
            if (str.charAt(i) == 'C') {
                numOfC++;
            }
        }
        return numOfC;
    }

    public static int computeGouanine(int begin, int end, String str) {
        int numOfG = 0;
        for (int i = begin; i < end; i++) {
            if (i >= str.length()) {
                break;
            }
            if (str.charAt(i) == 'G') {
                numOfG++;
            }
        }
        return numOfG;
    }
    
}
