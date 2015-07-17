package istresearch.lineaustestinggrounds;

import java.util.ArrayList;

/**
 * Created by Titch on 6/25/2015.
 */
public class Entity
{
    ArrayList<Link<Entity, Entity>> linksFromThisNode = new ArrayList<>();
    ArrayList<Link<Entity, Entity>> linksToThisNode = new ArrayList<>();

    public void receiveLink(Link<Entity, Entity> link)
    {
        linksToThisNode.add(link);
    }

    public void sendLink(Entity e)
    {
        Link<Entity, Entity> l = new Link<Entity, Entity>(this, e);
        linksFromThisNode.add(l);
    }
}
