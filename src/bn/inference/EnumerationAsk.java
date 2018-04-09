/*
Author: Danielle DeLooze
Date: 3/29/2018
Purpose: topigraphically sort node -> create empty distribution -> for each value xi of X ->
         Q(xi) = enumerate all(bn.vars, e_xi) where e_xi is extended with X=xi

         Return the exact inference
 */
package bn.inference;
import bn.core.*;

import java.util.List;

public class EnumerationAsk {

    public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e){
        Distribution dist = new Distribution(X);

        for(int i = 0; i < X.getDomain().size(); i++){
            Object elem = X.getDomain().get(i);
            Assignment copy = e.copy();
            copy.set(X, elem);
            dist.put(elem, Enumerate_all(bn, copy, 0));
        }

        dist.normalize();
        return dist;
    }

    public double Enumerate_all(BayesianNetwork bn, Assignment e, int index){
        List<RandomVariable> vars = bn.getVariableListTopologicallySorted();
        if( index >= vars.size()){
            return 1.0;
        }
        RandomVariable Y = vars.get(index);
        if( e.containsKey(Y)){
            Assignment copy = e.copy();
            //then return P(y | parents(Y )) × ENUMERATE-ALL(REST(vars), e)
            return  (bn.getProb(Y, e) * Enumerate_all(bn, copy, index+1));
        }
        else{
            double summation = 0;
            for(int i =0; i<Y.getDomain().size(); i++){
                Object y = Y.getDomain().get(i);
                Assignment copy = e.copy();
                copy.set(Y, y);
                summation += (bn.getProb(Y, copy) * Enumerate_all(bn, copy, index+1));
            }
            return summation;
        }
    }

}
