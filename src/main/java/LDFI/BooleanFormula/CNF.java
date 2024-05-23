package LDFI.BooleanFormula;

import java.util.HashSet;
import java.util.Set;

public class CNF {
    private Set<Disjunction> disjunctions;
    private Set<String> vars;

    public CNF()
    {
        disjunctions = new HashSet<>();
        vars = new HashSet<>();
    }

    public CNF(Disjunction disjunction)
    {
        disjunctions = new HashSet<>();
        disjunctions.add(disjunction);
        vars = new HashSet<>();
        vars.addAll(disjunction.getVars());
    }

    public CNF(CNF left, CNF right){
        disjunctions = new HashSet<>();
        disjunctions.addAll(left.disjunctions);
        disjunctions.addAll(right.disjunctions);
        vars = new HashSet<>();
        vars.addAll(left.vars);
        vars.addAll(right.vars);
    }

    public CNF(CNF cnf){
        disjunctions = new HashSet<>();
        disjunctions.addAll(cnf.disjunctions);
        vars = new HashSet<>();
        vars.addAll(cnf.vars);
    }


    public Set<String> getVars(){
        return new HashSet<>(vars);
    }

    public Set<Disjunction> getDisjunctions(){
        return new HashSet<>(disjunctions);
    }


    @Override
    public String toString(){
        String s = "CNF with disjunctions : ";
        for (Disjunction disjunction : disjunctions){
            s += disjunction.toString();
        }
        return s;
    }
}
