package LDFI.BooleanFormula;

import java.util.Set;

public interface BFNode{
    BFNode simplify();
    BFNode convertToCNF();
    Set<String> vars();
    BFNode flipPolarity();
    int clauses();


    public boolean equals(Object obj);

}


