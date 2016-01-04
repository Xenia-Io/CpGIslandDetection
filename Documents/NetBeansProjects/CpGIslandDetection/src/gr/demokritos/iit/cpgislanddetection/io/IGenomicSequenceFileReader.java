/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.io;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.util.ArrayList;
import java.util.List;

/**
 * This interface describes all classes that can read and parse
 * a genomic sequence file.
 * @author Xenia
 */
public interface IGenomicSequenceFileReader {
    public ArrayList<BaseSequence> getSequencesFromFile(String sFileName);
}
