/*
Author: Danielle DeLooze
Date: 4/3/2018
Purpose: performs likelihood weighting approximation on a given baysesian network and target random variable
 */


package bn.inference;

import bn.core.Assignment;
import bn.core.BayesianNetwork;
import bn.core.Distribution;
import bn.core.RandomVariable;

import java.util.List;

public class LikelihoodWeighting {

    public class WeightedSample{
        Assignment e;
        double weight;

        public WeightedSample(Assignment e, double weight){
            this.e = e;
            this.weight = weight;
        }
    }

    public Distribution likelihoodWeighting(RandomVariable X, Assignment e, BayesianNetwork bn, int num){
        Distribution weights = new Distribution();

        for(int i =0; i <= num; i++){
            WeightedSample sample = weightedSample(bn, e);
            if(weights.get(sample.e.get(X)) == null){
                weights.put(sample.e.get(X), sample.weight);
            }
            else{
                Double counter = weights.get(sample.e.get(X));
                counter += sample.weight;
                weights.put(sample.e.get(X), counter);
            }
        }

        weights.normalize();
        return weights;
    }

    public WeightedSample weightedSample(BayesianNetwork bn, Assignment e){
        double w = 1;
        Assignment x = e.copy();
        List<RandomVariable> vars = bn.getVariableListTopologicallySorted();

        for(RandomVariable var: vars){
            if(e.containsKey(var)){
                w = w * bn.getProb(var, x);
            }
            else{
                x.set(var, randomValue(bn, x, var));
            }
        }

        WeightedSample sample = new WeightedSample(x, w);
        return sample;

    }

    public Object randomValue(BayesianNetwork bn, Assignment e, RandomVariable var){
        Distribution dist = new Distribution();
        for(int i=0; i < var.getDomain().size(); i++){
            Assignment a = e.copy();
            a.set(var, var.getDomain().get(i));
            double res = bn.getProb(var, a);
            dist.put(var.getDomain().get(i), res);
        }

        dist.normalize();
        double val = Math.random();
        double test = 0.0;
        for(int j=0; j < var.getDomain().size(); j++){
            test += dist.get(var.getDomain().get(j));
            if(val <= test){
                return var.getDomain().get(j);
            }
        }

        return null; //should never reach here
    }


}
