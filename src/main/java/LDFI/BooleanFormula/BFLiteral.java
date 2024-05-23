package LDFI.BooleanFormula;
import LDFI.BooleanFormula.*;

import java.util.HashSet;
import java.util.Set;

public class BFLiteral implements BFNode {
    private String value;

    public BFLiteral(String value) {
        this.value = value;
    }

    @Override
    public BFNode simplify(){
        return this;
    }

    @Override
    public Set<String> vars(){
        Set<String> vars = new HashSet<String>();
        vars.add(value);
        return vars;
    }

    @Override
    public BFNode flipPolarity(){
        return new BFLiteral(value);
    }

    @Override
    public  int clauses(){
        return 0;
    }

    @Override
    public String toString(){
        return value;
    }

    public boolean isNone(){
        return value == null;
    }


    @Override
    public BFNode convertToCNF(){
        return new BFLiteral(value);
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof BFLiteral)
        {
            return value.equals(((BFLiteral)obj).value);
        }
        return false;
    }
}
