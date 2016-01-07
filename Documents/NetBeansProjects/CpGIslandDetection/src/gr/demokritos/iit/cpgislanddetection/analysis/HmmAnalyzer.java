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
 * List<ObservationDiscrete<Packet>>
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
        mMap.put('a',Packet.A.observation());
        mMap.put('g',Packet.G.observation());
        mMap.put('t',Packet.T.observation());
        mMap.put('c',Packet.C.observation());
        
        // For each sequence
        for (BaseSequence bsCur : baseSeq) {
            List<ObservationDiscrete<Packet>> testSeq = new ArrayList<>();
            // For each character
            for (Character c: bsCur.getSymbolSequence().toCharArray()) {
                testSeq.add(mMap.get(c));
            }
            
            // Output observation list
            System.err.println(testSeq);
            // Add observation list to list of sequences (result list)
            lRes.add(testSeq);
        }

//        ObservationDiscrete<Packet> packetA = Packet.A.observation();
//        ObservationDiscrete<Packet> packetT = Packet.T.observation();
//        ObservationDiscrete<Packet> packetC = Packet.C.observation();
//        ObservationDiscrete<Packet> packetG = Packet.G.observation();
//        
//        for (int line = 0; line < baseSeq.size(); line++) {
//            String currentStr = baseSeq.get(line).getSymbolSequence();
//            System.out.println(currentStr);
//            for (int i = 0; i < baseSeq.get(line).myLength(currentStr); i++) {
//                if (baseSeq.get(line).myCharAt(i, currentStr) == 'A') {
//
//                    testSeq.add(packetA);
//
//                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'T') {
//
//                    testSeq.add(packetT);
//
//                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'C') {
//
//                    testSeq.add(packetC);
//
//                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'G') {
//
//                    testSeq.add(packetG);
//
//                }
//            }
//            System.out.println(testSeq);
//        }

        return lRes;
    }
}
