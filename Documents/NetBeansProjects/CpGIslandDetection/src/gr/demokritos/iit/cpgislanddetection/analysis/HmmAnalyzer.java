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
 * HmmAnalyzer creates a sequence of A,T,C,G from a List of Strings or
 * Observations (like buildSequence function) returns a
 * List<ObservationDiscrete<Packet>>
 *
 * @author Xenia
 */
public class HmmAnalyzer implements ISequenceAnalyst<List<ObservationDiscrete<Packet>>> {

    @Override
    public List<ObservationDiscrete<Packet>> analyze(ArrayList<BaseSequence> baseSeq) {

        List<ObservationDiscrete<Packet>> testSeq
                = new ArrayList<ObservationDiscrete<Packet>>();

        ObservationDiscrete<Packet> packetA = Packet.A.observation();
        ObservationDiscrete<Packet> packetT = Packet.T.observation();
        ObservationDiscrete<Packet> packetC = Packet.C.observation();
        ObservationDiscrete<Packet> packetG = Packet.G.observation();

        for (int line = 0; line < baseSeq.size(); line++) {
            String currentStr = baseSeq.get(line).getSymbolSequence();
            System.out.println(currentStr);
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
            System.out.println(testSeq);
        }

        return testSeq;
    }
}
