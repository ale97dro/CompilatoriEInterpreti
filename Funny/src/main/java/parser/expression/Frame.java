package parser.expression;

import parser.EvalException;

import java.util.HashMap;
import java.util.List;

public class Frame {
    private HashMap<String, Val> binding;

    public Frame(List<String> params, List<String> locals, List<Val> argsVal) throws EvalException {
        binding = new HashMap<>();

        //per ogni params, associo il valore in argsVal
        //per ogni locals, piazzo nil

        if(params.size() != argsVal.size())
            throw new EvalException("Params!=argsVal");

        for(int i=0; i<params.size();i++)
            binding.put(params.get(i), argsVal.get(i));

        for(String s : locals)
            binding.put(s, NilVal.instance());
    }

    //TODO: implementa metodi per rcuperare i valori della mappa

    public Val getValue(String id) throws EvalException
    {
        if(binding.keySet().contains(id))
            return binding.get(id);

        return null;
    }

    public void addValue(String id, Val value) throws EvalException
    {
        if(binding.containsKey(id))
            binding.replace(id, value);

        throw new EvalException("add");
    }
}
