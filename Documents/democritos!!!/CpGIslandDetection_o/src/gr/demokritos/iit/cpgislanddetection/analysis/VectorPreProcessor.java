/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.io.FileCreatorARFF;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import weka.core.Instances;

/**
 *
 * @author Xenia
 */
public class VectorPreProcessor {

    public Instances createTrainingInstances(String filePathForPositivesamples, String filePathForNegativeSamples) throws ParseException {

        //read sequences from txt files
        SequenceListFileReader reader = new SequenceListFileReader();
        ArrayList<BaseSequence> lSeqs1 = new ArrayList<>();
        ArrayList<BaseSequence> lSeqs2 = new ArrayList<>();

        lSeqs1 = reader.getSequencesFromFile(filePathForPositivesamples);
        lSeqs2 = reader.getSequencesFromFile(filePathForNegativeSamples);

        //create vectors for every sequence
        List<Vector<Integer>> listVectorForPositiveSamples = new ArrayList<>();
        List<Vector<Integer>> listVectorForNegativeSamples = new ArrayList<>();

        VectorAnalyzer v = new VectorAnalyzer();
        listVectorForNegativeSamples = v.analyze(lSeqs2);
        listVectorForPositiveSamples = v.analyze(lSeqs1);

        //add a parameter that indicates if vector is positive or negative sample
        for (int i = 0; i < listVectorForPositiveSamples.size(); i++) {
            listVectorForPositiveSamples.get(i).add(1);
        }
        for (int i = 0; i < listVectorForNegativeSamples.size(); i++) {
            listVectorForNegativeSamples.get(i).add(0);
        }

        //create training set
        List<Vector<Integer>> listVectorForTrainingSet = new ArrayList<>(listVectorForPositiveSamples);
        listVectorForTrainingSet.addAll(listVectorForNegativeSamples);

        //create ARFF files for positive and negative samples
        FileCreatorARFF fc = new FileCreatorARFF();
        Instances trainingInstances = fc.createARFF(listVectorForTrainingSet);

        return trainingInstances;
    }

    public List<Vector<Integer>> createTestingSet(String filePathForTestingSet) throws ParseException {
        SequenceListFileReader reader = new SequenceListFileReader();
        ArrayList<BaseSequence> testSeq = new ArrayList<>();

        testSeq = reader.getSequencesFromFile(filePathForTestingSet);

        List<Vector<Integer>> listVectorForTestingSamples = new ArrayList<>();

        VectorAnalyzer v = new VectorAnalyzer();
        listVectorForTestingSamples = v.analyze(testSeq);

        return listVectorForTestingSamples;
    }

}
