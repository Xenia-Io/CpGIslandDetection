/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.io;

import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import weka.core.Attribute;
 import weka.core.FastVector;
 import weka.core.Instance;
 import weka.core.Instances;
/**
 *
 * @author Xenia
 */
public class FileCreatorARFF {
    
    public Instances createARFF(List<Vector<Integer>> listVector, String nameClass) throws ParseException{
    
        // Declare four numeric attributes
         Attribute Attribute1 = new Attribute("adenine");
         Attribute Attribute2 = new Attribute("thymine");
         Attribute Attribute3 = new Attribute("cytosine");
         Attribute Attribute4 = new Attribute("guanine");
          
         // Declare the class attribute along with its values
         FastVector fvClassVal = new FastVector(2);
         fvClassVal.addElement("yes");
         fvClassVal.addElement("no");
         Attribute ClassAttribute = new Attribute("theClass", fvClassVal);
          
         // Declare the feature vector
         FastVector fvWekaAttributes = new FastVector(5);
         fvWekaAttributes.addElement(Attribute1);    
         fvWekaAttributes.addElement(Attribute2);    
         fvWekaAttributes.addElement(Attribute3); 
         fvWekaAttributes.addElement(Attribute4); 
         fvWekaAttributes.addElement(ClassAttribute);
          
         // Create an empty training set
         int capacity = listVector.size() + 7;
         Instances isTrainingSet = new Instances("isCpG", fvWekaAttributes, capacity);       
          
         // Set class index
         isTrainingSet.setClassIndex(4);
             
         // Create the instances from the file with vectors
         for(int i=0; i<listVector.size(); i++){
               Instance instance = new Instance(5);
               instance.setValue((Attribute)fvWekaAttributes.elementAt(0), listVector.get(i).get(0));
               instance.setValue((Attribute)fvWekaAttributes.elementAt(1), listVector.get(i).get(1));
               instance.setValue((Attribute)fvWekaAttributes.elementAt(2), listVector.get(i).get(2));
               instance.setValue((Attribute)fvWekaAttributes.elementAt(3), listVector.get(i).get(3));
               instance.setValue((Attribute)fvWekaAttributes.elementAt(4), nameClass);
              
                //add the instance in training set
                isTrainingSet.add(instance);
              
         }
         System.out.println(isTrainingSet);
         return isTrainingSet;
    }
    
}
