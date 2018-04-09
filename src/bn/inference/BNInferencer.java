/*
Author: Danielle DeLooze
Date: 4/3/2018
Purpose: class to build the bayesian networks and use the arguments to perform either Enumeration, Rejection Sampling, or Likelihood Weighting
 */

package bn.inference;
import java.io.*;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import bn.core.*;
import bn.parser.*;

import static java.lang.Integer.parseInt;

public class BNInferencer {
    public static void main(String[] argv){
        if(argv[0].contains(".xml")){
            XMLBIFParser x = new XMLBIFParser();
            try {
                BayesianNetwork bn = x.readNetworkFromFile(argv[0]);
                if(argv[1].equals("EnumerationAsk")){
                    EnumerationAsk asker = new EnumerationAsk();
                    Assignment e = new Assignment();
                    for(int i = 3; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    Distribution dist = asker.ask(bn, bn.getVariableByName(argv[2]), e);
                    System.out.println(dist);
                }
                else if(argv[1].equals("RejectionSampling")){
                    RejectionSampling rej = new RejectionSampling();
                    Assignment e = new Assignment();
                    for(int i = 4; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    Distribution dist = rej.rejectionSampling(bn.getVariableByName(argv[3]), e, bn, parseInt(argv[2]));
                    System.out.println(dist);
                }
                else if(argv[1].equals("LikelihoodWeighting")){
                    LikelihoodWeighting like = new LikelihoodWeighting();
                    Assignment e = new Assignment();
                    for(int i = 4; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    Distribution dist = like.likelihoodWeighting(bn.getVariableByName(argv[3]), e, bn, parseInt(argv[2]));
                    System.out.println(dist);
                }
                else{
                    System.out.println("Invalid Type");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        } else {
            BIFParser x;
            try {
                x = new BIFParser(new FileInputStream(argv[0]));
                BayesianNetwork bn = x.parseNetwork();

                if(argv[1].equals("EnumerationAsk")){
                    Assignment e = new Assignment();
                    for(int i = 3; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    EnumerationAsk asker = new EnumerationAsk();
                    Distribution dist = asker.ask(bn, bn.getVariableByName(argv[2]), e);
                    System.out.println(dist);
                }
                else if(argv[1].equals("RejectionSampling")){
                    Assignment e = new Assignment();
                    for(int i = 4; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    RejectionSampling rej = new RejectionSampling();
                    Distribution dist = rej.rejectionSampling(bn.getVariableByName(argv[3]), e, bn, parseInt(argv[2]));
                    System.out.println(dist);
                }
                else if(argv[1].equals("LikelihoodWeighting")){
                    Assignment e = new Assignment();
                    for(int i = 4; i < argv.length; i+=2){
                        e.put(bn.getVariableByName(argv[i]), argv[i+1]);
                    }
                    LikelihoodWeighting like = new LikelihoodWeighting();
                    Distribution dist = like.likelihoodWeighting(bn.getVariableByName(argv[3]), e, bn, parseInt(argv[2]));
                    System.out.println(dist);
                }
                else{
                    System.out.println("Invalid Type");
                }

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

}