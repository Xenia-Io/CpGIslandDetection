/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.HmmClassifier;
import gr.demokritos.iit.cpgislanddetection.analysis.ISequenceClassifier;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.entities.WekaHMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

/**
 *
 * @author Xenia
 */
public class HiddenMarkovModelClassifier {

    public static void classify() throws IOException, Exception {

        // Init classifier
        ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();

        FASTAFileReader rPOS = new FASTAFileReader();
        FASTAObfuscatorReader rNEG = new FASTAObfuscatorReader();

        List<BaseSequence> baseS = rPOS.getSequencesFromFile("data\\testForPosCPG_Fasta.txt");
        List<BaseSequence> baseSNonCpG = rNEG.getSequencesFromFile("data\\testForPosCPG_Fasta.txt");

        System.out.println("baseSNonCpG size=" + baseSNonCpG.size());
        System.out.println("baseS size=" + baseS.size());

//        ArrayList<BaseSequence> baseS_test = rPOS.getSequencesFromFile("data/testForPosCPG_Fasta.txt");
//        ArrayList<BaseSequence> baseSNonCpG_test = rNEG.getSequencesFromFile("data/testForPosCPG_Fasta.txt");
        // Extract test instances
        List<BaseSequence> baseS_test = baseS.subList(baseS.size() - 100, baseS.size());
        List<BaseSequence> baseSNonCpG_test = baseSNonCpG.subList(baseSNonCpG.size() - 100, baseSNonCpG.size());

        System.out.println("baseSNonCpG_test size=" + baseSNonCpG_test.size());
        System.out.println("baseS_test size=" + baseS_test.size());

        // Update training instances (by removing the selected test instances)
//        baseS = baseS.subList(0, baseS.size() - 100);
//        baseSNonCpG = baseSNonCpG.subList(0, baseS.size() - 100);
        baseS = baseS.subList(0, 200);
        baseSNonCpG = baseSNonCpG.subList(0, 200);

        System.out.println("TRAIN: sizeOfPos = " + baseS.size() + " " + "sizeOfNeg = " + baseSNonCpG.size());
        System.out.println("TEST: sizeOfPos = " + baseS_test.size() + " "
                + "sizeOfNeg = " + baseSNonCpG_test.size());

        //System.out.println("array of base sequence size = " + baseS.size());
        HmmAnalyzer v = new HmmAnalyzer();

        List<List<ObservationDiscrete<HmmSequence.Packet>>> lRes = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lResNon = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lRes_test = new ArrayList<>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> lResNon_test = new ArrayList<>();

        lRes = v.analyze(baseS);
        lResNon = v.analyze(baseSNonCpG);
        lRes_test = v.analyze(baseS_test);
        lResNon_test = v.analyze(baseSNonCpG_test);

        classifier.train(lRes, "isCpG");
        classifier.train(lResNon, "isNotCpG");

        ArrayList<HMMFeatureVector> cpgTrainingVectors = new ArrayList<>();
        ArrayList<HMMFeatureVector> notCpgTrainingVectors = new ArrayList<>();
        ArrayList<HMMFeatureVector> cpgTestingVectors = new ArrayList<>();
        ArrayList<HMMFeatureVector> notCpgTestingVectors = new ArrayList<>();

        // Getting the feature vectors for each of our sequence lists 
        for (List<ObservationDiscrete<HmmSequence.Packet>> CpGinstanceRepresentation : lRes) {
            cpgTrainingVectors.add((HMMFeatureVector) classifier.getFeatureVector(CpGinstanceRepresentation, "positive cpg"));
        }

        for (List<ObservationDiscrete<HmmSequence.Packet>> NonCpGinstanceRepresentation : lResNon) {
            notCpgTrainingVectors.add((HMMFeatureVector) classifier.getFeatureVector(NonCpGinstanceRepresentation, "negative cpg"));
        }

        for (List<ObservationDiscrete<HmmSequence.Packet>> CpGinstanceRepresentation : lRes_test) {
            cpgTestingVectors.add((HMMFeatureVector) classifier.getFeatureVector(CpGinstanceRepresentation, "positive cpg"));
        }

        for (List<ObservationDiscrete<HmmSequence.Packet>> NoNCpGinstanceRepresentation : lResNon_test) {
            notCpgTestingVectors.add((HMMFeatureVector) classifier.getFeatureVector(NoNCpGinstanceRepresentation, "negative cpg"));
        }

        for (int i = 0; i < lRes_test.size(); i++) {
            System.out.println("cpg testing vector: <" + cpgTestingVectors.get(i).getProbArrayAtIndex(0)
                    + ", " + cpgTestingVectors.get(i).getProbArrayAtIndex(1) + ">" + " cpg testing vector label = " + cpgTestingVectors.get(i).getLabel());
            //System.out.println("cpg testing vector label = " + cpgTestingVectors.get(i).getLabel());
        }

        for (int i = 0; i < lResNon_test.size(); i++) {
            System.out.println("NOTcpg testing vector: <" + notCpgTestingVectors.get(i).getProbArrayAtIndex(0)
                    + ", " + notCpgTestingVectors.get(i).getProbArrayAtIndex(1) + ">" + " NOTcpg testing vector label = " + notCpgTestingVectors.get(i).getLabel());
            // System.out.println("NOTcpg testing vector label = " + notCpgTestingVectors.get(i).getLabel());
        }

        // 1st ALTERNATIVE
        // Classify an instance to the label of the HMM it is most similar to (i.e. HMM which return higher likelihood)
        // 2nd ALTERNATIVE
        // TODO: From vectors convert to WEKA instances
        // TODO: Train classifier based on training instances
        // TOOD: Test classifier (using the trained model) on the TEST instances
        //----------START 2nd ALTERNATIVE-----------------------------------
        // TODO: From vectors convert to WEKA instances            
        //Create and Get the Weka HMMfeature vectors
        WekaHMMFeatureVector HMMfv = new WekaHMMFeatureVector();

        System.out.println("1_ : cpgTrainingVectors size =====" + cpgTrainingVectors.size());
        System.out.println("2_ : cpgTestingVectors size =====" + cpgTestingVectors.size());
        Instances Training_Instances = HMMfv.fillInstanceSet(cpgTrainingVectors, notCpgTrainingVectors);
        Instances Testing_Instances = HMMfv.fillInstanceSet(cpgTestingVectors, notCpgTestingVectors);

        // TODO: Train classifier based on training instances
        // Create a naïve bayes classifier
//        Classifier cModel = (Classifier)new NaiveBayes();
//        cModel.buildClassifier(Training_Instances);
        // Test the model
        //Evaluation evaluator = new Evaluation(Training_Instances);
        //evaluator.evaluateModel(cModel, Testing_Instances);
        // Print the result à la Weka explorer:
        //String strSummary = evaluator.toSummaryString();
        //System.out.println(strSummary);
        // Get the confusion matrix
        //double[][] cmMatrix = evaluator.confusionMatrix();
    }

}
