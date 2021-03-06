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
public class SequenceListFileReader implements IGenomicSequenceFileReader {

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
            Logger.getLogger(SequenceListFileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SequenceListFileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        // Return result list
        return alRes;
    }

}
