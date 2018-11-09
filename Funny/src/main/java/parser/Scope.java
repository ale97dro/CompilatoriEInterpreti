package parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope
{
    private Map<String, Integer> ids;

    public Scope(List<String> new_ids)
    {
        ids = new HashMap<>();

        for(String s : new_ids)
            ids.put(s, 0);
    }

    //TODO implementare
    public Scope(List<String> new_ids, Scope scope)
    {
        ids = new HashMap<>();
    }

    public boolean checkInScope(String id)
    {
        return ids.containsKey(id);
    }
}
