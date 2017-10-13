Subject:
The main purpose of this project is the implementation of four different algorithms in order to identify CpG islands in human DNA. Each method is applied in the same test data for the training part of our classification. In the next part, we test our classifiers and they classify each DNA sequence between two classes: IS CpG/IS Not CpG.

Method 1: Vector Analysis
Steps for vector analysis are the following:
•	Create training instances: We read sequences from txt files, one file is for positive samples and one file for negative, and then we create vectors for every sequence. In each vector we add a parameter that indicates if vector is positive or negative training sample. So, we have created ARFF files for positive and negative samples.
•	Train classifier
•	Classify and cross validate classifier

Method 2: Hidden Markov Model
Steps for Hidden Markov Model are the following:
•	We create the representation for instances in this model using HmmAnalyzer and HmmClassifier. According to this step we create 4 feature vectors: one for cpg training instances, one for not cpg training instances, one for cpg testing instances and one for not cpg testing instances. Each feature vector is an ArrayList<HMMFeatureVector> object. Each HMMFeatureVector consists of a probability array and a label that indicates if this is a cpg island or not. Using getFeatureVector() method from HmmClassifier we create HMMFeatureVector for each sequence.
•	Then we create and get the Weka HMMfeature vectors
•	Train classifier based on training instances
•	We test the model

Method 3: Rule-Based Model
This method is an implementation based on the theory for cpg islands. Steps are the following:
•	We Extract test instances which are objects of List<BaseSequence>. Then we create again 4 feature vectors: one for cpg training instances, one for not cpg training instances, one for cpg testing instances and one for not cpg testing instances. Each feature vector is an ArrayList<HMMFeatureVector> object. Each HMMFeatureVector consists of a probability array and a label that indicates if this is a cpg island or not. Using getFeatureVector() method from HmmClassifier we create HMMFeatureVector for each sequence.
•	Using CpGIslandIdentification class we follow the 3 rules according to the theory of cpg islands to decide if the current sample is a cpg island.
•	Then we evaluate the model.

Method 4: N-gram Graph
Steps for N-gram Graph model:
•	Create List<List<DocumentNGramGraph>> objects for this classification using NGramGraphAnalyzer.
•	We train classifier.
•	We test classifier using NGramCachedGraphComparator class.
