/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Xenia
 */
public class NGramGraphAnalyzer implements ISequenceAnalyst<List<DocumentNGramGraph>> {

    @Override
    public List<List<DocumentNGramGraph>> analyze(List<BaseSequence> baseSeq) {
        
       
        List<List<DocumentNGramGraph>> lRes = new ArrayList<>();
    
        //For each sequence
        for(BaseSequence bsCur : baseSeq){
            //System.out.println("TEST_0");
            DocumentNGramGraph graph = new DocumentNGramGraph();
            
            //System.out.println("TEST_1");
            graph.setDataString(bsCur.getSymbolSequence());
            List<DocumentNGramGraph> testSeq = new ArrayList<>();
           
            testSeq.add(graph);
           
            lRes.add(testSeq);
        }
        
        return lRes;
    }    

    
 
}
