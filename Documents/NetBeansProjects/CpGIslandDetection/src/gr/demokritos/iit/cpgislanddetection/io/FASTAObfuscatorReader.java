/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.io;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Xenia
 * 
 * This file changes the original FASTA file with the positive CpG islands
 */
public class FASTAObfuscatorReader extends FASTAFileReader {

    @Override
    public ArrayList<BaseSequence> getSequencesFromFile(String sFileName) {
        ArrayList<BaseSequence> lTmp =  super.getSequencesFromFile(sFileName); 
        ArrayList<BaseSequence> lRes = new ArrayList<>();
        // For each sequence
        for (BaseSequence sSeq : lTmp) {
            // Get the string
            lRes.add(new BaseSequence(swapRandomCharacters(sSeq.getSymbolSequence())));
        }
         
        return lRes;
    }
    
    private String swapRandomCharacters(String sToChange) {
        StringBuffer sbRes = new StringBuffer();
        // Get first char
        char cPrv = sToChange.charAt(0);
        // Init randomizer
        Random r = new Random();
        
        // For every character 
        for (char cCur : sToChange.substring(1).toCharArray()) {
            // With a 50% chance
            if (r.nextBoolean()) {
                // swap the two last characters
                sbRes.append(cCur);
                // Do NOT change the previous character
            }
                
        }
        
        // Add last character
        sbRes.append(cPrv);
        
        // Return changed sequence
        return sbRes.toString();
    }

}
