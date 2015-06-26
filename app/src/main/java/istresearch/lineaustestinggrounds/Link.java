package istresearch.lineaustestinggrounds;

/**
 * Created by Titch on 6/25/2015.
 */
public class Link <P, C>
{
    private P parent;
    private C child;

    public Link(P p, C c)
    {
        parent = p;
        child = c;
    }

    public P getParent()
    {
        return parent;
    }

    public C getChild()
    {
        return child;
    }
}
