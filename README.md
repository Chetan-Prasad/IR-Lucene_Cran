# IR-Lucene_Cran
Information Retrieval Assignment 1

The AWS Instance contains a single folder containing all the related files of the assignment.
Execute the following commands:

cd Lucene_IR_A1

mvn clean

mvn install

java -jar ./target/Lucene_IR_A1-1.0-SNAPSHOT-jar-with-dependencies.jar

The first two is used to build and create the jar. 
Upon completion of the run the resuts are written to result.txt.

Create two empty folders Documents and Index.

Ensure that cran.all.1400 and cran.qry are in the project folder so that the program can access it.

Use trec_eval to test the performance using the QRel file.
