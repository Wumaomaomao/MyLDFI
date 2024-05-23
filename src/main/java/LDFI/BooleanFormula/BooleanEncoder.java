package LDFI.BooleanFormula;

import LDFI.lineageGraph.NodeClass;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class BooleanEncoder {

    public BFNode buildBooleanFormula(List<List<NodeClass>> paths)
    {
        List<BFNode> conjs = new ArrayList<BFNode>();
        for (int i = 0; i < paths.size(); i++) {
            BFNode conj  = buildConjunction(paths.get(i));
            conjs.add(conj);
        }

        return buildDisjunction(conjs);
    }

    BFNode buildConjunction(List<NodeClass> path) {
        BFNode conjunction = null;
        for (NodeClass node : path)
        {
            BFLiteral literal = new BFLiteral(node.toString());
            if (conjunction == null)
            {
                conjunction = literal;
            }
            else {
                conjunction = new BFAndNode(conjunction, literal);
            }
        }

        return conjunction;
    }
    BFNode buildDisjunction(List<BFNode> conjunctions) {
        BFNode disjunction = null;

        for (BFNode conjunction : conjunctions)
        {
            if (disjunction == null)
            {
                disjunction = conjunction;
            }
            else
            {
                disjunction = new BFOrNode(disjunction, conjunction);
            }
        }
        return disjunction;
    }
}
