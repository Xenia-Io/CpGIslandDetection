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
import gr.demokritos.iit.jinsect.structs.GraphSimilarity;
import gr.demokritos.iit.jinsect.*;
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
        FASTAFileReader reader = new FASTAFileReader();  
        //create for each sequence similarity String (from file with positive CpG samples)
        ArrayList<BaseSequence> lSeqs;
        lSeqs  = reader.getSequencesFromFile("C:\\Users\\Xenia\\Documents\\17112016\\CpGIslandDetection\\data\\testForPosCPG_Fasta.txtSMALL");
        
        //create this list for saving the graphs (from the file above)
        List<List<DocumentNGramGraph>>  lNGramGraphSeqs = new ArrayList<>();  
        
        ISequenceAnalyst<List<DocumentNGramGraph>> nGramGraphanalyst
                = new NGramGraphAnalyzer();
        lNGramGraphSeqs = nGramGraphanalyst.analyze(lSeqs);
        
        GraphSimilarityComparatorAdapter comparator = new GraphSimilarityComparatorAdapter();
        ISimilarity similarity=null;
        System.out.println("R size = " + representation.size());
        System.out.println("LS size = " + lSeqs.size());
        //For every object (which is similarity graph) find the similarity with every positive CpG sample
        for(int i=0; i<representation.size(); i++){
        
            for(int j=0; j< lSeqs.size(); j++){
                try {
                    
                    
                    System.out.println("represGRAPH = "+ representation.get(i).length() + "  lSeqsG = "+ lSeqs.get(j).myLength(lSeqs.get(j).getSymbolSequence()));
                    similarity = comparator.getSimilarityBetween(representation.get(i), lSeqs.get(j));
                    
                    
                } catch (InvalidClassException ex) {
                    Logger.getLogger(NGramGraphClassifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return similarity.toString();
        
    }
    
    @Override
    public void train(List<List<DocumentNGramGraph>> representation, String sLabel) {

        // Create similarity temporary model
        DocumentNGramGraph curGraph = new DocumentNGramGraph();
        int iCount = 0;
        
        // Train the model 
        for(int i=0; i<representation.size()*0.9; i++){
            for(int j = 0; j < representation.get(i).size(); j++) {
                
                curGraph.mergeGraph(representation.get(i).get(j), 1.0/((double)iCount + 1.0));
                iCount++;
            
            }
        }
       
        // Store the model in the classModel map
        classModel.put(sLabel,curGraph);
    }

    @Override
    public HMMFeatureVector getFeatureVector(List<ObservationDiscrete<HmmSequence.Packet>> get, String cpG) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
