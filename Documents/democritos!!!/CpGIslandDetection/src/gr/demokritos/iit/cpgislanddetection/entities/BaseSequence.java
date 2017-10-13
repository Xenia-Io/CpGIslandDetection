/* 
 * Copyright 2016 NCSR Demokritos.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.demokritos.iit.cpgislanddetection.entities;

/**
 *In this class we build sequences which are appropriate  
 * for VectorSpaceRepresentation method
 * @author Xenia
 */
public class BaseSequence implements IGenomicSequence {

    protected String BaseSeq;
    
    public BaseSequence(String sSeq) {
        BaseSeq = sSeq;
    }
    
    @Override
    public String getSymbolSequence() {
        return BaseSeq;
    }
    
    @Override
    public String toString() {
        return BaseSeq;
    }
    
    public int myLength(String sSeq) {
        return sSeq.length();
    }
    
    public int myCharAt(int i, String sSeq) {
        return sSeq.charAt(i);
    }
}