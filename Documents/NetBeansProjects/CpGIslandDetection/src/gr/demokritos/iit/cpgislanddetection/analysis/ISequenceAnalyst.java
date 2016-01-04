/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.util.ArrayList;

/**
 * This interface describes all classes that can take a sequence as an input
 * and produce an equivalent representation.
 * @author Xenia
 */
public interface ISequenceAnalyst<T> {
    public T analyze(ArrayList<BaseSequence> gsSeq);
}
