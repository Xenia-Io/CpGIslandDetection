/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.entities;

import java.util.ArrayList;

/**
 *
 * @author Xenia
 */
public class HMMFeatureVector {

    String label;
    ArrayList<Double> probArray;

    public HMMFeatureVector() {
        probArray = new ArrayList<>();
    }

    public void setLabel(String classLabel) {

        label = classLabel;
    }

    public String getLabel() {
        return label;
    }

    public double getProbArrayAtIndex(int index) {
        return probArray.get(index);
    }

    public void setProbArrayAtIndex(double element, int index) {
        this.probArray.add(index, element);
    }

    public int myLengthForProbArray() {

        return this.probArray.size();

    }

}
