/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.entities;

/**
 *In this class we build sequences which are appropriate  
 * for VectorSpaceRepresentation method
 * @author Xenia
 */
public class BaseSequence implements IGenomicSequence {

    protected String BaseSeq;
    
    public BaseSequence(String sSeq) {
        BaseSeq = sSeq;
    }
    
    @Override
    public String getSymbolSequence() {
        return BaseSeq;
    }
    
    @Override
    public String toString() {
        return BaseSeq;
    }
    
    public int myLength(String sSeq) {
        return sSeq.length();
    }
    
    public int myCharAt(int i, String sSeq) {
        return sSeq.charAt(i);
    }
}
