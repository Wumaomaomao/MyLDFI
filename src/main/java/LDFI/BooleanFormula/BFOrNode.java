package LDFI.BooleanFormula;

public class BFOrNode extends BinaryBFNode{
    public BFOrNode(BFNode left, BFNode right) {
        super(left, right);
    }

    @Override
    protected BFNode construct(BFNode left, BFNode right) {
        return new BFOrNode(left, right);
    }

    @Override
    public BFNode simplify(){
        return new BFOrNode(left.simplify(),right.simplify());
    }

    @Override
    public BFNode flip(BFNode left, BFNode right) {
        return new BFAndNode(left,right);
    }


    @Override
    public BFNode convertToCNF(){
        if (left instanceof BFAndNode){
            BFNode l = ((BFAndNode)left).left.convertToCNF();
            BFNode r = ((BFAndNode)left).right.convertToCNF();

            return new BFAndNode(new BFOrNode(l, right.convertToCNF()), new BFOrNode(r, right.convertToCNF()));
        }
        if (right instanceof BFAndNode){
            BFNode l = ((BFAndNode)right).left.convertToCNF();
            BFNode r = ((BFAndNode)right).right.convertToCNF();

            return new BFAndNode(new BFOrNode(left.convertToCNF(), l), new BFOrNode(left.convertToCNF(), r));
        }

        return new BFOrNode(left.convertToCNF(), right.convertToCNF());
    }
    public Disjunction findDisjuncts(){
        return new Disjunction(vars());
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof BFOrNode)
        {
            return left.equals(((BFOrNode) obj).left) && right.equals(((BFOrNode) obj).right);
        }
        return false;
    }
    @Override
    public String toString(){
        return "(" + left + "OR" + right + ")";
    }
}
