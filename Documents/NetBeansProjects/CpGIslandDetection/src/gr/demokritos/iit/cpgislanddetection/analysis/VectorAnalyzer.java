/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.BaseSequence;
import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Create vectors from ArrayList<BaseSequence> alRes
 *
 * @author Xenia
 */
public class VectorAnalyzer implements ISequenceAnalyst<Vector> {

    /**
     *
     * @param baseSeq
     * @return
     */
    @Override
    public Vector<Vector<Integer>> analyze(ArrayList<BaseSequence> baseSeq) {

        int countAden, countThym, countGouan, countCyt;
        int arrayForCounting[][] = new int[baseSeq.size()][4];
        int arrayForPercents[][] = new int[baseSeq.size()][4];

        //gia to ka8e stoixeio ths listas vriskw to plithos twn A,T,C,G.
        //kai ta apo8hkeuw se enan pinaka o opoios exei oses grammes einai ta stoixeia ths listas 
        //kai kathe grammi exei 4 sthles countA, countC, countG, countT
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
        }

        //Find percents
        for (int i = 0; i < baseSeq.size(); i++) {

            String currentStr = baseSeq.get(i).getSymbolSequence();

            arrayForPercents[i][0] = (arrayForCounting[i][0] * 100) / baseSeq.get(i).myLength(currentStr);
            arrayForPercents[i][1] = (arrayForCounting[i][1] * 100) / baseSeq.get(i).myLength(currentStr);
            arrayForPercents[i][2] = (arrayForCounting[i][2] * 100) / baseSeq.get(i).myLength(currentStr);
            arrayForPercents[i][3] = (arrayForCounting[i][3] * 100) / baseSeq.get(i).myLength(currentStr);
        }

        // Represent as vector
        Vector<Vector<Integer>> list = new Vector<Vector<Integer>>(baseSeq.size());

        for (int i = 0; i < baseSeq.size(); i++) {
            Vector<Integer> vector = new Vector<>();

            vector.add(arrayForPercents[i][0]);
            vector.add(arrayForPercents[i][1]);
            vector.add(arrayForPercents[i][2]);
            vector.add(arrayForPercents[i][3]);

            list.add(vector);

        }
        // Return a vector list vector

        return list;
    }

}
