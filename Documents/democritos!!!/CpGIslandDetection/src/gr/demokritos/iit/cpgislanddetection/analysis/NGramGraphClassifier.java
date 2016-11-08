/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import be.ac.ulg.montefiore.run.jahmm.ObservationDiscrete;
import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.HMMFeatureVector;
import gr.demokritos.iit.cpgislanddetection.entities.HmmSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import gr.demokritos.iit.jinsect.events.GraphSimilarityComparatorAdapter;
import gr.demokritos.iit.jinsect.structs.ISimilarity;
import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Xenia
 */
/*
*Classify: pairnw to arxeio g ta positive CpG samples kai gia kathe ena apo ayta
*ftiaxnw grafo. Gia kathe grammi tou arxeiou pou testarw ftiaxnw ena grafo kai ton
*sugkrinw me kathe grammi tou arxeiou gia positive CpG samples. An vrw megalh 
*omoiothta sumperainw pws einai CpG.
*/
public class NGramGraphClassifier implements ISequenceClassifier<List<DocumentNGramGraph>>{

    protected Map<String,DocumentNGramGraph> classModel = new HashMap<>();
    
    
    @Override
    public Map<String, DocumentNGramGraph> getClassModel() {
        return classModel;
    }
    
    @Override
    public String classify(List<DocumentNGramGraph> representation) {
      
        //create reader for positive CpG samples
        IGenomicSequenceFileReader reader = new FASTAFileReader();  
        //create for each sequence a String (from file with positive CpG samples)
        ArrayList<BaseSequence> lSeqs;
        lSeqs  = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\posSamples.txt");
        //create this list for saving the graphs (from the file above)
        List<List<DocumentNGramGraph>>  lNGramGraphSeqs = new ArrayList<>();  
        
        ISequenceAnalyst<List<DocumentNGramGraph>> nGramGraphanalyst
                = new NGramGraphAnalyzer();
        lNGramGraphSeqs = nGramGraphanalyst.analyze(lSeqs);
        GraphSimilarityComparatorAdapter comparator = new GraphSimilarityComparatorAdapter();
        ISimilarity a=null;
        //For every object (which is a graph) find the similarity with every positive CpG sample
        for(int i=0; i<representation.size(); i++){
        
            for(int j=0; j< lSeqs.size(); j++){
                try {
                    //System.out.println(comparator.getSimilarityBetween(representation.get(i), lSeqs.get(j)));
                    a = comparator.getSimilarityBetween(representation.get(i), lSeqs.get(j));
                    //System.out.println(a.toString());
                } catch (InvalidClassException ex) {
                    Logger.getLogger(NGramGraphClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return a.toString();
    }
    
    @Override
    public void train(List<List<DocumentNGramGraph>> representation, String sLabel) {
    //1/1+ar documents pou xoun ginei merge edge

        // Create a temporary model
        DocumentNGramGraph curGraph = new DocumentNGramGraph();
        
        // Train the model 
        for(int i=0; i<representation.size(); i++)
            curGraph.mergeGraph(representation.get(i).get(i), 1/(1+i));
        
        // Store the model in the classModel map
        classModel.put(sLabel,curGraph);
    
    }

    @Override
    public HMMFeatureVector getFeatureVector(List<ObservationDiscrete<HmmSequence.Packet>> get, String cpG) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
