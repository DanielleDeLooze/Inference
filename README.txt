Name: Danielle DeLooze
StudentID: 29493487
Email: ddelooze@u.rochester.edu

Collaborators: None

How to Build: 
run "ant -f build.xml"
run "java BNInferencer [arguments]"

argument order is as follows:
1. network you would like to build (ex. aima-alarm.xml)
2. Algorithmn you would like to use
   ExactInference or RejectionSampling or LikelihoodWeighting
3. Query variable
4. Evidence variables

java BNInferencer aima-alarm.xml RejectionSampling 1000 B J true M true

This would run rejection sampling with a trial number of 1000 on the query variable B with J and M set to true on the alarm bayesian network.

If you get a "main class not found" error try running the following instead
"java -cp ./build BNInferencer [arguments]