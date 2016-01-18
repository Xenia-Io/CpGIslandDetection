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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class HmmClassifier implements ISequenceClassifier<List<List<ObservationDiscrete<HmmSequence.Packet>>>>{

    
    @Override
    public String classify(List<List<ObservationDiscrete<HmmSequence.Packet>>> representation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void train(List<List<ObservationDiscrete<HmmSequence.Packet>>> representation, String sLabel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static List<Double> computeProbability(ArrayList<BaseSequence> baseSeq) throws IOException {

        List<Double> doubleList = new ArrayList<Double>();
        List<List<ObservationDiscrete<HmmSequence.Packet>>> testSequence = new ArrayList<>();;
       
        //create the appropriate representation for my stringl-list from a file
        HmmAnalyzer h = new HmmAnalyzer();
        testSequence = h.analyze(baseSeq);
        
        /* Baum-Welch learning */
		
		BaumWelchLearner bwl = new BaumWelchLearner();
		
		Hmm<ObservationDiscrete<HmmSequence.Packet>> myHmm = buildInitHmm();
                
                // Incrementally improve the solution
//		for (int i = 0; i < baseSeq.size(); i++) {
//                    
//                    mySequence.add( buildSequence(i,baseSeq) );
//                    
//			
//		}
                
                for (int i = 0; i < baseSeq.size(); i++) {
			
                    myHmm = bwl.learn(myHmm, testSequence);
                    myHmm = bwl.iterate(myHmm, testSequence);
                    System.out.println(myHmm);
			doubleList.add(myHmm.probability(testSequence.get(i)));
		}
                
//                 for (int i = 0; i < baseSeq.size(); i++) {
//			
//			System.out.println("Sequence probability: " +
//				myHmm.probability(mySequence.get(i)));
//		}
                
		
        
        return doubleList;
    }
    
    
    
//    public static List<Double> computePositiveProbability(ArrayList<BaseSequence> baseSeq){
//    
//        double arrayForCounting[][] = new double[baseSeq.size()][4];
//        int countAden = 0, countCyt = 0, countThym = 0, countGouan = 0;
//        double[] mean = new double[baseSeq.size()];
//        double[] Var = new double[baseSeq.size()];
//        double[] aden = new double[baseSeq.size()];
//        double[] thym = new double[baseSeq.size()];
//        double[] cyt = new double[baseSeq.size()];
//        double[] gouan = new double[baseSeq.size()];
//        
//        List<Double> doubleList = new ArrayList<Double>();
//        List<List<ObservationDiscrete<HmmSequence.Packet>>> testSequence;
//        Hmm<ObservationDiscrete<HmmSequence.Packet>> learntHmm = buildInitHmm();;
//        Hmm<ObservationDiscrete<HmmSequence.Packet>> hmm=null;
//        
//        /* Baum-Welch learning */
//        BaumWelchLearner bwl = new BaumWelchLearner();
//        
//        //create the appropriate representation for my stringl-list from a file
//        HmmAnalyzer h = new HmmAnalyzer();
//        testSequence = h.analyze(baseSeq);
//        //System.out.println(testSequence);
//        //gia to ka8e stoixeio ths listas vriskw to plithos twn A,T,C,G kai ta apo8hkeuw se enan pinaka
//        for (int line = 0; line < baseSeq.size(); line++) {
//
//            countAden = 0;
//            countCyt = 0;
//            countGouan = 0;
//            countThym = 0;
//
//            for (int i = 0; i < baseSeq.get(line).getSymbolSequence().length(); i++) {
//
//                if (baseSeq.get(line).myCharAt(i,baseSeq.get(line).getSymbolSequence()) == 'A') {
//
//                    countAden++;
//                    arrayForCounting[line][0] = countAden;
//
//                } else if (baseSeq.get(line).myCharAt(i,baseSeq.get(line).getSymbolSequence()) == 'T') {
//
//                    countThym++;
//                    arrayForCounting[line][1] = countThym;
//
//                } else if (baseSeq.get(line).myCharAt(i,baseSeq.get(line).getSymbolSequence()) == 'C') {
//
//                    countCyt++;
//                    arrayForCounting[line][2] = countCyt;
//
//                } else if (baseSeq.get(line).myCharAt(i,baseSeq.get(line).getSymbolSequence()) == 'G') {
//
//                    countGouan++;
//                    arrayForCounting[line][3] = countGouan;
//
//                }
//
//            }
//         
//            //compute emission probabilities
//            aden[line] = (arrayForCounting[line][0] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
//            thym[line] = (arrayForCounting[line][1] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
//            cyt[line] = (arrayForCounting[line][2] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
//            gouan[line] = (arrayForCounting[line][3] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
//
//            //build learntHmm and hmm for each sequence
//            
//            hmm = buildHmm(aden[line], thym[line], cyt[line], gouan[line]);
//            //System.out.println(learntHmm);
//            //System.out.println(hmm);
//            
//            System.out.println(testSequence.get(line));
//            
//            List<ObservationDiscrete<HmmSequence.Packet>> a = testSequence.get(line);
//            if(a==null)
//            {
//                System.out.println("Null ignoring !!!");
//            
//
//            }else
//            {
//                try{
//                     hmm.probability(a);
//                }catch(NullPointerException e){
//                 
//                     System.err.println("Could not calculate probability of a:");
//                     System.err.println(a.toString());
//                }
//                
//                
//            }
//           
//            // doubleList.add(learntHmm.probability(testSequence.get(line)));
////            doubleList.add(learntHmm.probability(testSequence.get(line)));
////              System.out.println(learntHmm.probability(testSequence.get(line)));
//            // for (int j = 0; j < testSequence.size(); j++) {
//            //doubleList.add(learntHmm.probability(testSequence.get(j)));
//            //}
//         
//            
//    }          
//return doubleList;
//    
//}
    
    static Hmm<ObservationDiscrete<HmmSequence.Packet>> buildHmm(double a, double t, double c, double g) {

        Hmm<ObservationDiscrete<HmmSequence.Packet>> hmm
                = new Hmm<ObservationDiscrete<HmmSequence.Packet>>(2,
                        new OpdfDiscreteFactory<HmmSequence.Packet>(HmmSequence.Packet.class));

        //initial probabilities
        //we give in the 2nd class bigger probability as we implements a model that fits with CpG islands
        hmm.setPi(0, 0.05);
        hmm.setPi(1, 0.95);

        //emissions probabilities
        hmm.setOpdf(0, new OpdfDiscrete<HmmSequence.Packet>(HmmSequence.Packet.class,
                new double[]{0.25, 0.25, 0.25, 0.25}));
        hmm.setOpdf(1, new OpdfDiscrete<HmmSequence.Packet>(HmmSequence.Packet.class,
                new double[]{a, t, c, g}));

        //transitions between the 2 classes - we give bigger probability for "CpG state"
        hmm.setAij(0, 1, 1);
        hmm.setAij(0, 0, 0);
        hmm.setAij(1, 0, 0);
        hmm.setAij(1, 1, 1);

        return hmm;
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

    /* Generate several observation sequences using a HMM */
    static <O extends Observation> List<List<O>>
            generateSequences(Hmm<O> hmm) {
        MarkovGenerator<O> mg = new MarkovGenerator<O>(hmm);

        List<List<O>> sequences = new ArrayList<List<O>>();
        for (int i = 0; i < 200; i++) {
            sequences.add(mg.observationSequence(100));
        }

        return sequences;
    }

}
