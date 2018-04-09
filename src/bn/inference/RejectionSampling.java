/*
Author: Danielle DeLooze
Date: 4/3/2018
Purpose: performs rejection sampling approximation on the given bayesian network and target variable
 */

package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;
import java.util.List;

public class RejectionSampling {

    public Distribution rejectionSampling(RandomVariable X, Assignment e, BayesianNetwork bn, int num){
        Distribution count = new Distribution();

        for(int j = 0; j <= num; j++){
            Assignment x = PriorSample(bn);
            if(consistent(e,x)){
                if(count.get(x.get(X)) == null){
                    count.put(x.get(X), 1);
                }
                else{
                    Double counter = count.get(x.get(X));
                    counter++;
                    count.put(x.get(X), counter);
                }
            }
        }
        count.normalize();
        return count;

    }

    public boolean consistent(Assignment e, Assignment x){
       for(RandomVariable V: e.variableSet()){
           if(x.containsKey(V)){
               if(!e.get(V).equals(x.get(V))){
                   return false;
               }
           }
       }
       return true;
    }


    public Assignment PriorSample(BayesianNetwork bn){
        Assignment e = new Assignment();
        List<RandomVariable> vars = bn.getVariableListTopologicallySorted();

        for(int i = 0; i < vars.size(); i++){
            RandomVariable X = vars.get(i);
            Distribution dist = new Distribution();
            for(int j = 0; j < X.getDomain().size(); j++){
                Assignment temp = e.copy();
                temp.set(X, X.getDomain().get(j));
                double prob = bn.getProb(X, temp);
                dist.put(X.getDomain().get(j), prob );
            }

            dist.normalize();
            double value = Math.random();
            double test = 0.0;

            for(int k=0; k < X.getDomain().size();k++){
                test += dist.get(X.getDomain().get(k));
                if(value <= test){
                    e.set(X, X.getDomain().get(k));
                    break;
                }
            }


        }
        return e;
    }

}
