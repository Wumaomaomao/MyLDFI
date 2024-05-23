package LDFI.SATSolver;

import com.google.common.collect.FluentIterable;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.minisat.core.Solver;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;
import org.sat4j.tools.ModelIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SATSolver {
    private final ISolver solver;
    List<List<Integer>> results;
    List<List<Integer>> miniResults;
    Logger logger = Logger.getLogger(SATSolver.class.getName());

    public SATSolver() {
        solver = SolverFactory.newDefault();
        solver.setTimeout(3600);
    }

    public void addClauses(List<List<Integer>> clauses)
    {
        for (List<Integer> clause : clauses)
        {
            addClause(clause);
        }
    }

    public void addClause(List<Integer> literals) {
        IVecInt clause = new VecInt();
        for (int literal : literals) {
            clause.push(literal);
        }
        try {
            solver.addClause(clause);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    public List<List<Integer>> getSatisfyingAssignments() {
        List<List<Integer>> satisfyingAssignments = new ArrayList<List<Integer>>();

        ModelIterator mi = new ModelIterator(solver);

        try {
            while (mi.isSatisfiable()) {
                int[] model = mi.model();
                List<Integer> assignment = new ArrayList<>();
                for (int literal : model) {
                    assignment.add(literal);
                }
                satisfyingAssignments.add(assignment);
            }
        }catch(Exception e)
            {
                logger.info(e.getMessage());
            }


        return satisfyingAssignments;

    }

    private boolean isMinimalist(List<Integer> result) {
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i) > 0)
            {
                List<Integer> subResult = new ArrayList<>(result);
                subResult.set(i, -result.get(i));
                if (results.contains(subResult))
                {
                    return false;
                }
            }

        }

        return true;
    }

    private List<List<Integer>> removeDuplicates(List<List<Integer>> results) {
        Set<List<Integer>> uniqueResults = new HashSet<>(results);

        results.clear();
        results.addAll(uniqueResults);

        return results;

    }

    public List<List<Integer>> getMinimalSatisfyingAssignments() {
        results = getSatisfyingAssignments();
        miniResults = new ArrayList<>();
        for (List<Integer> result : results) {
            for (int index = 0; index < result.size(); index++) {
                if (isMinimalist(result))
                {
                    miniResults.add(result);
                }
            }
        }

        removeDuplicates(miniResults);

        return miniResults;
    }

}




