package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope
{
    //private Map<String, Integer> ids;
    private Scope upScope;
    private List<String> scope;

    public Scope(Scope scope, List<String> params)
    {
        //ids = new HashMap<>();

        //for(String s : new_ids)
            //ids.put(s, 0);
        this.scope = new ArrayList<>();

        if(scope != null)
            this.upScope = scope;
            //this.scope.addAll(scope.getScope());
        this.scope.addAll(params);
    }

    public boolean checkInScope(String id)
    {
        //return scope.contains(id);

        if(scope.contains(id))
            return true;
        else
            if(upScope != null)
                return upScope.checkInScope(id);
            else
                return false;
    }

    public List<String> getScope()
    {
        return scope;
    }
}
