/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.io.FASTAFileReader;
import gr.demokritos.iit.cpgislanddetection.io.IGenomicSequenceFileReader;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class NGramGraphClassifier implements ISequenceClassifier<List<DocumentNGramGraph>>{

    @Override
    public String classify(List<DocumentNGramGraph> representation) {
        
        //ftiaxnw grafous gia to arxeio me ta positive CpG samples
        IGenomicSequenceFileReader reader = new FASTAFileReader();
        ArrayList<BaseSequence> lSeqs;
              
        lSeqs  = reader.getSequencesFromFile("C:\\Users\\Xenia\\Desktop\\posSamples.txt");
        
        // Create sequences
        ISequenceAnalyst<List<DocumentNGramGraph>> nGramGraphanalyst
                = new NGramGraphAnalyzer();
        List<DocumentNGramGraph> lHmmSeqs = nGramGraphanalyst.myAnalyze(lSeqs);
            
        //For every sequence which is a graph
        for(int i=0; i<representation.size(); i++){
        
            DocumentNGramGraph currentGraph = new DocumentNGramGraph();
            
            currentGraph = representation.get(i).inverseIntersectGraph(currentGraph);
        
        }
        //Get the graph
        //Compute the similarity with all the positive CpG samples
        //Check the similarity and decide if this sample is a CpG Island
        
        return null;
    }

    @Override
    public void train(List<List<DocumentNGramGraph>> representation, String sLabel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getClassModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
