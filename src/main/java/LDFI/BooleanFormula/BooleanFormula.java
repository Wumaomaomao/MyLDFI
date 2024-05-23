package LDFI.BooleanFormula;

public class BooleanFormula {
    private final BFNode root;

    private final CNF cnf = new CNF();

    public BooleanFormula(BFNode root) {
        this.root = root;
    }
    public BooleanFormula simplifyAll(){
        BFNode last = null;
        BFNode current = root;

        while(!current.equals(last))
        {
            last = current;
            current = current.simplify();
        }

        return new BooleanFormula(current);
    }

    public BooleanFormula simplify(){
        return new BooleanFormula(root.simplify());
    }

    public BooleanFormula convertToCNF(){


//        return new BooleanFormula(root.convertToCNF());

        BFNode root = null;
        BFNode current =this.root;
        int iterations = 0;

        while(!current.equals(root))
        {
            root = current;
//            System.out.println("starting iteration: " + iterations + ". " + this.root.toString() );
            current = current.convertToCNF();
//            System.out.println("finished iteration: " + iterations + ". " + this.root.vars().toString() );
            ++iterations;
        }

        return new BooleanFormula(current);

    }

    public BooleanFormula flipPolarity(){
        return new BooleanFormula(root.flipPolarity());
    }

    public CNF getCnf()
    {
        if (root instanceof BFLiteral)
        {
            return new CNF(new Disjunction(((BFLiteral)root).toString()));
        }
        else if (root instanceof BFAndNode)
        {
            return new CNF(((BFAndNode)root).findConjuncts());
        }
        else if (root instanceof BFOrNode)
        {
            return new CNF(((BFOrNode) root).findDisjuncts());
        }
        else {
            System.err.println("root : " +  root.toString() + "Can't convert to CNF");
            System.exit(-1);
        }

        return null;
    }


    @Override
    public String toString() {
        return root.toString();
    }

}
