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
import static com.sun.xml.internal.ws.streaming.XMLStreamReaderUtil.close;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.SequenceListFileReader;
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
public class HmmClassifier implements ISequenceClassifier<List<ObservationDiscrete<HmmSequence.Packet>>>{

    protected Map<String,Hmm> classModel = new HashMap<>();
 
    @Override
    public Map<String, Hmm> getClassModel() {
        return classModel;
    }

    
    @Override
    public String classify(List<ObservationDiscrete<HmmSequence.Packet>> representation) {
        //For every class
        double dMaxProb = -1.0;
        String sResult = null;
        
        for (String sClassName : classModel.keySet()) { 
            //Get the model
            Hmm hTempModel = classModel.get(sClassName);  
            //Get the probability of the representation given the model
            double dProb = hTempModel.probability(representation);
            //Select the model with the highest probability
            if (dProb > dMaxProb) {
                dMaxProb = dProb;
                sResult = sClassName;   
                //System.out.println(sResult);
            }
            // DEBUG
//            else {
//                System.out.println("Not assigned: :" + dProb);
//            }
        }
        if(sResult==null)
            System.out.println(classModel.keySet().size());
        // Return the class 
        return sResult;
    }

     // train: ayta ta seq pane se ayto to label :1)train hmm 2) save th antistoixish
    @Override
    public void train(List<List<ObservationDiscrete<HmmSequence.Packet>>> representation, String sLabel) {
        // Create a temporary HMM model
        Hmm hmmTmp = buildInitHmm();
        // Train the model based on the observations
        BaumWelchLearner bwl = new BaumWelchLearner();
        //hmmTmp = bwl.learn(hmmTmp, representation);
        // Store the model in the classModel map
        classModel.put(sLabel, hmmTmp);
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
