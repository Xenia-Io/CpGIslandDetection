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
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.FASTAObfuscatorReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class RuleBasedClassifier {

    public static void classify() {

        // Init classifier
        ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>> classifier
                = new HmmClassifier();

        FASTAFileReader rPOS = new FASTAFileReader();
        FASTAObfuscatorReader rNEG = new FASTAObfuscatorReader();

        List<BaseSequence> baseS = rPOS.getSequencesFromFile("data\\testForPosCPG_Fasta.txt");
        List<BaseSequence> baseSNonCpG = rNEG.getSequencesFromFile("data\\testForPosCPG_Fasta.txt");

        System.out.println("baseSNonCpG size=" + baseSNonCpG.size());
        System.out.println("baseS size=" + baseS.size());

        // Extract test instances
        List<BaseSequence> baseS_test = baseS.subList(baseS.size() - 100, baseS.size());
        List<BaseSequence> baseSNonCpG_test = baseSNonCpG.subList(baseSNonCpG.size() - 100, baseSNonCpG.size());

        System.out.println("baseSNonCpG_test size=" + baseSNonCpG_test.size());
        System.out.println("baseS_test size=" + baseS_test.size());

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

        CpGIslandIdentification i = new CpGIslandIdentification();

        //create variables to store successes and failures for each class
        int successForCpG = 0, failForCpG = 0, successForNotCpG = 0, failForNotCpG = 0;
        int size = baseS_test.size() + baseSNonCpG_test.size();

        boolean[] resultArrayForBaseS_test = new boolean[baseS_test.size()];
        boolean[] resultArrayForBaseSNonCpG_test = new boolean[baseSNonCpG_test.size()];

        for (int j = 0; j < baseS_test.size(); j++) {
            resultArrayForBaseS_test[j] = i.identify(baseS_test.get(j).getSymbolSequence());

            if (resultArrayForBaseS_test[j] == true && cpgTestingVectors.get(j).getLabel() == "positive cpg") {
                successForCpG++;
            } else {
                failForCpG++;
            }
        }

        for (int j = 0; j < baseSNonCpG_test.size(); j++) {
            resultArrayForBaseSNonCpG_test[j] = i.identify(baseSNonCpG_test.get(j).getSymbolSequence());

            if (resultArrayForBaseSNonCpG_test[j] == true && notCpgTestingVectors.get(j).getLabel() == "negative cpg") {
                successForNotCpG++;
            } else {
                failForNotCpG++;
            }
        }

        //Evaluation: calculation of classification rate and accuracy
        double totalAccuracy = (0.0 + successForNotCpG + successForCpG) / (0.0 + successForCpG + failForCpG + failForNotCpG + successForNotCpG);

        //missclassification rate for CpG class
        double rate1 = (failForCpG + successForCpG) != 0
                ? (0.0 + failForCpG) / (failForCpG + successForCpG)
                : 0.0;

        //missclassification rate for Not CpG class
        double rate2 = (failForNotCpG + successForNotCpG) != 0
                ? (0.0 + failForNotCpG) / (failForNotCpG + successForNotCpG)
                : 0.0;

        System.out.println("RESULTS FOR RULE-BASED METHOD: " + "total Accuracy = " + totalAccuracy + " rate1 = " + rate1 + " rate2  = " + rate2);

    }

}
