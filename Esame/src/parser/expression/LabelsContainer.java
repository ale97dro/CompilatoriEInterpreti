package parser.expression;

import java.util.HashMap;
import java.util.Map;

public class LabelsContainer {
    private Map<String, Expr> contenitore;

    public LabelsContainer()
    {
        contenitore = new HashMap<>();
    }

    public void addLabel(String id, Expr expression)
    {
        contenitore.put(id, expression);
    }

    public Expr get(String id)
    {
        return contenitore.get(id);
    }

    public boolean isLabelStored(String id)
    {
        Expr expr = contenitore.get(id);

        return expr != null;
    }
}
