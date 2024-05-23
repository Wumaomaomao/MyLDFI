package LDFI.BooleanFormula;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

abstract class BinaryBFNode implements BFNode {
    protected  BFNode left;
    protected  BFNode right;

    abstract  BFNode flip(BFNode left, BFNode right);

    public BinaryBFNode(BFNode left, BFNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public  int clauses(){
        return 1 + left.clauses() + right.clauses();
    }

    @Override
    public Set<String> vars(){
        Set<String> vars = new HashSet<>();
        vars.addAll(left.vars());
        vars.addAll(right.vars());
        return vars;
    }

    @Override
    public BFNode simplify()
    {
        if(left instanceof BFLiteral &&((BFLiteral) left).isNone())
        {
            if (right instanceof BFLiteral &&((BFLiteral) right).isNone())
            {
                return null;
            }
            else return right;
        }
        else if (right instanceof BFLiteral &&((BFLiteral) right).isNone())
        {
            return left;
        }

        if (left instanceof BFLiteral  && right instanceof BFLiteral)
        {
            if (left.equals(right))
            {
                return left;
            }
            else return construct(left, right);
        }

        return construct(left.simplify(), right.simplify());

    }

    @Override
    public BFNode flipPolarity(){
        return flip(left.flipPolarity(), right.flipPolarity());

    }



    protected abstract BFNode construct(BFNode left, BFNode right);
}
