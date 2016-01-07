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
package gr.demokritos.iit.cpgislanddetection.analysis;

import gr.demokritos.iit.cpgislanddetection.entities.IGenomicSequence;
import java.util.List;

/**
 *
 * @author Xenia
 */
public class VectorSequenceDetector implements ICpGSequenceDetector {

    public VectorSequenceDetector(List<IGenomicSequence> sequences, List<String> labels) {
        // Initialize classifier
        // Train classifier
    }

    
    @Override
    public boolean detect(IGenomicSequence seq) {
        return true;
        // Classify seq using your existing classifier
        // return true if the label indicates a CpG
    }
    
}
