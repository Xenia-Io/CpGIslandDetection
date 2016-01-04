/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class VectorSequenceDetector implements ICpGSequenceDetector {

    public VectorSequenceDetector(List<IGenomicSequence> sequences, List<String> labels) {
        // Initialize classifier
        // Train classifier
    }

    
    @Override
    public boolean detect(IGenomicSequence seq) {
        return true;
        // Classify seq using your existing classifier
        // return true if the label indicates a CpG
    }
    
}
