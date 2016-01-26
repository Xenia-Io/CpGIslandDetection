/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Xenia
 */
public class CpGIslandIdentificationByList extends CpGIslandIdentification {
    Set<String> sSeqs;
    
    public CpGIslandIdentificationByList(String sFASTAFileName) {
        sSeqs = new HashSet<>();
        FASTAFileReader f = new FASTAFileReader();
        
        for (BaseSequence b : f.getSequencesFromFile(sFASTAFileName)) {
            sSeqs.add(b.getSymbolSequence());
        }
    }

    @Override
    public boolean identify(String sequence) {
        return sSeqs.contains(sequence);
    }
    
    
}
