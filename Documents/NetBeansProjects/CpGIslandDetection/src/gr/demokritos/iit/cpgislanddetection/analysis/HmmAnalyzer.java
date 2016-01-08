/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import java.util.*;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import java.util.ArrayList;
import java.util.List;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence.Packet;

/**
 * HmmAnalyzer creates a sequence of A,T,C,G from a List of Strings or
 * Observations (like buildSequence function) returns a
 * List of List<ObservationDiscrete<Packet>>
 *
 * @author Xenia
 */
public class HmmAnalyzer implements ISequenceAnalyst<List<List<ObservationDiscrete<Packet>>>> {

    @Override
    public List<List<ObservationDiscrete<Packet>>> analyze(ArrayList<BaseSequence> baseSeq) {

        List<List<ObservationDiscrete<Packet>>> lRes = new ArrayList<>();
                
        // Map strings to observations
        Map<Character,ObservationDiscrete<Packet>> mMap = new HashMap<>();
        mMap.put('A',Packet.A.observation());
        mMap.put('G',Packet.G.observation());
        mMap.put('T',Packet.T.observation());
        mMap.put('C',Packet.C.observation());
        
        //System.out.println(baseSeq);
        // For each sequence
        for (BaseSequence bsCur : baseSeq) {
            List<ObservationDiscrete<Packet>> testSeq = new ArrayList<>();
           
            // For each character
            for (Character c: bsCur.getSymbolSequence().toCharArray()) {
                testSeq.add(mMap.get(c));
               
            }
            
            // Output observation list
            // System.err.println(testSeq);
            
            // Add observation list to list of sequences (result list)
            lRes.add(testSeq);
        }

        return lRes;
    }
}
