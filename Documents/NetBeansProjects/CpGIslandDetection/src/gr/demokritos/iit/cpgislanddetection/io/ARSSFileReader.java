 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.io;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xenia
 */
public class ARSSFileReader implements IGenomicSequenceFileReader {

    @Override
    public ArrayList<BaseSequence> getSequencesFromFile(String sFileName) {

        String str = new String();

        // Initialize empty result list
        ArrayList<BaseSequence> alRes = new ArrayList<>();

        try {
            //Open and Read all text with sequences-samples and Create a BufferedReader from a FileReader
            BufferedReader reader = new BufferedReader(new FileReader(sFileName));

            // For every line/sequence
            while ((str = reader.readLine()) != null) {

                // Create corresponding sequence object
                BaseSequence baseSeq = new BaseSequence(str);
                // Add it to results list
                alRes.add(baseSeq);
                //MyListBaseSequence.addList(baseSeq);

            }
                    reader.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ARSSFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ARSSFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        // Return result list
        return alRes;
    }

}
