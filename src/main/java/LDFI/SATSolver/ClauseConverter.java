package LDFI.SATSolver;

import LDFI.BooleanFormula.BooleanFormula;
import LDFI.BooleanFormula.CNF;
import LDFI.BooleanFormula.Disjunction;

import java.util.*;

public class ClauseConverter {
    private Map<String, Integer> symbolMapInt;
    private Map<Integer, String> intMapSymbol;
    private List<List<Integer>> cnfClauses;
    Integer stringToIntegerNumber = 1;

    public ClauseConverter()
    {
        symbolMapInt = new HashMap<>();
        intMapSymbol = new HashMap<>();
        symbolMapInt = new HashMap<String, Integer>();
        cnfClauses = new ArrayList<List<Integer>>();
    }

    private void cnfMapping(Set<String> cnfVars){
        for (String var : cnfVars)
        {
            symbolMapInt.put(var, stringToIntegerNumber);
            intMapSymbol.put(stringToIntegerNumber, var);
            ++stringToIntegerNumber;
        }


    }

    public List<List<Integer>> convertSymbolsToIntegerArray(CNF cnf){
        List<List<Integer>> clauses = new ArrayList<>();
        cnfMapping(cnf.getVars());

        for (Disjunction disj : cnf.getDisjunctions())
        {
            List<Integer> clause = new ArrayList<>();
            for (String var : disj.getVars())
            {
                clause.add(symbolMapInt.get(var));
            }
//            System.out.println(clause);
            if (!clause.isEmpty())
            {
                clauses.add(clause);
            }

        }
        cnfClauses = clauses;
        return cnfClauses;
    }

    public List<String> convertIntegersToSymbolArray(List<Integer> result){
        List<String> symbols = new ArrayList<>();
        for (Integer integer : result)
        {
            if (integer > 0)
            {
                symbols.add(intMapSymbol.get(integer));
            }
        }
        return symbols;
    }


}
