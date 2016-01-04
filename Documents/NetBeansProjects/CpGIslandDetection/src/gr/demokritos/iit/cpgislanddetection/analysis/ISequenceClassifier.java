/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

/**
 *
 * @author Xenia
 */
public interface ISequenceClassifier<T> {
    public String classify(T representation);
    public void train(T representation, String sLabel);
}
