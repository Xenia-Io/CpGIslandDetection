/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.entities;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;

/**
 *
 * @author Xenia
 */
public class HmmSequence implements IGenomicSequence{

    protected String hmmSeq;
    
    public static enum Packet {

        A, T, C, G;

        public ObservationDiscrete<Packet> observation() {
            return new ObservationDiscrete<Packet>(this);
        }
    };
    
    
    @Override
    public String getSymbolSequence() {
          //hmmSeq = ((Packet.A).toString())+((Packet.T).toString())+((Packet.C).toString())+((Packet.G).toString());
            return hmmSeq;
    } 
    
}
