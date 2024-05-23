package LDFI.BooleanFormula;

import java.util.HashSet;
import java.util.Set;

public class BFAndNode extends BinaryBFNode {
    public BFAndNode(BFNode left, BFNode right) {
        super(left, right);
    }

    public CNF findConjuncts(){
        CNF left_conj = null;
        if (left instanceof BFAndNode)
        {
           left_conj = new CNF(((BFAndNode) left).findConjuncts());
        }
        else if (left instanceof BFOrNode){
            left_conj = new CNF(((BFOrNode) left).findDisjuncts());
        }
        else if (left instanceof BFLiteral)
        {
            left_conj = new CNF(new Disjunction(((BFLiteral)left).toString()));
        }
        else {
            System.out.println("Unexpected Node Type");
            System.exit(-1);
        }

        CNF right_conj = null;

        if (right instanceof BFAndNode)
        {
            right_conj = new CNF(((BFAndNode) right).findConjuncts());
        }
        else if (right instanceof BFOrNode){
            {
                right_conj = new CNF(((BFOrNode) right).findDisjuncts());
            }
        }
        else if (right instanceof BFLiteral){
            right_conj = new CNF(new Disjunction(((BFLiteral)right).toString()));
        }
        else {
            System.out.println("Unexpected Node Type");
            System.exit(-1);
        }

        return new CNF(left_conj, right_conj);
    }

    @Override
    protected  BFNode construct(BFNode left, BFNode right) {
        return new BFAndNode(left, right);
    }

    @Override
    public BFNode simplify(){
        return new BFAndNode(this.left.simplify(), this.right.simplify());
    }

    @Override
    public BFNode flip(BFNode left, BFNode right) {
        return new BFOrNode(left,right);
    }

    @Override
    public BFNode convertToCNF(){
        return new BFAndNode(this.left.convertToCNF(), this.right.convertToCNF());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof BFAndNode)
        {
            return left.equals(((BFAndNode) obj).left) && right.equals(((BFAndNode) obj).right);
        }
        return false;
    }

    @Override
    public String toString(){
        return "(" + left + " AND " + right + ")";
    }

}
