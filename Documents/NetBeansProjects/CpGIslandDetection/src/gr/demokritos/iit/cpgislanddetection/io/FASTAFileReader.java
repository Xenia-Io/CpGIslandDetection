/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.io;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Xenia
 */
public class FASTAFileReader implements IGenomicSequenceFileReader {

    @Override
    public ArrayList<BaseSequence> getSequencesFromFile(String sFileName) {
        ArrayList<BaseSequence> lResults = new ArrayList<>();
        
        try {
            // Open file
            BufferedReader fIn = new BufferedReader(new FileReader(sFileName));
            String sCurLine = null;
            StringBuffer sbCurrentSequence = new StringBuffer();
            // For every line
            while ((sCurLine = fIn.readLine()) != null) {
                // If line indicated new sequence
                if (sCurLine.trim().startsWith(">")) {                    
                    //  Store previous sequence to list of results
                    // if not empty
                    if (sbCurrentSequence.length() > 0)
                        
                        lResults.add(new BaseSequence(sbCurrentSequence.toString()));
                   // System.out.println("sbCurrentSequence is="+sbCurrentSequence.toString());
                    // Initialize buffer
                    sbCurrentSequence = new StringBuffer();
                }
                else // else 
                {
                    // Append to buffer (removing whitespace)
                    sbCurrentSequence.append(sCurLine);
                }
            }
            
            // Store last sequence
            // Close file
            fIn.close();
        } catch (IOException iOException) {
            return null;
        }
        
        System.out.println("lResults size="+lResults.size());
        return lResults;
    }
    
}
