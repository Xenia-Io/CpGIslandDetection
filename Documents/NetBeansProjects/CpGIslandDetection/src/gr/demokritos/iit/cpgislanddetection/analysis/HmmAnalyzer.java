/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import be.ac.ulg.montefiore.run.distributions.GaussianDistribution;
import java.util.*;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import be.ac.ulg.montefiore.run.jahmm.ObservationVector;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscreteFactory;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;
import be.ac.ulg.montefiore.run.jahmm.io.FileFormatException;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationSequencesReader;
import be.ac.ulg.montefiore.run.jahmm.io.ObservationVectorReader;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.toolbox.KullbackLeiblerDistanceCalculator;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import static javax.management.Query.value;
import java.io.ObjectOutputStream;
import java.util.Vector;
import static javafx.scene.input.KeyCode.O;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence.Packet;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;

/**
 * HmmAnalyzer creates a Hidden Markov Model from a List of Strings or
 * Observations (like buildSequence function)
 *
 * @author Xenia
 */
public class HmmAnalyzer implements ISequenceAnalyst<List<ObservationDiscrete<Packet>>> {

    @Override
    public List<ObservationDiscrete<Packet>> analyze(ArrayList<BaseSequence> baseSeq) {

        int countAden, countThym, countGouan, countCyt;
        double arrayForCounting[][] = new double[baseSeq.size()][4];
        double[] aden = new double[baseSeq.size()];
        double[] thym = new double[baseSeq.size()];
        double[] cyt = new double[baseSeq.size()];
        double[] gouan = new double[baseSeq.size()];

        List<ObservationDiscrete<Packet>> testSeq = new ArrayList<ObservationDiscrete<Packet>>();

        ObservationDiscrete<Packet> packetA = Packet.A.observation();
        ObservationDiscrete<Packet> packetT = Packet.T.observation();
        ObservationDiscrete<Packet> packetC = Packet.C.observation();
        ObservationDiscrete<Packet> packetG = Packet.G.observation();

        for (int line = 0; line < baseSeq.size(); line++) {

            countAden = 0;
            countCyt = 0;
            countGouan = 0;
            countThym = 0;

            String currentStr = baseSeq.get(line).getSymbolSequence();

            for (int i = 0; i < baseSeq.get(line).myLength(currentStr); i++) {

                if (baseSeq.get(line).myCharAt(i, currentStr) == 'A') {

                    // Count base appearences and save it
                    countAden++;
                    arrayForCounting[line][0] = countAden;

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'T') {

                    // Count base appearences and save it
                    countThym++;
                    arrayForCounting[line][1] = countThym;

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'C') {

                    // Count base appearences and save it
                    countCyt++;
                    arrayForCounting[line][2] = countCyt;

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'G') {

                    // Count base appearences and save it
                    countGouan++;
                    arrayForCounting[line][3] = countGouan;

                }

            }

            //compute emission probabilities
            aden[line] = (arrayForCounting[line][0] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
            thym[line] = (arrayForCounting[line][1] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
            cyt[line] = (arrayForCounting[line][2] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
            gouan[line] = (arrayForCounting[line][3] / (arrayForCounting[line][0] + arrayForCounting[line][1] + arrayForCounting[line][2] + arrayForCounting[line][3]));
            
            for (int i = 0; i < baseSeq.get(line).myLength(currentStr); i++) {
                if (baseSeq.get(line).myCharAt(i, currentStr) == 'A') {

                    testSeq.add(packetA);

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'T') {

                    testSeq.add(packetT);

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'C') {

                    testSeq.add(packetC);

                } else if (baseSeq.get(line).myCharAt(i, currentStr) == 'G') {

                    testSeq.add(packetG);

                }
            }
        }

        return testSeq;
    }
}
