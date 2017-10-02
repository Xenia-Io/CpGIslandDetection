/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection;

import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphAnalyzer;
import gr.demokritos.iit.cpgislanddetection.analysis.NGramGraphClassifier;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class NGGClassifier {

    public static void classify() {

        NGramGraphClassifier nGramGraphClassifier = new NGramGraphClassifier();
        SequenceListFileReader reader = new SequenceListFileReader();
        FASTAFileReader fReader = new FASTAFileReader();

        List<List<DocumentNGramGraph>> representationForCpG;
        List<List<DocumentNGramGraph>> representationForNonCpG;

        ArrayList<BaseSequence> listOfBaseSeqForCpG = new ArrayList<>();
        ArrayList<BaseSequence> listOfBaseSeqForNonCpG = new ArrayList<>();

        //listOfBaseSeqForCpG = reader.getSequencesFromFile("data/posSamples.txt");
        listOfBaseSeqForNonCpG = reader.getSequencesFromFile("data/negSamples.txt");

        listOfBaseSeqForCpG = fReader.getSequencesFromFile("data/testForPosCPG_Fasta.txtSMALL");

        NGramGraphAnalyzer myAnalyst = new NGramGraphAnalyzer();
        representationForCpG = myAnalyst.analyze(listOfBaseSeqForCpG);
        System.out.println("size = " + listOfBaseSeqForCpG.size());
        representationForNonCpG = myAnalyst.analyze(listOfBaseSeqForNonCpG);
        System.out.println("size = " + listOfBaseSeqForNonCpG.size());

        //System.out.println("representation = "+representationForCpG.toString());
        //System.out.println("representation = "+representationForNonCpG.toString());
        // TODO: Train with negatives and positive samples
        nGramGraphClassifier.train(representationForCpG, "isCpG");
        nGramGraphClassifier.train(representationForNonCpG, "notCpG");
        int i = 0;
        //System.out.println("representationForCpG size = " + representationForCpG.size());
        for (List<DocumentNGramGraph> representation1 : representationForCpG) {
            //System.out.println("representation1 SIZEEE = " + representation1.size());
            String result = nGramGraphClassifier.classify(representation1);
            i++;
            System.out.println("i = " + i);
            System.out.println("RESULT = " + result);
        }

    }
}
