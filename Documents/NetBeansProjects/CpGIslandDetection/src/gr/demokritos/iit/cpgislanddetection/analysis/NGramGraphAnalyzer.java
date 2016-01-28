/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.jinsect.*;
import gr.demokritos.iit.jinsect.documentModel.representations.DocumentNGramGraph;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Xenia
 */
public class NGramGraphAnalyzer implements ISequenceAnalyst<List<DocumentNGramGraph>> {

    
    
    public List<DocumentNGramGraph> myAnalyze(List<BaseSequence> baseSeq) {
        
        List<DocumentNGramGraph> lRes = new ArrayList<>();
        
        // For each sequence
        for (BaseSequence bsCur : baseSeq) {
        
            DocumentNGramGraph graph = new DocumentNGramGraph();
            graph.setDataString(bsCur.getSymbolSequence());
            lRes.add(graph);
            
        }
                
        return lRes;
    }

    
    @Override
    public List<List<DocumentNGramGraph>> analyze(List<BaseSequence> gsSeq) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
