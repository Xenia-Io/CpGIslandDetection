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
package gr.demokritos.iit.cpgislanddetection;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.ISequenceClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorPreProcessor;
import gr.demokritos.iit.cpgislanddetection.analysis.VectorSequenceDetector;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.entities.WekaHMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

/**
 *
 * @author Xenia
 */
public class CpGIslandDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, Exception {

        VClassifier.classify();
        //RuleBasedClassifier.classify();
        //HiddenMarkovModelClassifier.classify();
        //NGGClassifier.classify();
    }
}
