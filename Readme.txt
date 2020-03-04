Command to connect:
ssh -i "19308180.pem" ubuntu@ec2-54-92-149-221.compute-1.amazonaws.com
The pem file is also attached.

The AWS Instance contains a single folder containing all the related files of the assignment.
Execute the following commands:
cd Lucene_IR_A1
mvn clean
mvn install
java -jar ./target/Lucene_IR_A1-1.0-SNAPSHOT-jar-with-dependencies.jar

The first two is used to build and create the jar, which is already done. Hence run only
the fourth command to run the program.
Upon completion of the run the resuts are written to result.txt.

use:
cp result.txt ./trec_eval
which moves it to the trec_eval folder

Then run:
./trec_eval QRelsCorrectedforTRECeval result.txt


The Documents and Index folder in the Lucene_IR_A1 are cleared so that the program can run.
Ensure the same before running again.
rm -rf Documents
rm -rf Index
mkdir Documents
mkdir Index
rm result.txt.
use these commands to reset.