package LDFI.BooleanFormula;

import java.util.HashSet;
import java.util.Set;

public class Disjunction {
    private Set<String> conjs;
    private Set<String> vars;

    public Set<String> getVars(){
        return new HashSet<>(vars);
    }

    public Disjunction(){
        conjs = new HashSet<>();
        vars = new HashSet<>();
    }

    public Disjunction(String s){
        conjs = new HashSet<>();
        conjs.add(s);
        vars = new HashSet<>();
        vars.add(s);
    }

    public Disjunction(Set<String> clauses){
        conjs = new HashSet<>();
        conjs.addAll(clauses);
        vars = new HashSet<>();
        vars. addAll(clauses);

    }

    @Override
    public String toString(){
        return conjs.toString();
    }
}
