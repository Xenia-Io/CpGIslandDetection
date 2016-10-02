/* 
 * Copyright 2016 NCSR Demokritos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import gr.demokritos.iit.cpgislanddetection.io.FileCreatorARFF;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import static gr.demokritos.iit.jinsect.algorithms.statistics.ChiSquareDistributionBase.data;
import java.util.List;
import java.util.Vector;
import weka.classifiers.*;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import weka.classifiers.*;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.converters.ArffLoader;

/**
 *
 * @author Xenia
 */
public class VectorSequenceDetector implements ICpGSequenceDetector {

    // Initialize classifier
    // Train classifier
    public VectorSequenceDetector(List<BaseSequence> sequences, List<String> labels) throws FileNotFoundException, IOException, Exception {

        //gia ola ta seq
        //gia kathe seq pare to vector me vash ton analyzer 
        //vale kai to label
        //kai update classify
        
        // load data
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("/Desktop/filesForWeka/2o_peirama/dataForWeka.arff"));
        Instances structure = loader.getStructure();
        // setting class attribute
        structure.setClassIndex(structure.numAttributes() - 1);
        
        // train NaiveBayes
        NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
        nb.buildClassifier(structure);
        Instance current;
        while ((current = loader.getNextInstance(structure)) != null)
          nb.updateClassifier(current);
    }

     // Classify one sequence using your existing classifier
     // return true if the label indicates a CpG
    //edw kanw to test me ton epanw classifier 
    @Override
    public boolean detect(BaseSequence seq) {
        
        //metasxhmatizei to baseSeq se vector gia to weka-->kalw thn analyze
        //kalw ton classifier
        
        return true;
       
    }
    
}
