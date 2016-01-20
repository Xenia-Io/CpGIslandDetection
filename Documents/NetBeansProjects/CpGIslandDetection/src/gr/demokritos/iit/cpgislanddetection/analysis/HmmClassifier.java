/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscreteFactory;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.toolbox.KullbackLeiblerDistanceCalculator;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.ARSSFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Xenia
 */
public class HmmClassifier implements ISequenceClassifier<List<List<ObservationDiscrete<HmmSequence.Packet>>>>{

    
    @Override
    public String classify(List<List<ObservationDiscrete<HmmSequence.Packet>>> representation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     // train: ayta ta seq pane se ayto to label :1)train hmm 2) save th antistoixish
    @Override
    public void train(List<List<ObservationDiscrete<HmmSequence.Packet>>> representation, String sLabel) {
       
        HmmClassifier hmm = new HmmClassifier();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> representationPos;
        List<List<ObservationDiscrete<HmmSequence.Packet>>> representationNeg;
        /* Baum-Welch learning */
  
        BaumWelchLearner bwl = new BaumWelchLearner();

        Hmm<ObservationDiscrete<HmmSequence.Packet>> myHmm = buildInitHmm();

        
        // Read  file
        IGenomicSequenceFileReader readerPos = new ARSSFileReader();
        IGenomicSequenceFileReader readerNeg = new ARSSFileReader();
        
        
        ArrayList<BaseSequence> posBaseSeq = readerPos.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\negSamples.txt");
        ArrayList<BaseSequence> negBaseSeq = readerNeg.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\negSamples.txt");
        
        
        List<List<ObservationDiscrete<HmmSequence.Packet>>> positiveSequences = new ArrayList<>();;
        List<List<ObservationDiscrete<HmmSequence.Packet>>> negativeSequences = new ArrayList<>();;

        //create the appropriate representation for my stringl-list from a file
        HmmAnalyzer hPos = new HmmAnalyzer();
        HmmAnalyzer hNeg = new HmmAnalyzer();

        positiveSequences = hPos.analyze(posBaseSeq);
        negativeSequences = hNeg.analyze(negBaseSeq);
       
        try {
            representationPos = hmm.createRepresentation(posBaseSeq);
            representationNeg = hmm.createRepresentation(negBaseSeq);
            
            
            // Learn HMM
            myHmm = bwl.learn(myHmm, representationPos);
            myHmm = bwl.learn(myHmm, representationNeg);

        
        } catch (IOException ex) {
            Logger.getLogger(HmmClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        //map of dataset files
        IGenomicSequenceFileReader reader = new ARSSFileReader();
        Map<String, URL> trainingFiles = new HashMap<>();
        trainingFiles.put("No Cpg Island", HmmClassifier.class.getResource("C:\\Users\\Xenia\\Desktop\\negSamples.txt"));
        trainingFiles.put("Cpg Island", HmmClassifier.class.getResource("C:\\Users\\Xenia\\Desktop\\posSamples.txt"));

        //loading in memory
        Map<String, String[]> trainingExamples = new HashMap<>();
        for(Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
            
            try {
                trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
            } catch (IOException ex) {
                Logger.getLogger(HmmClassifier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    public static List<List<ObservationDiscrete<HmmSequence.Packet>>> createRepresentation(ArrayList<BaseSequence> baseSeq) throws IOException {

        List<List<ObservationDiscrete<HmmSequence.Packet>>> testSequences = new ArrayList<>();;
       
        //create the appropriate representation for my stringl-list from a file
        HmmAnalyzer h = new HmmAnalyzer();
        testSequences = h.analyze(baseSeq);
        
        return testSequences;
    }
   

    /* Initial guess for the Baum-Welch algorithm */
    static Hmm<ObservationDiscrete<HmmSequence.Packet>> buildInitHmm() {

        Hmm<ObservationDiscrete<HmmSequence.Packet>> hmm
                = new Hmm<ObservationDiscrete<HmmSequence.Packet>>(2,
                        new OpdfDiscreteFactory<HmmSequence.Packet>(HmmSequence.Packet.class));

        hmm.setPi(0, 0.50);
        hmm.setPi(1, 0.50);

        hmm.setOpdf(0, new OpdfDiscrete<HmmSequence.Packet>(HmmSequence.Packet.class,
                new double[]{0.2, 0.2, 0.2, 0.2, 0.2}));
        hmm.setOpdf(1, new OpdfDiscrete<HmmSequence.Packet>(HmmSequence.Packet.class,
                new double[]{0.2, 0.2, 0.2, 0.2, 0.2}));

        hmm.setAij(0, 1, 0.25);
        hmm.setAij(0, 0, 0.25);
        hmm.setAij(1, 0, 0.25);
        hmm.setAij(1, 1, 0.25);

        return hmm;
    } 

    private String[] readLines(URL value) throws IOException {
        Reader fileReader = new InputStreamReader(value.openStream(), Charset.forName("UTF-8"));
        List<String> lines;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines.toArray(new String[lines.size()]);
    }

}
