/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmmpositivecpg;

import java.util.*;
import be.ac.ulg.montefiore.run.jahmm.Hmm;
import be.ac.ulg.montefiore.run.jahmm.Observation;
import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscrete;
import be.ac.ulg.montefiore.run.jahmm.OpdfDiscreteFactory;
import be.ac.ulg.montefiore.run.jahmm.draw.GenericHmmDrawerDot;
import be.ac.ulg.montefiore.run.jahmm.learn.BaumWelchLearner;
import be.ac.ulg.montefiore.run.jahmm.toolbox.KullbackLeiblerDistanceCalculator;
import be.ac.ulg.montefiore.run.jahmm.toolbox.MarkovGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class HmmPositiveCpG {

	public enum Packet {
		A, T, C, G;
		
		public ObservationDiscrete<Packet> observation() {
			return new ObservationDiscrete<Packet>(this);
		}
	};
	
	
	static public void main(String[] argv) 
	throws java.io.IOException
	{	
		/* Build a HMM and generate observation sequences using this HMM */
		
		Hmm<ObservationDiscrete<Packet>> hmm = buildHmm();
		
		List<List<ObservationDiscrete<Packet>>> sequences;
		sequences = generateSequences(hmm);
		System.out.println(sequences);
               System.out.println(sequences.size());
		/* Baum-Welch learning */
		
		BaumWelchLearner bwl = new BaumWelchLearner();
		
		Hmm<ObservationDiscrete<Packet>> learntHmm = buildInitHmm();
		
		// This object measures the distance between two HMMs
		KullbackLeiblerDistanceCalculator klc = 
			new KullbackLeiblerDistanceCalculator();
		
		// Incrementally improve the solution
		for (int i = 0; i < 4; i++) {
			System.out.println("Distance at iteration " + i + ": " +
					klc.distance(learntHmm, hmm));
			learntHmm = bwl.iterate(learntHmm, sequences);
		}
		
		System.out.println("Resulting HMM:\n" + learntHmm);
		
		/* Computing the probability of a sequence */
		
		ObservationDiscrete<Packet> packetA = Packet.A.observation();
		ObservationDiscrete<Packet> packetT = Packet.T.observation();
                ObservationDiscrete<Packet> packetC = Packet.C.observation();
		ObservationDiscrete<Packet> packetG = Packet.G.observation();
		
		List<ObservationDiscrete<Packet>> testSequence = 
			new ArrayList<ObservationDiscrete<Packet>>(); 
		testSequence.add(packetA);
		testSequence.add(packetT);
		testSequence.add(packetC);
                testSequence.add(packetG);

		System.out.println("Sequence probability: " +
				learntHmm.probability(testSequence));
		
//		/* Write the final result to a 'dot' (graphviz) file. */
//		
//		(new GenericHmmDrawerDot()).write(learntHmm, "learntHmm.dot");
	}
	
	
	/* The HMM this example is based on */
	
	static Hmm<ObservationDiscrete<Packet>> buildHmm()
	{	
		Hmm<ObservationDiscrete<Packet>> hmm = 
			new Hmm<ObservationDiscrete<Packet>>(2,
					new OpdfDiscreteFactory<Packet>(Packet.class));
		
                //we give in the 2nd class bigger probability as we implements a model that fits with CpG islands
		hmm.setPi(0, 0.05);     //class for not-CpG-islands
		hmm.setPi(1, 0.95);     //class for CpG-islands
		
		hmm.setOpdf(0, new OpdfDiscrete<Packet>(Packet.class, 
				new double[] { 0.25, 0.25, 0.25, 0.25 }));
		hmm.setOpdf(1, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.05, 0.05, 0.45, 0.45 }));
		
                //transitions between the 2 classes - we give bigger probability for "CpG state"
		hmm.setAij(0, 1, 0.90);
		hmm.setAij(0, 0, 0.10);
		hmm.setAij(1, 0, 0.05);
		hmm.setAij(1, 1, 0.95);
		
		return hmm;
	}
	
	/* Initial guess for the Baum-Welch algorithm */
	
	static Hmm<ObservationDiscrete<Packet>> buildInitHmm()
	{	
		Hmm<ObservationDiscrete<Packet>> hmm = 
			new Hmm<ObservationDiscrete<Packet>>(2,
					new OpdfDiscreteFactory<Packet>(Packet.class));
		
		hmm.setPi(0, 0.50);
		hmm.setPi(1, 0.50);
		
		hmm.setOpdf(0, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.25, 0.25, 0.25, 0.25 }));
		hmm.setOpdf(1, new OpdfDiscrete<Packet>(Packet.class,
				new double[] { 0.05, 0.05, 0.45, 0.45 }));
		
		hmm.setAij(0, 1, 0.8);
		hmm.setAij(0, 0, 0.2);
		hmm.setAij(1, 0, 0.1);
		hmm.setAij(1, 1, 0.9);
		
		return hmm;
	}
		
	/* Generate several observation sequences using a HMM */
	
	static <O extends Observation> List<List<O>> 
	generateSequences(Hmm<O> hmm)
	{
		MarkovGenerator<O> mg = new MarkovGenerator<O>(hmm);
		
		List<List<O>> sequences = new ArrayList<List<O>>();
		for (int i = 0; i < 200; i++)
			sequences.add(mg.observationSequence(100));

		return sequences;
	}
    
}
