/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ArffSaver;

/**
 *
 * @author Xenia
 */
public class WekaHMMFeatureVector {
    
  
    
    public FastVector initializeWekaFeatureVector() {
         //https://weka.wikispaces.com/Programmatic+Use 
          
        //Declaration of the numeric value dMaxProb
        Attribute Attribute1 = new Attribute("Prob1");
        Attribute Attribute2 = new Attribute("Prob2");

        //Declare the class attribute along with its values
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("CpG island");
        fvClassVal.addElement("Not CpG island");
        
        Attribute ClassAttribute = new Attribute("theClass", fvClassVal);

        // Declare the feature vector
        FastVector fvWekaAttributesHMM = new FastVector(3);
        fvWekaAttributesHMM.addElement(Attribute1);
        fvWekaAttributesHMM.addElement(Attribute2);
        fvWekaAttributesHMM.addElement(ClassAttribute);

        return fvWekaAttributesHMM;
}
    
    public Instance fillFeatureVector(HMMFeatureVector vSource, Instances data) {
        double[] values = new double[data.numAttributes()];
        double min_prob;
//        System.out.println("debugging = "+ vSource.myLengthForProbArray());
        values[0] = vSource.getProbArrayAtIndex(0)/(vSource.getProbArrayAtIndex(0) + vSource.getProbArrayAtIndex(1));
        values[1] = vSource.getProbArrayAtIndex(1)/(vSource.getProbArrayAtIndex(0) + vSource.getProbArrayAtIndex(1));
        values[2] = data.attribute(2).indexOfValue(vSource.getLabel());
        
        Instance inst = new Instance(1.0, values);
        return inst;
}
    
    public Instances fillInstanceSet(ArrayList<HMMFeatureVector> vList, ArrayList<HMMFeatureVector> vList2) throws IOException {

        //FastVector fvWekaAttributesHmm = new FastVector(3);
        
        //fill FastVector variable with the ArrayList of Attribute
        FastVector fvWekaAttributesHmm = initializeWekaFeatureVector();
        //fvWekaAttributesHmm.addElement(attributes);
        int size = vList.size()+vList2.size();
        Instances isSet = new Instances("dataset", fvWekaAttributesHmm, vList.size());

        isSet.setClassIndex(isSet.numAttributes() - 1);

        for (HMMFeatureVector HMMv : vList) {

            Instance i = fillFeatureVector(HMMv, isSet);

            isSet.add(i);
        }
        
        for (HMMFeatureVector HMMv : vList2) {

            Instance i = fillFeatureVector(HMMv, isSet);

            isSet.add(i);
        }
        
        ArffSaver saver = new ArffSaver();
        saver.setInstances(isSet);
        saver.setFile(new File("./dataARFF/testWekaHMM.arff"));
        saver.writeBatch();

        return isSet;
}
    
}
